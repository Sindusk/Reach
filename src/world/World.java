package world;

import world.blocks.Block;
import events.ProjectileEvent;
import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import entity.Entity;
import entity.PlayerEntity;
import entity.Projectile;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import netdata.ChunkData;
import tools.Sys;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class World {
    private static final int SIZE_X = 25;
    private static final int SIZE_Y = 25;
    private static final int UNLOAD_DISTANCE = 7;
    private static final int LOAD_DISTANCE = 4;
    
    protected HashMap<Vector2i, Chunk> chunkMap = new HashMap();
    protected Node node = new Node("World");
    protected ArrayList<Entity> entities=new ArrayList<Entity>();
    protected QuadTree quadTree = new QuadTree(0, new Rectangle2D.Float(-100, -100, 200, 200));
    protected int seed;
    
    public World(int seed){
        this.seed = seed;   // Not yet used
    }
    
    // Getters
    public Node getNode(){
        return node;
    }
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    public Chunk getChunk(Vector2f loc){
        Vector2i chunk = new Vector2i(Math.round(((loc.x+0.5f)/Chunk.SIZE)-0.5f), Math.round(((loc.y+0.5f)/Chunk.SIZE)-0.5f));
        return chunkMap.get(chunk);
    }
    public Block getBlock(Vector2f loc){
        Vector2i chunk = new Vector2i(Math.round(((loc.x+0.5f)/Chunk.SIZE)-0.5f), Math.round(((loc.y+0.5f)/Chunk.SIZE)-0.5f));
        Vector2i coords = new Vector2i(Math.round(loc.x)-(chunk.x*Chunk.SIZE), Math.round(loc.y)-(chunk.y*Chunk.SIZE));
        Chunk c = chunkMap.get(chunk);
        if(c == null){
            return null;
        }
        return c.getBlock(coords);
    }
    public Block getBlock(Vector3f loc){
        return getBlock(new Vector2f(loc.x, loc.y));
    }
    
    public void sendData(HostedConnection conn){
        int x = 0-(SIZE_X/2);
        int y;
        while(x < SIZE_X/2){
            y = 0-(SIZE_Y/2);
            while(y < SIZE_Y/2){
                conn.send(chunkMap.get(new Vector2i(x, y)).toData());
                y++;
            }
            x++;
        }
    }
    
    public Projectile addProjectile(ProjectileEvent attack){
        Projectile p = new Projectile(node, attack);        // Creates the projectile class data
        p.create(0.4f, attack.getStart(), attack.getTarget());    // Creates the projectile entity
        entities.add(p);    // Adds to the list of entities in the world
        return p;
    }
    public void destroyProjectile(int hashCode){
        Projectile p;
        for(Entity e : entities){
            if(e instanceof Projectile){
                p = (Projectile) e;
                if(p.getAttack().getHashCode() == hashCode){
                    p.destroy();
                    return;
                }
            }
        }
    }
    public PlayerEntity addPlayerEntity(Player player, ColorRGBA color){
        PlayerEntity e = new PlayerEntity(node, player, color);
        entities.add(e);    // Adds to the list of entities in the world
        return e;
    }
    
    public void update(float tpf){
        // Updates all entities currently in the world
        // Might need to be changed, sounds super inefficient...
        quadTree.clear();
        int i = 0;
        Entity t;   // Temp entity
        while(i < entities.size()){
            t = entities.get(i);
            if(entities.get(i).isDestroyed()){
                entities.remove(entities.get(i));   // Remove any entities that are destroyed, before updating.
            }else{
                t.update(tpf);
                quadTree.insert(t);
                i++;
            }
        }
    }
    public void serverUpdate(float tpf){
        update(tpf);
        ArrayList<Entity> collisions = new ArrayList();
        // Do collision checking for all remaining entities
        int i = 0;
        Entity t;   // Temp entity
        while(i < entities.size()){
            t = entities.get(i);
            t.checkCollisions(Sys.getNetwork(), quadTree.retrieve(collisions, t));
            i++;
        }
    }
    
    public void updateChunk(ChunkData d){
        Vector2i key = d.getKey();
        Chunk chunk = new Chunk(node, key);
        chunk.generateBlocks(d.getBlocks());
        chunkMap.put(key, chunk);
    }
    
    public void reloadChunks(Chunk chunk){
        Vector2i key = chunk.getKey();
        int x = key.x - UNLOAD_DISTANCE;
        int y;
        Vector2i temp;
        Chunk c;
        while(x < key.x+UNLOAD_DISTANCE){
            y = key.y - UNLOAD_DISTANCE;
            while(y < key.y+UNLOAD_DISTANCE){
                temp = new Vector2i(x, y);
                c = chunkMap.get(temp);
                if(c != null && !chunk.getKey().within(temp, LOAD_DISTANCE)){
                    c.unload();
                }else if(c != null && !c.loaded()){
                    c.load();
                }
                y++;
            }
            x++;
        }
    }
    public void reloadChunks(Player p){
        Vector2f loc = p.getLocation();
        Chunk chunk = getChunk(loc);
    }
    
    // World generation algorithm
    public void generate(){
        Sys.setWorld(this);
        Vector2i key;
        int x = 0-(SIZE_X/2);
        int y;
        while(x < SIZE_X/2){
            y = 0-(SIZE_Y/2);
            while(y < SIZE_Y/2){
                key = new Vector2i(x, y);
                Chunk chunk = new Chunk(node, key);
                chunk.generateBlocks();
                chunkMap.put(key, chunk);
                y++;
            }
            x++;
        }
    }
}
