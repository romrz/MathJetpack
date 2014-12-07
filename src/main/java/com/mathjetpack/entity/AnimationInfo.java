package mathjetpack.entity;

/**
 * This class holds the animation info for each animation
 * of an animated entity
 */
public class AnimationInfo {

    // Start frame of the animation
    private int mStartFrame;
    // Total frames of the animation
    private int mFrames;
    // Current frame of the animation
    private int mCurrentFrame;
    // Animation direction. Forwards or backwards
    private int mAnimationDirection;
    // Animation looping
    private boolean mLoop;
    // Frame duration
    private double mFrameTime;
    // Auxiliary to keep the time count
    private double mTimeCount = 0.0;
    
    public AnimationInfo() {
	this(0, 1, 0, 24, false);
    }
        
    public AnimationInfo(int startFrame, int frames, int dir, int fps, boolean loop) {
	
	mCurrentFrame = startFrame;
	setStartFrame(startFrame);
	setFrames(frames);
	setDirection(dir);
	setFPS(fps);
	setLooping(loop);

    }

    /**
     * Sets the start frame relative to the entity's image frames
     *
     * @param frame
     */
    public void setStartFrame(int frame) {
	mStartFrame = frame >= 0 ? frame : 0;
    }

    /**
     * Gets the start frame
     *
     * @return The animation start frame, relative to the entity's image frames
     */
    public int getStartFrame() {
	return mStartFrame;
    }

    /**
     * Sets the number of frames of the animation
     *
     * @param frames
     */
    public void setFrames(int frames) {
	mFrames = frames > 0 ? frames : 1;
    }

    
    /**
     * Gets the animation's frame
     *
     * @return The number of frames of this animation
     */
    public int getFrames() {
	return mFrames;
    }

    /**
     * Gets the current frame relative to the
     * entity's spritesheet image
     */
    public int getCurrentFrame() {
	return mCurrentFrame;
    }

    /**
     * Sets animation direction.
     *
     * @param dir 1 for forwards, -1 for backwards
     */
    public void setDirection(int dir) {
	mAnimationDirection = dir == 1 || dir == -1 ? dir : 1;
    }

    
    /**
     * Gets animation's direction
     *
     * @return 1 for forwards, -1 backwards
     */
    public int getDirection() {
	return mAnimationDirection;
    }

    /**
     * Sets whether the animetion is looping or just shows once
     *
     * @param loop True for looping, false for execute just once
     */
    public void setLooping(boolean loop) {
	mLoop = loop;
    }

    /**
     * Gets whether the animation is looping or not
     */
    public boolean isLooping() {
	return mLoop;
    }

    /**
     * Sets the speed of the animation in frames per second
     * @param fps
     */
    public void setFPS(int fps) {
	mFrameTime = fps >= 0 ? 1.0 / (double) fps : 0.0;
    }

    /**
     * Gets the duration of a single frame in seconds
     */
    public double getFrameTime() {
	return mFrameTime;
    }

    /**
     * Updates the current frame
     */
    public void update(double duration) {
	
	if(mFrames > 1) {
	    
	    mTimeCount += duration;

	    if(mTimeCount >= mFrameTime) {
		
		mTimeCount -= mFrameTime;

		mCurrentFrame += mAnimationDirection;

		if(mAnimationDirection == 1 && mCurrentFrame > mStartFrame + mFrames - 1)
		    mCurrentFrame = mStartFrame;
		if(mAnimationDirection == -1 && mCurrentFrame < mStartFrame)
		    mCurrentFrame = mStartFrame + mFrames - 1;
	    }
	}
    }
}
