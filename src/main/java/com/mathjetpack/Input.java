package mathjetpack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by rom on 13/10/14.
 */

public class Input implements KeyListener {

    private Game mGame;

    public Input(final Game game) {
        mGame = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Shows or hides the debug info pressing 'D'
        if(e.getKeyCode() == KeyEvent.VK_D) {
            mGame.toggleDebugInfo();
            System.out.println("KEY EVENT");
        }

        // Exits the game

    }
}
