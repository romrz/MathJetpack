package mathjetpack.images;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Transparency;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;

/**
 * This class stores and retreives a requested image
 * If the requested image is not yet in the hash map,
 * it loads the image and stores it in the hashmap for
 * future usage, avoiding memory wasting. 
 */
public final class Images {
    
    /**
     * HashMap of the images.
     * The key is a string containing the path.
     * The value is the BufferedImage
     */
    private static HashMap<String, BufferedImage> mImages = new HashMap<String, BufferedImage>();

    /**
     * Retreives the requested image.
     * If the image is not in the hash, loads and stores it.
     * 
     * @param path The path to the image
     * @return The BufferedImage
     */
    public static BufferedImage getImage(String path) {
       
	if(mImages.containsKey(path))
	    return mImages.get(path);

	// Loads the image
	BufferedImage auxImage = null;
	try {
	    auxImage = ImageIO.read(Images.class.getResource(path));    
	}
	catch(Exception e) {
	    System.out.println("Error loading the image: " + path);
	    System.exit(0);
	}

	// Create a compatible image with the device
	GraphicsConfiguration gc = 
	    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	BufferedImage image = gc.createCompatibleImage(auxImage.getWidth(), auxImage.getHeight(), Transparency.BITMASK);
	Graphics2D g = (Graphics2D) image.createGraphics();
	g.drawImage(auxImage, 0, 0, auxImage.getWidth(), auxImage.getHeight(), null);
	g.dispose();

	// Stores the image
	mImages.put(path, image);

	return image;
    }
}
