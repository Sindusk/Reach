package polarity.shared.items.data;

import com.jme3.math.ColorRGBA;
import com.jme3.network.serializing.Serializable;
import polarity.shared.items.Inventory;
import polarity.shared.spellforge.nodes.SpellNodeData;

/**
 *
 * @author SinisteRing
 */
@Serializable
public class SpellNodeItemData extends ItemData {
    protected SpellNodeData data;
    
    public SpellNodeItemData(){}    // For serialization
    public SpellNodeItemData(Inventory inv, int itemLevel, SpellNodeData data){
        super(inv, itemLevel, data.getIcon());
        this.data = data;
        this.name = data.getName();
        this.archetype = "Spell Node";
        this.archetypeColor = ColorRGBA.Red;
        this.type = data.getType();
        this.typeColor = data.getTypeColor();
        this.properties = data.genProperties(itemLevel);
    }
    public SpellNodeData getData(){
        data.setItem(this);
        return data;
    }
}
