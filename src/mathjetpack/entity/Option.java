package mathjetpack.entity;

import mathjetpack.images.Images;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;


public class Option extends AnimatedEntity {
    
    // The option
    private String mText;
    
    private boolean mCorrect;    

    // Text dimensions. Usefuls when painting the text
    private int mTextWidth;
    private int mTextHeight;

    public Option(String text) {
        super();

        mText = text;
        mCorrect = false;

        mWidth = 30;
        mHeight = 30;

        setImage(Images.getImage("/entities/option.png"));

        addAnimation(new AnimationInfo(0, 1, 1, 1, true));
        setAnimation(1);
    }
    public Option(String text, boolean correct) {
        this(text);
        setCorrect(correct);
    }

    public void setCorrect(boolean c) {
        mCorrect = c;
    }

    public boolean isCorrect() {
        return mCorrect;
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if(mTextWidth == 0 || mTextHeight == 0) {
            mTextWidth = g.getFontMetrics().stringWidth(mText);
            mTextHeight = g.getFontMetrics().getHeight();
        }

        g.setColor(Color.WHITE);
        g.drawString(mText, getLeft() - mTextWidth - 10, getTop() + (mHeight / 2) + mTextHeight / 2);
    }

    public String toString() {
        return mCorrect ? "*" + mText : mText;
    }
}
