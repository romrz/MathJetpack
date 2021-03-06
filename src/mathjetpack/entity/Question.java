package mathjetpack.entity;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import mathjetpack.Vector2;

public class Question extends Entity {
    
    // States of the Question
    public static enum State {CORRECT, WRONG, NO_SELECTED};
    
    // The Question
    private String mQuestion;
    
    private State mState;
    
    // Options for this question
    private ArrayList<Option> mOptions;

    // Distance left until the options begin to move
    // to the player
    private double mDistanceLeft;

    public Question(String question) {
        super();

        mQuestion = question;
        mState = State.NO_SELECTED;

        mOptions = new ArrayList<Option>();

        mDistanceLeft = 400;
    }

    public void reset() {	
        mState = State.NO_SELECTED;
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

    public void setOptionsPosition(double x) {
	
        for(Option o : mOptions)
            o.setX(x - 40);
	
    }

    /**
     * Checks collision between an entity(the player) and
     * an option and sets the question's state
     * depending on what was answered by the player
     *
     * @param entity The entity to check collision with. Generally the player.
     * @return The state of the question
     */
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

    /**
     * Moves the question's options or updates the distance left
     * until the options have to move
     *
     * @param duration The frame duration in seconds
     */
    public void move(double duration) {
	
        mDistanceLeft -= mVelocity.x * duration;

        if(mDistanceLeft <= 0) {
            for(Option option : mOptions) {
                option.move(duration);
            }
        }
    }

    public void setOptionsRelativeVelocity(Vector2 velocity) {
        for(Option option : mOptions) {
            option.setRelativeVelocity(velocity);
        }
    }
    
    public void pack(int width, int height) {
        int posY = height / 6;
        for(Option option : mOptions) {
            option.setPosition(width - getWidth() - 20 - 40, posY);
            posY += height / 4;
        }
    }

    /**
     * Draws the question and its options
     */
    public void draw(Graphics2D g) {
        if(mWidth == 0 || mHeight == 0) {
            mWidth = g.getFontMetrics().stringWidth(mQuestion);
            mHeight = g.getFontMetrics().getHeight();
        }
	
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        g.drawString(mQuestion, getLeft(), getBottom());

        for(Option option : mOptions) {
            option.draw(g);
        }
    }

    public String toString() {
    
        String string = mQuestion + " ";
    
        for(Option o : mOptions)
            string += o.toString() + " ";

        return string;
    }
}
