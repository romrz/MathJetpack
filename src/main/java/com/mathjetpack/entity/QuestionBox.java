package mathjetpack.entity;

import mathjetpack.images.Images;

import java.awt.image.BufferedImage;

public class QuestionBox extends AnimatedEntity {
    
    /**
     * Initializes the QuestionBox
     */
    public QuestionBox() {
	super();

	setImage(Images.getImage("/entities/question_box.png"));
	setWidth(44);
	setHeight(44);
	setType(Entity.Type.QBOX);
	
	// Add animations
	addAnimation(new AnimationInfo());
	setAnimation(1);

    }

}
