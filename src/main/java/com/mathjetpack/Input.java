package mathjetpack;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;

/**
 * Created by rom on 13/10/14.
 */

public class Input implements KeyListener, MouseListener {

    private Game mGame;

    public Input(final Game game) {
        mGame = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
	    mGame.playSound("jetpack", true);
	    mGame.getPlayer().applyThrust();
	}

    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Shows or hides the debug info pressing 'D'
        if(e.getKeyCode() == KeyEvent.VK_D) {
            mGame.toggleDebugInfo();
            System.out.println("KEY EVENT");
        }

	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
	    mGame.stopSound("jetpack");
	    mGame.getPlayer().removeThrust();
	}

        // Exits the game
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
	if(mGame.getCurrentMenu() != null)
	    mGame.getCurrentMenu().inputHandling(e);
	
	if(mGame.getPauseButton().isPressed(e.getX(), e.getY()))
	    if(mGame.getState() == Game.States.PAUSED)
		mGame.resume();
	    else
		mGame.pause();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {}

}
