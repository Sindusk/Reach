package polarity.shared.input;

import com.jme3.input.Input;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author SinisteRing
 */
public enum Bind {
    // Mouse polarity.shared.input
    MouseLeft(MouseInput.AXIS_X, true),
    MouseRight(MouseInput.AXIS_X, false),
    MouseUp(MouseInput.AXIS_Y, false),
    MouseDown(MouseInput.AXIS_Y, true),
    LClick(MouseInput.BUTTON_LEFT, MouseInput.class),
    RClick(MouseInput.BUTTON_RIGHT, MouseInput.class),
    // Directions (WASD)
    D(KeyInput.KEY_D, KeyInput.class),
    A(KeyInput.KEY_A, KeyInput.class),
    W(KeyInput.KEY_W, KeyInput.class),
    S(KeyInput.KEY_S, KeyInput.class),
    // Important Function Keys
    Enter(KeyInput.KEY_RETURN, KeyInput.class),
    Escape(KeyInput.KEY_ESCAPE, KeyInput.class),
    // Numbers
    One(KeyInput.KEY_1, KeyInput.class),
    Two(KeyInput.KEY_2, KeyInput.class),
    Three(KeyInput.KEY_3, KeyInput.class),
    Four(KeyInput.KEY_4, KeyInput.class),
    // Extra
    B(KeyInput.KEY_B, KeyInput.class),
    J(KeyInput.KEY_J, KeyInput.class),
    K(KeyInput.KEY_K, KeyInput.class),
    M(KeyInput.KEY_M, KeyInput.class),
    N(KeyInput.KEY_N, KeyInput.class),
    Y(KeyInput.KEY_Y, KeyInput.class),
    // Scroll
    ScrollUp(MouseInput.AXIS_WHEEL, false),
    ScrollDown(MouseInput.AXIS_WHEEL, true),
    // Unused
    ArrowUp(KeyInput.KEY_UP, KeyInput.class),
    ArrowDown(KeyInput.KEY_DOWN, KeyInput.class),
    ArrowRight(KeyInput.KEY_RIGHT, KeyInput.class),
    ArrowLeft(KeyInput.KEY_LEFT, KeyInput.class);
    
    public final String mapping;
    public final int key;
    public final Trigger trigger;
    
    Bind(int key, boolean dir){
        mapping = this.toString();
        this.key = key;
        trigger = new MouseAxisTrigger(key, dir);
    }
    Bind(int key, Class<? extends Input> input){
        mapping = this.toString();
        this.key = key;
        if(input == MouseInput.class){
            trigger = new MouseButtonTrigger(key);
        }else{
            trigger = new KeyTrigger(key);
        }
    }
}
