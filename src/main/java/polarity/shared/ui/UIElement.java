package polarity.shared.ui;

import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.tools.Util;

/**
 *
 * @author SinisteRing
 */
public class UIElement {
    protected Node node = new Node("UIElement");
    protected Geometry geo;
    protected Vector3f offset;
    protected Vector2f loc;
    protected Vector4f bounds;
    protected float sizeX;
    protected float sizeY;
    protected float localZ;
    protected float priority;
    
    /**
     * 
     * @param parent Parent node. Should usually be a Root or GUI node.
     * @param center Position in space on the screen to center the UIElement.
     * @param x Size in units X
     * @param y Size in units Y
     * @param z Priority. Overlapping elements will display the element with the higher Z value on top.
     */
    public UIElement(Node parent, Vector2f center, float x, float y, float z){
        sizeX = x;
        sizeY = y;
        localZ = z;
        x /= 2.0f;  // Divide size by half, since the objects are drawn
        y /= 2.0f;  // from the center outward (i.e. 100px object would be 50px in both directions)
        // This whole series of recursive offset checking is used to figure out the bounds of a control.
        // By checking the translation of every node above it, we can figure out where on the screen
        // any given element is. This allows us to create a bounds which detects player polarity.shared.input for that area.
        // This process will also assist in figuring out proper priority of overlapping elements.
        offset = Util.getOffset(parent);
        node.setLocalTranslation(new Vector3f(center.x, center.y, z));
        // Using the offsets determined by the recursive loop, we now have proper bounds for the screen
        loc = center;
        bounds = new Vector4f(offset.x+center.x-x, offset.x+center.x+x, offset.y+center.y-y, offset.y+center.y+y);
        priority = offset.z+z;
        parent.attachChild(node);
    }
    
    // Getters
    public Node getNode(){
        return node;
    }
    public Vector3f getOffset(){
        return offset;
    }
    public float getPriority(){
        return priority;
    }
    
    // Action definition placeholder
    public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
        Util.log("Error 3: No override for onAction in class "+this.getClass().toString());
    }
    public void onKeyEvent(KeyInputEvent evt){
        Util.log("Error 3: No override for onKeyEvent in class "+this.getClass().toString());
    }
    
    // Changes the color of the polarity.shared.main geometry
    public void setColor(ColorRGBA color){
        geo.setMaterial(Util.getMaterial(color));
    }
    // Update the bounds (when UI elements move)
    public void updateBounds(float x, float y){
        bounds = new Vector4f(bounds.x+x, bounds.y+x, bounds.z+y, bounds.w+y);
    }
    
    public void moveWithOffset(Vector2f cursorLoc){
        float x = cursorLoc.x-offset.x;
        float y = cursorLoc.y-offset.y;
        node.setLocalTranslation(x, y, 5);
    }
    
    // Check if the location given is within the bounds of the current UI element.
    public boolean withinBounds(Vector2f loc){
        if(loc.x >= bounds.x && loc.x <= bounds.y){
            if(loc.y >= bounds.z && loc.y <= bounds.w){
                return true;
            }
        }
        return false;
    }
    
    public boolean destroy(){
        return node.removeFromParent();
    }
}
