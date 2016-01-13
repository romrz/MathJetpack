package mathjetpack.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import mathjetpack.Vector2;

public class Player extends AnimatedEntity {

    // Player name
    private String mName;
    
    // Coins taken
    private int mCoins;
    // Points made
    private int mPoints;
    
    private boolean thrusting = false;
    private boolean running = false;

    /**
     * Initializes the player's attributes
     */
    public Player(BufferedImage image) {
	super();

	setImage(image);
	setWidth(48);
	setHeight(68);
	setMass(20);
	setType(Entity.Type.PLAYER);
		
	// Add animations
	addAnimation(new AnimationInfo(0, 3, 1, 12, true));
	addAnimation(new AnimationInfo(3, 2, 1, 6, true));
	addAnimation(new AnimationInfo(6, 1, 1, 1, true));
	addAnimation(new AnimationInfo(5, 1, 1, 1, true));
	
	setColumns(3);
	setImageFrames(7);

	reset();
    }

    public void setName(String name) {
	mName = name;
    }

    public String getName() {
	return mName;
    }

    public void setCoins(int coins) {
	mCoins = coins;
    }

    public void addCoin() {
	mCoins++;
    }

    public int getCoins() {
	return mCoins;
    }

    public void setPoints(int points) {
	mPoints = points;
    }

    public void addPoint() {
	mPoints++;
    }

    public int getPoints() {
	return mPoints;
    }    

    /**
     * Resets the position, coins, points
     * and the state of the player
     */
    public void reset() {
	setPosition(100, 350);
	setVelocity(200, 0);
	running = false;
	mPoints = 0;
	mCoins = 0;
	removeThrust();
    }

    /**
     * Apply thrust to the jetpack and changes the animation
     */
    public void applyThrust() {
	if(!thrusting) {

	    setAnimation(2);

	    // If the player is falling and then apply thrust, a big force is applied
	    // to slow down the player. It makes the game more playable. Otherwise
	    // it would take much longer to slow the player
	    if(mVelocity.y > 0)
		mVelocity.y -= mVelocity.y * 0.5;
		//addForce(new Vector2(0, -(getMass()  * ((mVelocity.y - mVelocity.y * 0.6) / 0.01))));

	    setAcceleration(0, -600);
	    running = false;
	    thrusting = true;
	}
    }

    /**
     * Removes the thrust and changes the animation
     */
    public void removeThrust() {
	setAnimation(3);
	setAcceleration(0, 600);
	thrusting = false;
    }
    
    /**
     * Sets whether the player is running or not
     * and changes the animation
     */
    public void running(boolean r) {
	if((running = r) == true && !thrusting)
	    setAnimation(1);
    }

    /**
     * Updates the player's position
     */
    public void move(double duration) {
	super.move(duration);

	if(mVelocity.y > 0 && !thrusting && !running && mAnimationIndex != 3)
	    setAnimation(4);
    }
}
