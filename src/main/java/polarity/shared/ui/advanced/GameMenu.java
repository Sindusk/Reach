package polarity.shared.ui.advanced;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import polarity.shared.input.Bind;
import java.util.ArrayList;
import polarity.shared.tools.GeoFactory;
import polarity.shared.tools.Sys;
import polarity.shared.ui.Button;
import polarity.shared.ui.Menu;
import polarity.shared.ui.UIElement;

/**
 *
 * @author SinisteRing
 */
public class GameMenu extends Menu {
    protected Node parent;
    protected Geometry background;
    
    public GameMenu(Node parent, Vector2f loc, float z){
        super(parent, loc, z);
        this.parent = parent;
        background = GeoFactory.createBox(node, new Vector3f(Sys.width*0.5f, Sys.height*0.5f, 0), Vector3f.ZERO, new ColorRGBA(0,0,0,0.5f));
        node.removeFromParent();
        active = false;
    }
    
    public Button createReturnButton(final ArrayList<UIElement> ui){
        Button b = new Button(node, new Vector2f(0, Sys.height*0.08f), Sys.width*0.4f, Sys.height*0.05f, 0){
            @Override
            public void onAction(Vector2f cursorLoc, String bind, boolean down, float tpf){
                if(bind.equals(Bind.LClick.toString()) && down){
                    setVisible(ui, false);
                }
            }
        };
        b.setText("Return to Game");
        b.setColor(ColorRGBA.Red);
        addOption(ui, b);
        ui.remove(b);
        return b;
    }
    
    public void setVisible(ArrayList<UIElement> ui, boolean show){
        if(show){
            parent.attachChild(node);
            for(Button b : options){
                if(!ui.contains(b)){
                    ui.add(b);
                }
            }
        }else{
            node.removeFromParent();
            for(Button b : options){
                while(ui.contains(b)){
                    ui.remove(b);
                }
            }
        }
        active = show;
    }
}
