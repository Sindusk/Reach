/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import tools.GeoFactory;
import tools.SinText;

/**
 *
 * @author SinisteRing
 */
public class Label extends UIElement {
    protected SinText label;
    
    public Label(Node parent, Vector2f center, float height, float z){
        super(parent, center, 0, height, z);
        label = GeoFactory.createSinText(node, height, new Vector3f(0, 0, 0.01f), "TNR32", "Testing", ColorRGBA.Blue, SinText.Alignment.Center);
    }
    
    public void setText(String text){
        label.setText(text);
    }
    @Override
    public void setColor(ColorRGBA color){
        label.setColor(color);
    }
    public void setTextAlign(SinText.Alignment align){
        label.setAlignment(align);
    }
}