package mathjetpack.entity;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

import mathjetpack.Vector2;


public class AnimatedEntity extends Entity {

    /* Entity Animation */
    // Current animation
    protected AnimationInfo mCurrentAnimation;
    protected int mAnimationIndex;
    // Animations fot this entity
    private ArrayList<AnimationInfo> mAnimations;
    // Total frames of the sprite sheet image
    private int mFrames;
    // Columns on the sprite sheet
    private int mColumns;
    
    /**
     * Constructor.
     * Initializes its attributes with the given animation's number
     *
     */
    public AnimatedEntity() {
        super();

     	// Default animation settings
	mAnimations = new ArrayList<AnimationInfo>();
	
	mFrames = 1;
	mColumns = 1;
    }

    /**
     * Adds a new animation info
     *
     * @param animation The AnimationInfo containig the necesary
     *        information for a nes animation
     */
    public void addAnimation(AnimationInfo animation) {
	mAnimations.add(animation);
    }

    /**
     * Sets the current animation
     */
    public void setAnimation(int a) {
	mAnimationIndex = a > 0 ? a - 1 : 0;
        mCurrentAnimation = mAnimations.get(mAnimationIndex);
    }

    /**
     * Sets the number of images in the sprite sheet
     * @param frames
     */
    public void setImageFrames(int frames) {
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
     * Animates the entity
     */
    public void animate(double duration) {
	mCurrentAnimation.update(duration);
    }

    /**
     * Integrates the entity in time by the given amount.
     * This method uses the Euler integration method
     *
     * @param duration The duration of the frame
     */
    public void move(double duration) {
	super.move(duration);

	animate(duration);
    }

    /**
     * Draws this entity
     *
     * @param g The graphics onto which to draw the entity
     */
    public void draw(Graphics2D g) {

	int frameX = (mCurrentAnimation.getCurrentFrame() % mColumns) * mWidth;
	int frameY = (mCurrentAnimation.getCurrentFrame() / mColumns) * mHeight;

        g.drawImage(mImage, (int) mPosition.x, (int) mPosition.y, (int) mPosition.x + mWidth, (int) mPosition.y + mHeight,
		    frameX, frameY, frameX + mWidth, frameY + mHeight, null);

	if(mTestCollition) {
	    g.setColor(Color.WHITE);
	    g.drawRect((int) mPosition.x, (int) mPosition.y, mWidth, mHeight);
	}

    }
}
