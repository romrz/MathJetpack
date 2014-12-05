package mathjetpack.entity;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class Question extends Entity {

    public static enum State {CORRECT, WRONG, NO_SELECTED};
    
    private String mQuestion;
    private State mState;

    private ArrayList<Option> mOptions;

    private double mDistanceLeft;

    public Question(String question) {
	super();

	mQuestion = question;
	mState = State.NO_SELECTED;

	mOptions = new ArrayList<Option>();

	mDistanceLeft = 400;

    }

    public void setState(State state) {
	mState = state;
    }

    public State getState() {
	return mState;
    }

    public boolean isAnswered() {
	return mState != State.NO_SELECTED;
    }

    public void addOption(Option o) {
	mOptions.add(o);
    }

    public State checkCollision(Entity entity) {
	for(Option option : mOptions) {

	    if(option.collidesWith(entity) && !isAnswered())
		if(option.isCorrect())
		    mState = State.CORRECT;
		else
		    mState = State.WRONG;
	}

	if(mState == State.NO_SELECTED && entity.getLeft() > mOptions.get(0).getRight())
	    mState = State.WRONG;
	
	return mState;
    }

    public void move(double duration) {
	
	mDistanceLeft -= mVelocity.x * duration;

	if(mDistanceLeft <= 0)
	    for(Option option : mOptions) {
		option.move(duration);
	    }
    }

    public void draw(Graphics2D g) {

	if(mWidth == 0 || mHeight == 0) {
	    mWidth = g.getFontMetrics().stringWidth(mQuestion);
	    mHeight = g.getFontMetrics().getHeight();
	}
	
	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	g.setColor(Color.WHITE);
	g.drawString(mQuestion, getLeft(), getBottom());
	
	for(Option option : mOptions) {
	    option.draw(g);
	}

    }

}