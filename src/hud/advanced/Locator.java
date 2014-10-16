/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hud.advanced;

import character.Player;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import hud.HUDElement;
import tools.SinText;
import ui.Label;

/**
 * HUD Element for displaying current location of the player.
 * @author Sindusk
 */
public class Locator extends HUDElement {
    protected Label label;
    
    public Locator(Node parent, Vector2f location, float height){
        super(parent, location);
        label = new Label(node, Vector2f.ZERO, height, 0);
        label.setTextAlign(SinText.Alignment.Left);
        label.setColor(ColorRGBA.White);
    }
    
    @Override
    public void update(Player player, float tpf){
        // Rounding is done as a roundabout method for obtaining 2 decimal places
        label.setText("X: "+Math.round(player.getLocation().x*100)/100f+
                "\nY:"+Math.round(player.getLocation().y*100)/100f);
    }
}