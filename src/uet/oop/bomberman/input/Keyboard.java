package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;

public class Keyboard {
    public void creatKeyListener() {
        //gameScene.setOn
    }

    public boolean up, down, left, right, space;
    public boolean[] keys = new boolean[150];

    public void update() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
    }

    public void keyPressed(KeyEvent event) {
        try {
            keys[event.getKeyCode()] = true;
        } catch (ArrayIndexOutOfBoundsException aie) {
        }
    }

    public void keyReleased(KeyEvent event) {
        try {
            keys[event.getKeyCode()] = false;
        } catch (ArrayIndexOutOfBoundsException aie) {
        }
    }

    public void keyTyped(KeyEvent event) {

    }
}