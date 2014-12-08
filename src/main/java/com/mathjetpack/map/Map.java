package mathjetpack.map;

import mathjetpack.Game;
import mathjetpack.entity.EntitiesGenerator;
import mathjetpack.images.Images;
import mathjetpack.Vector2;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;


public class Map {

    // Map visible dimensions
    private int mWidth;
    private int mHeight;

    // Map Bounds
    private int mTopBound;
    private int mBottomBound;

    // Background Images
    private LinkedList<MapImage> mMapImages;

    private Vector2 mPosition;
    private Vector2 mVelocity;

    // The entities generator
    private EntitiesGenerator mGenerator;

    /**
     * Constructor.
     * Initialize the map and loads the Map Images
     *
     * @param width
     * @param height
     * @param relativeVelocity Map's velocity
     */
    public Map(Game game, int width, int height, Vector2 relativeVelocity) {

        mWidth = width;
        mHeight = height;

	mTopBound = 10;
	mBottomBound = mHeight - 75;

        mMapImages = new LinkedList<MapImage>();

	loadImages(relativeVelocity);
	
	mGenerator = new EntitiesGenerator(game, this);
	
	mPosition = new Vector2();
	mVelocity = relativeVelocity;
    }

    /**
     * Loads the images of the map
     * @param relativeVelocity Map's velocity
     */
    private void loadImages(Vector2 relativeVelocity) {
	
	MapImage image; // Auxiliary Image to load the images
        
	// Loads the Clouds Image
	image = new MapImage(this);
	image.setWidth(50);
	image.setHeight(640);
	image.setPosition(0, 0);
	image.setVelocity(0, 0);
	image.setRelativeVelocity(new Vector2());
	image.repeatX(true);
	image.setImage(Images.getImage("/map/sky_r.png"));
	mMapImages.add(image);

	// Loads the Mountains Image
	image = new MapImage(this);
	image.setWidth(528);
	image.setHeight(192);
	image.setPosition(0, 368);
	image.setVelocity(relativeVelocity.x * 0.9, 0);
	image.setRelativeVelocity(relativeVelocity);
	image.repeatX(true);
	image.setImage(Images.getImage("/map/mountains_r.png"));
	mMapImages.add(image);

	// Loads the Floor Image
	image = new MapImage(this);
	image.setWidth(65);
	image.setHeight(80);
	image.setPosition(0, 560);
	image.setVelocity(0, 0);
	image.setRelativeVelocity(relativeVelocity);
	image.repeatX(true);
	image.setImage(Images.getImage("/map/grass_r.png"));
	mMapImages.add(image);

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

    public int getTopBound() {
	return mTopBound;
    }

    public int getBottomBound() {
	return mBottomBound;
    }

    public double getX() {
	return mPosition.x;
    }

    public void reset() {
	mPosition.x = 0;
	mPosition.y = 0;
	mGenerator.reset();
    }

    /**
     * Moves the map
     */
    public void move(double duration) {

        // Moves the Map Images
        for(MapImage image : mMapImages)
            image.move(duration);
	
	mPosition.addScaledVector(mVelocity, duration);
	
	mGenerator.update();
    }

    /**
     * Draws the Map
     *
     * @param g The graphics
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
