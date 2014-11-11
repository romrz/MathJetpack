package mathjetpack.map;

import mathjetpack.Game;
import mathjetpack.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;

/**
 * Created by rom on 20/10/14.
 */
public class Map {

    // Map visible dimensions
    private int mWidth;
    private int mHeight;

    // Background Images
    private LinkedList<MapImage> mMapImages;

    /**
     * Constructor.
     * Initialize the map and loads the Map Images
     *
     * @param width
     * @param height
     * @param velocity Map's velocity
     */
    public Map(int width, int height, Vector2 velocity) {

        mWidth = width;
        mHeight = height;
        mMapImages = new LinkedList<MapImage>();

	loadImages(velocity);
    }

    /**
     * Loads the images of the map
     * @param velocity Map's velocity
     */
    private void loadImages(Vector2 velocity) {
	
	MapImage image; // Auxiliary Image to load the images

        try {
            // Loads the Clouds Image
	    image = new MapImage(this);
            image.setWidth(50);
            image.setHeight(640);
            image.setPosition(0, 0);
            image.setVelocity(0, 0);
            image.repeatX(true);
            image.setImage(ImageIO.read(Map.class.getResource("/map/sky_r.png")));
            mMapImages.add(image);

            // Loads the Mountains Image
            image = new MapImage(this);
            image.setWidth(528);
            image.setHeight(192);
            image.setPosition(0, 368);
            image.setVelocity(velocity.x * -0.1, 0);
            image.repeatX(true);
            image.setImage(ImageIO.read(Map.class.getResource("/map/mountains_r.png")));
            mMapImages.add(image);

            // Loads the Floor Image
            image = new MapImage(this);
            image.setWidth(65);
            image.setHeight(80);
            image.setPosition(0, 560);
            image.setVelocity(-velocity.x, 0);
            image.repeatX(true);
            image.setImage(ImageIO.read(Map.class.getResource("/map/grass_r.png")));
            mMapImages.add(image);

        }
        catch (Exception e) {
            System.out.println("Error while loading Map Images");
        }

    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * Moves the map
     */
    public void move(double duration) {

        // Moves the Map Images
        for(MapImage image : mMapImages)
            image.move(duration);

    }

    /**
     * Draws the Map
     */
    public void draw(Graphics2D g) {

        // Draws the background
        //g.setColor(Color.BLUE);
        //g.fillRect(0, 0, mWidth, mHeight);

	// Draws the images
        for(MapImage image : mMapImages)
            image.draw(g);

    }

}
