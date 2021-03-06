package polarity.shared.netdata;

import polarity.shared.world.blocks.BlockData;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import polarity.shared.tools.Vector2i;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class ChunkData extends AbstractMessage {
    protected int x;
    protected int y;
    protected ArrayList<ArrayList<BlockData>> blocks;
    public ChunkData(){}
    public ChunkData(int x, int y, ArrayList<ArrayList<BlockData>> blocks){
        this.x = x;
        this.y = y;
        this.blocks = blocks;
    }
    public Vector2i getKey(){
        return new Vector2i(x, y);
    }
    public ArrayList<ArrayList<BlockData>> getBlocks(){
        return blocks;
    }
}
