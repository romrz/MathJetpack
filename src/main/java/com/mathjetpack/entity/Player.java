package mathjetpack.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import mathjetpack.Vector2;

public class Player extends Entity {
    
    /**
     * Initializes the player's attributes
     */
    public Player(BufferedImage image) {
	super();

	setImage(image);
	setWidth(40);
	setHeight(60);
	setPosition(100, 350);
	
    }

}
