package polarity.shared.ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class Button extends UIElement {
    protected SinText text;
    
    public Button(Node parent, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        text = GeoFactory.createSinText(node, y*1.5f, new Vector3f(0, 0, 0.01f), "AW32", ColorRGBA.Blue, SinText.Alignment.Center);
        geo = GeoFactory.createBox(node, "test", new Vector3f(x, y, 0.1f), Vector3f.ZERO, ColorRGBA.Blue);
    }
    public Button(Node parent, String icon, Vector2f loc, float x, float y, float z){
        super(parent, loc, x, y, z);
        x /= 2.0f;
        y /= 2.0f;
        text = GeoFactory.createSinText(node, y*1.5f, new Vector3f(0, 0, 0.01f), "AW32", ColorRGBA.Blue, SinText.Alignment.Center);
        geo = GeoFactory.createBox(node, "test", new Vector3f(x, y, 0), Vector3f.ZERO, icon, new Vector2f(1, 1));
    }
    
    // Sets the location of the button (centered)
    public void setLocation(Vector2f loc){
        node.setLocalTranslation(new Vector3f(loc.x, loc.y, priority));
    }
    // Sets the text of the button
    public void setText(String str){
        text.setText(str);
    }
    // Sets the color of the text
    public void setTextColor(ColorRGBA color){
        text.setColor(color);
    }
    
    public Vector3f getLocation(){
        return node.getLocalTranslation().clone();
    }
}
