package mathjetpack.ui;

import mathjetpack.Vector2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * This class implements the functionality of a button
 */
public class Button {

    // Text of the button
    private String mText;
    
    private BufferedImage mBackgroundImage;

    private Vector2 mPosition;
    
    private int mWidth;
    private int mHeight;

    public Button(String t) {
        mText = t;
        
        mWidth = 110;
        mHeight = 30;

	mPosition = new Vector2();
    }

    public String getText() {
        return mText;
    }

    public void setWidth(int w) {
        mWidth = w;
    }

    public void setHeight(int h) {
        mHeight = h;
    }
    
    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setPosition(double x, double y) {
        mPosition.x = x;
        mPosition.y = y;
    }

    public void setX(double x) {
	mPosition.x = x;
    }
    
    public void setY(double y) {
	mPosition.y = y;
    }

    /**
     * Checks if the button was pressed
     */
    public boolean isPressed(int x, int y) {
        return x >= mPosition.x - mWidth / 2 && x <= mPosition.x + mWidth / 2
	    && y >= mPosition.y - mHeight / 2 && y <= mPosition.y + mHeight / 2;
    }

    /**
     * Draws the button.
     * Draws the text centered.
     */
    public void draw(Graphics2D g) {
        int x = (int) mPosition.x - mWidth / 2;
        int y = (int) mPosition.y - mHeight / 2;

        g.setColor(Color.RED);
        g.fillRect(x, y, mWidth, mHeight);
     
	Rectangle2D rect = g.getFontMetrics().getStringBounds(mText, g);
	
	x = (int) (mPosition.x - rect.getWidth() / 2);
	y = (int) (mPosition.y + rect.getHeight() / 2);

        g.setColor(Color.WHITE);
        g.drawString(mText, x, y);
    }
}
