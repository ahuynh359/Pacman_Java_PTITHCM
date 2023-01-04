package pacman.ultis;

import pacman.GameManager;
import pacman.GamePlayPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    public static List<Key> keyList = new ArrayList<>();
    public Key kUp = new Key();
    public Key kDown = new Key();
    public Key kLeft = new Key();
    public Key kRight = new Key();

    public KeyHandler(GamePlayPanel g) {
        g.addKeyListener(this);

    }

    public void toogle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            kLeft.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            kRight.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            kUp.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            kDown.toggle(pressed);
        }
    }


    public class Key {
        public boolean isPressed;

        public Key() {
            keyList.add(this);
        }
        public void keyState(){
            System.out.println(isPressed);
        }

        public void toggle(boolean pressed) {
            if (pressed != isPressed) {
                isPressed = pressed;
            }

        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toogle(e, true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        toogle(e, false);

    }
}
