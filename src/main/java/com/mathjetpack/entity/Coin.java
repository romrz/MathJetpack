package mathjetpack.entity;

import java.awt.image.BufferedImage;

public class Coin extends AnimatedEntity {
    
    /**
     * Initializes the Coin
     */
    public Coin(BufferedImage image) {
	super();

	setImage(image);
	setWidth(30);
	setHeight(33);
	setVelocity(0, 0);
	setAcceleration(0, 0);

	// Set animations	
	setImageFrames(3);
	setColumns(3);

	addAnimation(new AnimationInfo(0, 3, 1, 6, true));	
	setAnimation(1);
    }
}
