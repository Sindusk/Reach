package polarity.shared.ui.items;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.equipment.Equipment;
import polarity.shared.equipment.EquipmentData;
import java.util.HashMap;
import polarity.shared.tools.Util;
import polarity.shared.tools.Vector2i;
import polarity.shared.ui.Button;
import polarity.shared.ui.interfaces.Draggable;
import polarity.shared.ui.interfaces.TooltipInfo;

/**
 *
 * @author SinisteRing
 */
public class EquipmentButton extends Button implements TooltipInfo, Draggable {
    protected Equipment equipment;
    protected EquipmentData gear;
    
    public EquipmentButton(Node parent, Equipment equipment, EquipmentData gear, Vector2f loc, float x, float y, float z){
        super(parent, Util.getEquipmentIcon(gear.getIcon()), loc, x, y, z);
        this.equipment = equipment;
        this.gear = gear;
    }
    
    public EquipmentData getGear(){
        return gear;
    }
    
    public void resetDragging(){
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, localZ));
    }
    public HashMap<Vector2i,ColorRGBA> getColorMap(){
        return gear.getColorMap();
    }
    public String getTooltip(){
        return gear.getTooltip();
    }
}
