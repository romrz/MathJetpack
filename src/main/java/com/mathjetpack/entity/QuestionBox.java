package mathjetpack.entity;

import java.awt.image.BufferedImage;

public class QuestionBox extends AnimatedEntity {
    
    /**
     * Initializes the QuestionBox
     */
    public QuestionBox(BufferedImage image) {
	super();

	setImage(image);
	setWidth(44);
	setHeight(44);
	//setPosition(400, 350);
	setVelocity(0, 0);
	setAcceleration(0, 0);

	// Add animations
	addAnimation(new AnimationInfo());
	setAnimation(1);

    }

}
