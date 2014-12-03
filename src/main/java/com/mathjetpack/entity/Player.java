package mathjetpack.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import mathjetpack.Vector2;

public class Player extends AnimatedEntity {

    private int mCoins;
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

    public void reset() {
	setPosition(100, 350);
	setVelocity(200, 0);
	running = false;
	mPoints = 0;
	mCoins = 0;
	removeThrust();
    }

    public void applyThrust() {

	//mSound.play("jetpack");

	if(!thrusting) {

	    setAnimation(2);

	    if(mVelocity.y > 0)
		addForce(new Vector2(0, -(getMass()  * ((mVelocity.y - mVelocity.y * 0.6) / 0.01))));

	    setAcceleration(0, -600);
	    running = false;
	    thrusting = true;
	}
    }

    public void removeThrust() {
	setAnimation(3);
	setAcceleration(0, 600);
	thrusting = false;
    }
    
    public void running(boolean r) {
	if((running = r) == true && !thrusting)
	    setAnimation(1);
    }

    public void move(double duration) {
	super.move(duration);

	if(mVelocity.y > 0 && !thrusting && !running && mAnimationIndex != 3)
	    setAnimation(4);
    }

}
