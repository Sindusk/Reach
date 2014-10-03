/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import entity.Entity;
import java.awt.Rectangle;
import java.util.ArrayList;
import tools.Util.Vector2i;

/**
 *
 * @author SinisteRing
 */
public class QuadTree {
    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;
    
    private int level;
    private ArrayList<Entity> objects;
    private Rectangle bounds;
    private QuadTree[] nodes;
    private Vector2i location;
    
    public QuadTree(int pLevel, Rectangle pBounds){
        level = pLevel;
        objects = new ArrayList();
        bounds = pBounds;
        nodes = new QuadTree[4];
        location = new Vector2i(0, 0);
    }
    
    public void clear() {
        objects.clear();
        int i = 0;
        while(i < nodes.length){
            if (nodes[i] != null){
                nodes[i].clear();
                nodes[i] = null;
            }
            i++;
        }
    }
    
    public void split(){
        // x to z = horizontal (left to right)
        // y to w = vertical (down to up)
        int subWidth = (int)(bounds.getWidth()/2);
        int subHeight = (int)(bounds.getHeight()/2);
        int x = location.x;
        int y = location.y;
        
        nodes[0] = new QuadTree(level+1, new Rectangle(x+subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y+subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x+subWidth, y+subHeight, subWidth, subHeight));
    }
    
    private int getIndex(Entity e){
        Rectangle pRect = e.getBounds();
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth()/2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight()/2);
        
        // Object can completely fit within the top quadrants
        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);
        
        // Object can completely fit within the left quadrants
        if(pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint){
            if(topQuadrant){
                index = 1;
            }else if(bottomQuadrant){
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if(pRect.getX() > verticalMidpoint){
            if(topQuadrant){
                index = 0;
            }else if (bottomQuadrant){
                index = 3;
            }
        }
        
        return index;
    }
    
    public void insert(Entity e){
        if(nodes[0] != null){
            int index = getIndex(e);
            
            if(index != -1){
                nodes[index].insert(e);
                
                return;
            }
        }
        
        objects.add(e);
        
        if(objects.size() > MAX_OBJECTS && level < MAX_LEVELS){
            if(nodes[0] == null){
                split();
            }
            
            int i = 0;
            while(i < objects.size()){
                int index = getIndex(objects.get(i));
                if(index != -1){
                    nodes[index].insert(objects.remove(i));
                }
                else{
                    i++;
                }
            }
        }
    }
    
    public ArrayList retrieve(ArrayList returnObjects, Entity e){
        int index = getIndex(e);
        if(index != -1 && nodes[0] != null){
            nodes[index].retrieve(returnObjects, e);
        }
        
        returnObjects.addAll(objects);
        
        return returnObjects;
    }
}
