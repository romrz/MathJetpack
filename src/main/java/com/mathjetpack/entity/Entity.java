package mathjetpack.entity;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import mathjetpack.Vector2;


public class Entity {

    // Entity dimensions
    protected int mWidth;
    protected int mHeight;

    // Entity inverse mass
    //private double mInverseMass;

    // Entity position
    protected Vector2 mPosition;
    // Entity velocity
    protected Vector2 mVelocity;
    // Entity Acceleration
    protected Vector2 mAcceleration;

    /*
      Relative velocity.
      It is the player's velocity.
      It's used to do the map scrolling so all the entities have the
      same base, or relative, velocity.
     */
    protected Vector2 mRelativeVelocity;


    // Image for this entity
    protected BufferedImage mImage;

    /* Entity Animation */

    // Total frames of the animation
    private int mFrames;
    // Current frame of the animation
    private int mCurrentFrame = 0;
    // Frame duration
    private double mFrameTime;
    // Time counter
    private double mTimeCount;
    // Columns on the sprite sheet
    private int mColumns;
    

    /**
     * Constructor.
     * Initializes its attributes with default values
     */
    public Entity() {
        mWidth = mHeight = 0;

        mPosition = new Vector2();
        mVelocity = new Vector2();
        mAcceleration = new Vector2();

	// Default animation settings
	setAnimationFPS(24);
	setAnimationFrames(1);
	setColumns(1);
    }

    public void setWidth(int width) {
        mWidth = width > 0 ? width : 0;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setHeight(int height) {
        mHeight = height > 0 ? height : 0;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setPosition(double x, double y) {
        mPosition.x = x;
        mPosition.y = y;
    }

    public void setVelocity(double x, double y) {
        mVelocity.x = x;
        mVelocity.y = y;
    }

    public final Vector2 getVelocity() {
	return mVelocity;
    }

    public void setAcceleration(double x, double y) {
        mAcceleration.x = x;
        mAcceleration.y = y;
    }

    public void setRelativeVelocity(Vector2 v) {
	mRelativeVelocity = v;
    }

    public void setImage(BufferedImage image) {
	mImage = image;
    }

    public BufferedImage getImage() {
        return mImage;
    }

    /**
     * Sets the number of images in the sprite sheet
     * @param frames
     */
    public void setAnimationFrames(int frames) {
	mFrames = frames;
    }

    /**
     * Sets the number of columns in the spritesheet
     * @param columns
     */
    public void setColumns(int columns) {
	mColumns = columns;
    }

    /**
     * Sets the speed of the animation in frames per second
     * @param fps
     */
    public void setAnimationFPS(int fps) {
	mFrameTime = 1.0 / (double) fps;
    }
    
    /**
     * Animates the entity
     */
    public void animate(double duration) {
	
	if(mFrames > 1) {
	    
	    mTimeCount += duration;

	    if(mTimeCount >= mFrameTime) {
		
		mTimeCount -= mFrameTime;

		mCurrentFrame++;

		if(mCurrentFrame > mFrames - 1)
		    mCurrentFrame = 0;
	    }
	}
    }

    /**
     * Integrates the entity in time by the given amount.
     * This method uses the Euler integration method
     *
     * @param duration The duration of the frame
     */
    public void move(double duration) {

	// Animates the entity
	animate(duration);

        // Updates the position from the velocity
	mPosition.x += (mVelocity.x - mRelativeVelocity.x) * duration;
	mPosition.y += (mVelocity.y - mRelativeVelocity.y) * duration;
	// mPosition.addScaledVector(mRelativeVelocity, duration);
        // mPosition.addScaledVector(mVelocity, duration);

	
        // Updates the velocity from acceleration
        mVelocity.addScaledVector(mAcceleration, duration);
    }

    /**
     * Draws this entity
     *
     * @param g The graphics onto which to draw the entity
     */
    public void draw(Graphics2D g) {

	int frameX = (mCurrentFrame % mColumns) * mWidth;
	int frameY = (mCurrentFrame / mColumns) * mHeight;

        g.drawImage(mImage, (int) mPosition.x, (int) mPosition.y, (int) mPosition.x + mWidth, (int) mPosition.y + mHeight,
		    frameX, frameY, frameX + mWidth, frameY + mHeight, null);

    }

}
