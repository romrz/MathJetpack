package mathjetpack.entity;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mathjetpack.Vector2;


public class Entity {

    // Entity dimensions
    protected int mWidth;
    protected int mHeight;
    
    // Entity inverse mass
    private double mInverseMass;

    // Entity position
    protected Vector2 mPosition;
    // Entity velocity
    protected Vector2 mVelocity;
    // Entity Acceleration
    protected Vector2 mAcceleration;
    
    // Auxiliary in accumulation the the resulting acceleration
    protected Vector2 mResultingAcc;
    // Force accumulator to be applied to the next integration loop
    protected Vector2 mForceAccum;

    /*
      Relative velocity.
      It is the player's velocity.
      It's used to do the map scrolling so all the entities have the
      same base, or relative, velocity.
     */
    protected Vector2 mRelativeVelocity;
    
    // Image for this entity
    protected BufferedImage mImage;

    public static enum Type {PLAYER, WALL, QBOX, COIN};
    protected Type mType;

    protected boolean mCollidable;
    protected boolean mVisible;
    protected boolean mAlive;
    
    // Test collition
    protected boolean mTestCollition = false;

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
	mResultingAcc = new Vector2();
	mForceAccum = new Vector2();
	
	setInverseMass(0.0);

	mCollidable = true;
	mVisible = true;
	mAlive = true;
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

    public void setTop(int top) {
	mPosition.y = top;
    }

    public int getTop() {
	return (int) mPosition.y;
    }

    public void setBottom(int bottom) {
	mPosition.y = bottom - mHeight;
    }

    public int getBottom() {
	return (int) mPosition.y + mHeight;
    }

    public void setLeft(int left) {
	mPosition.x = left;
    }

    public int getLeft() {
	return (int) mPosition.x;
    }

    public void setRight(int right) {
	mPosition.x = right - mWidth;
    }

    public int getRight() {
	return (int) mPosition.x + mWidth;
    }

    public void setMass(double mass) {
	mInverseMass = 1.0 / mass;
    }

    public void setInverseMass(double inverseMass) {
	mInverseMass = inverseMass;
    }

    public double getMass() {
	return 1.0 / mInverseMass;
    }

    public void setPosition(double x, double y) {
        mPosition.x = x;
        mPosition.y = y;
    }

    public void setVelocity(double x, double y) {
        mVelocity.x = x;
        mVelocity.y = y;
    }

    public void setVY(double vy) {
	mVelocity.y = vy;
    }

    public void setVX(double vx) {
	mVelocity.x = vx;
    }

    public double getVY() {
	return mVelocity.y;
    }

    public double getVX() {
	return mVelocity.x;
    }

    public final Vector2 getVelocity() {
	return mVelocity;
    }

    public void setAcceleration(double x, double y) {
        mAcceleration.x = x;
        mAcceleration.y = y;
    }

    public void addAcceleration(double x, double y) {
	mAcceleration.x += x;
	mAcceleration.y += y;
    }

    public void addForce(Vector2 force) {
	mForceAccum.addVector(force);
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

    public void setType(Type type) {
	mType = type;
    }

    public Type getType() {
	return mType;
    }

    public void setCollidable(boolean collidable) {
	mCollidable = collidable;
    }
    
    public boolean isCollidable() {
	return mCollidable;
    }

    public void setVisible(boolean visible) {
	mVisible = visible;
    }

    public boolean isVisible() {
	return mVisible;
    }

    public void setAlive(boolean alive) {
	mAlive = alive;
    }

    public boolean isAlive() {
	return mAlive;
    }

    public boolean collidesWith(Entity e) {
	return getRight() > e.getLeft() && getLeft() < e.getRight()
	    && getBottom() > e.getTop() && getTop() < e.getBottom()
	    && this != e;
    }

    public void testCollition() {
	mTestCollition = true;
    }
    
    /**
     * Integrates the entity in time by the given amount.
     * This method uses the Euler integration method
     *
     * @param duration The duration of the frame
     */
    public void move(double duration) {
	
	mResultingAcc.addVector(mAcceleration);
	mResultingAcc.addScaledVector(mForceAccum, mInverseMass);

        // Updates the position from the velocity
	mPosition.x += (mVelocity.x - mRelativeVelocity.x) * duration;
	mPosition.y += (mVelocity.y) * duration;
        // Updates the velocity from acceleration
        mVelocity.addScaledVector(mResultingAcc, duration);

	// Clear the accumulators
	mForceAccum.clear();
	mResultingAcc.clear();
    }

    /**
     * Draws this entity
     *
     * @param g The graphics onto which to draw the entity
     */
    public void draw(Graphics2D g) {

	g.drawImage(mImage, (int) mPosition.x, (int) mPosition.y, mWidth, mHeight, null);

	if(mTestCollition) {
	    g.setColor(Color.WHITE);
	    g.drawRect((int) mPosition.x, (int) mPosition.y, mWidth, mHeight);
	}
    }
}
