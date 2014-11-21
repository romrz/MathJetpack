package mathjetpack.entity;

import java.awt.Graphics2D;
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

    /**
     * Constructor.
     * Initializes its attributes with the given animation's number
     *
     * @param animations Then number of animations for this entity
     */
    public Entity() {
        mWidth = mHeight = 0;

        mPosition = new Vector2();
        mVelocity = new Vector2();
        mAcceleration = new Vector2();
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
     * Integrates the entity in time by the given amount.
     * This method uses the Euler integration method
     *
     * @param duration The duration of the frame
     */
    public void move(double duration) {
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

        g.drawImage(mImage, (int) mPosition.x, (int) mPosition.y, mWidth, mHeight, null);

    }
}
