package mathjetpack.map;

import mathjetpack.Game;

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

    private LinkedList<MapImage> mMapImages;

    /**
     * Constructor.
     * Loads the Map Images
     *
     * @param width
     * @param height
     */
    public Map(int width, int height) {

        mWidth = width;
        mHeight = height;

        mMapImages = new LinkedList<MapImage>();

        MapImage image; // Auxiliary Image to load the images

        int vel = -200;

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
            image.setVelocity(vel * 0.1, 0);
            image.repeatX(true);
            image.setImage(ImageIO.read(Map.class.getResource("/map/mountains_r.png")));
            mMapImages.add(image);

            // Loads the Floor Image
            image = new MapImage(this);
            image.setWidth(65);
            image.setHeight(80);
            image.setPosition(0, 560);
            image.setVelocity(vel, 0);
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

    public void move(double duration) {

        // Updates the Map Images
        for(MapImage image : mMapImages)
            image.move(duration);

    }

    public void draw(Graphics2D g) {

        // Draws the background
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, mWidth, mHeight);

        for(MapImage image : mMapImages)
            image.draw(g);

    }

}
