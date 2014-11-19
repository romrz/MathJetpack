package mathjetpack.entity;

import java.awt.image.BufferedImage;

public class Coin extends Entity {
    
    /**
     * Initializes the Coin
     */
    public Coin(BufferedImage image) {
	super();

	setImage(image);
	setWidth(30);
	setHeight(33);
	setVelocity(0, 0);

	setAnimationFPS(6);
	setAnimationFrames(3);
	setColumns(3);
    }

}
