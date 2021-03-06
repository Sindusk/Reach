package polarity.shared.spellforge.nodes;

import com.jme3.math.ColorRGBA;
import com.jme3.network.HostedConnection;
import polarity.shared.spellforge.nodes.conduits.PowerConduitData;
import com.jme3.network.serializing.Serializable;
import polarity.shared.items.creation.ItemFactory;
import java.util.HashMap;
import polarity.shared.netdata.updates.GeneratorPowerUpdate;
import polarity.shared.spellforge.PulseHandler;
import polarity.shared.spellforge.SpellMatrix;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class GeneratorData extends SpellNodeData {
    protected float rate = 5f;
    protected float storedPower = 0;
    protected float maxPower = 100;
    
    public GeneratorData(){
        super();
        init();
    }
    public GeneratorData(SpellNodeData data){
        super(data.getX(), data.getY(), data.getLocation());
        init();
    }
    private void init(){
        type = "Generator";
        //typeColor = new ColorRGBA(0.76f, 0.15f, 0.97f, 1);  // Purple
        typeColor = new ColorRGBA(0.58f, 0.11f, 0.73f, 1);  // Purple
    }
    
    @Override
    public String getIcon(){
        return "generator";
    }
    public float getChargeCost(float tpf){
        float checkPower = storedPower+(rate*tpf);
        if(checkPower <= maxPower){
            return rate*tpf;
        }else{
            return maxPower-storedPower;
        }
    }
    public float getStoredPower(){
        return storedPower;
    }
    
    public void setStoredPower(float amount){
        this.storedPower = amount;
    }
    public void addPower(float tpf){
        storedPower += rate*tpf;
    }
    public void subtractPower(float amount){
        storedPower -= amount;
    }
    public void subtractPower(HostedConnection conn, int slot, float amount){
        subtractPower(amount);
        conn.send(new GeneratorPowerUpdate(slot, index, storedPower));
    }
    
    @Override
    public String getText(){
        return ""+Math.round(storedPower*100f)/100f;
    }
    
    @Override
    public boolean canProvide(SpellNodeData data){
        if(data instanceof CoreData){
            return true;
        }else if(data instanceof EffectData){
            return true;
        }else if(data instanceof ModifierData){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canConnect(SpellNodeData data){
        if(data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
    @Override
    public boolean canTravel(SpellNodeData data){
        if(data instanceof PowerConduitData){
            return true;
        }
        return false;
    }
    
    @Override
    public HashMap<String,Float> getSpellNodeProperties(){
        float roundedPower = Math.round(storedPower*100f)/100f;
        properties.put("Stored", roundedPower);
        return properties;
    }
    @Override
    public HashMap<String,Float> genProperties(int level){
        properties = new HashMap();
        rate = ItemFactory.leveledRandomFloat(5f, level, 2);
        properties.put("Power Rate", rate);
        maxPower = ItemFactory.leveledRandomFloat(100f, level, 1);
        properties.put("Maximum Power", maxPower);
        return properties;
    }
    
    @Override
    public void recalculate(SpellMatrix matrix){
        PulseHandler handler = new PulseHandler(matrix, this);
        handler.createPulse(this);
        granted = handler.getGranted();
        for(SpellNodeData data : granted){
            if(data instanceof CoreData){
                CoreData core = (CoreData) data;
                core.addPowerSource(this);
            }
        }
    }
    
    @Override
    public void update(float tpf){
        //storedPower += rate*tpf;
        if(storedPower > maxPower){
            storedPower = maxPower;
        }
    }
}
