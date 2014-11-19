package mathjetpack.entity;

import java.awt.image.BufferedImage;

public class Wall extends Entity {
    
    /**
     * Initializes the Wall
     */
    public Wall(BufferedImage image) {
	super();

	setImage(image);
	setWidth(60);
	setHeight(40);
	// setPosition(300, 500);
	setVelocity(0, 0);
    }

}
