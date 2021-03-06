package polarity.shared.spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import polarity.shared.actions.Action;
import polarity.shared.spellforge.PulseHandler;
import polarity.shared.spellforge.SpellMatrix;
import polarity.shared.spellforge.nodes.conduits.EffectConduitData;
import polarity.shared.tools.Util;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class EffectData extends SpellNodeData {
    protected float multiplier = 1;
    protected float cost = 1;
    
    public EffectData(){
        init();
    }
    public EffectData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        init();
    }
    private void init(){
        type = "Effect";
        //typeColor = new ColorRGBA(0.22f, 0.80f, 0, 1);  // Green
        typeColor = new ColorRGBA(0.16f, 0.60f, 0, 1);  // Green
    }
    
    public Action getAction(final float mult){
        Util.log("[EffectData] <getAction> Critical error: no override on getAction()!");
        return null;
    }
    
    public float getCost(){
        return cost;
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof EffectConduitData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canTravel(SpellNodeData data){
        if(data instanceof EffectConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public String getText(){
        return Math.round(multiplier*100f)+"%";
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
        if(granted.size() > 0){
            multiplier = Math.min(1, 2f/granted.size());
        }
        CoreData coreData;
        for(SpellNodeData data : granted){
            if(data instanceof CoreData){
                coreData = (CoreData) data;
                coreData.addEffect(this, multiplier);
            }
        }
    }
    
    @Override
    public void update(float tpf){
        // Not Needed
    }
}
