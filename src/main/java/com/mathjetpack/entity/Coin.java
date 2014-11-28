package mathjetpack.entity;

import mathjetpack.images.Images;

import java.awt.image.BufferedImage;

public class Coin extends AnimatedEntity {
    
    /**
     * Initializes the Coin
     */
    public Coin() {
	super();

	setImage(Images.getImage("/entities/coin.png"));
	setWidth(30);
	setHeight(33);

	// Set animations	
	setImageFrames(3);
	setColumns(3);

	addAnimation(new AnimationInfo(0, 3, 1, 6, true));	
	setAnimation(1);
    }
}
