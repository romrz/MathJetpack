package mathjetpack.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Player extends Entity {
    
    /**
     * Initializes the player's attributes
     */
    public Player() {
	super();

	// Loads the image
	try {
	    setImage(ImageIO.read(Player.class.getResource("/entities/player.png")));
	}
	catch(Exception e) {
	    System.out.println("Error loading player image");
	}

	setWidth(40);
	setHeight(60);
	setPosition(100, 350);
	setVelocity(0, 0);
    }

}
