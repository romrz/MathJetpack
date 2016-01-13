package mathjetpack.ui;

import mathjetpack.Game;
import mathjetpack.entity.Player;
import mathjetpack.Vector2;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * This class creates game menus, with images and buttons
 */
public class Menu {

    protected Game mGame;

    // Background Image (optional)
    protected BufferedImage mBackgroundImage;

    // Menu Buttons
    private LinkedList<Button> mButtons;

    protected Vector2 mPosition;

    protected int mWidth;
    protected int mHeight;

    protected int mMargin = 10;

    public Menu(Game game) {
        mButtons = new LinkedList<Button>();

        mWidth = game.getWidth() / 4;
        mHeight = game.getHeight() / 3;

        mPosition = new Vector2(game.getWidth() / 2, game.getHeight() / 2);

        mGame = game;
    }

    public void setBackground(BufferedImage bg) {
        mBackgroundImage = bg;
    }

    public void setPosition(int x, int y) {
        mPosition.x = x;
        mPosition.y = y;
    }

    public void setWidth(int w) {
        mWidth = w;
    }
    
    public void setHeight(int h) {
        mHeight = h;
    }

    /**
     * Adds a Button to this menu and adjust
     * the existing buttons to have all an equal
     * width an height
     */
    public void addButton(Button btn) {
        mButtons.add(btn);
        btn.setWidth(mWidth - 2 * mMargin);
	
        int n = mButtons.size();
        int y = (int) mPosition.y - mHeight / 2;
        for(Button b : mButtons) {
            b.setHeight(mHeight / n - 2 * mMargin);
            b.setPosition(mPosition.x, y + mMargin);
	    
            y += b.getHeight() + 2 * mMargin;
        }
    }

    /**
     * Handles the user input on this menu
     */
    public void inputHandling(MouseEvent e) {
        for (Button b : mButtons) {
            if(b.isPressed(e.getX(), e.getY())) {
		
                mGame.playSound("select", false);

                if(b.getText().equals("Jugar")) {

                    Player p = mGame.getPlayer();
		    
                    mGame.play();
                }
                else if(b.getText().equals("Pausar")) mGame.pause();
                else if(b.getText().equals("Reanudar")) mGame.resume();
                else if(b.getText().equals("Reiniciar")) mGame.play();
                else if(b.getText().equals("Menu Principal")) mGame.mainMenu();
                else if(b.getText().equals("Salir")) mGame.quit();
            }
        }
    }

    public void draw(Graphics2D g) {
        if(mBackgroundImage != null) {
            g.drawImage(mBackgroundImage, (int) mPosition.x - mWidth / 2, (int) mPosition.y - mHeight / 2, mWidth, mHeight, null);
        }

        for (Button b : mButtons) {
            b.draw(g);
        }
    }
}
