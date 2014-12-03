package mathjetpack.entity;

import mathjetpack.images.Images;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Wall extends Entity {
    
    private int mBricks;
    
    private int mBrickHeight;

    /**
     * Initializes the Wall
     */
    public Wall(int bricks) {
	super();

	setImage(Images.getImage("/entities/wall.png"));
	setType(Entity.Type.WALL);

	mBricks = bricks;
	mBrickHeight = 38;

	setWidth(60);
	setHeight(mBrickHeight * mBricks);	
    }

    public void setBricks(int bricks) {
	mBricks = bricks;
    }

    /**
     * Draws this entity
     *
     * @param g The graphics onto which to draw the entity
     */
    @Override
    public void draw(Graphics2D g) {
	
	for(int y = (int) mPosition.y; y < mPosition.y + mHeight; y += mBrickHeight)
	    g.drawImage(mImage, (int) mPosition.x, y, mWidth, mBrickHeight, null);

	if(mTestCollition) {
	    g.setColor(Color.WHITE);
	    g.drawRect((int) mPosition.x, (int) mPosition.y, mWidth, mHeight);
	}
    }

}
