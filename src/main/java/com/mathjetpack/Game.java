package mathjetpack;

import mathjetpack.map.Map;
import mathjetpack.entity.Entity;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 * Created by rom on 16/09/14.
 */
public class Game extends Canvas implements Runnable {

    // Panel dimension
    private int mWidth;
    private int mHeight;

    // Game entities
    private LinkedList<Entity> mEntities;

    // The map of the game
    private Map mMap;

    // Game states
    public static enum States {STARTING, STARTED, PLAYING, PAUSED};
    private States mState = States.STARTING;

    // Frames per second
    private int mFrameRate;

    // Game loop's running state
    private boolean mRunning;

    // Debugging info
    private boolean mShowInfo = false;

    public Game(int width, int height) {

        // Sets the size to the canvas
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

        mWidth = width;
        mHeight = height;

        addKeyListener(new Input(this));

        mEntities = new LinkedList<Entity>();

        init();
    }

    /**
     * Prepares the game to start
     */
    public void init() {
        mMap = new Map(mWidth, mHeight);
    }

    /**
     * Sets the game state
     *
     * @param state A state of the enum States
     */
    public void setState(States state) {
        mState = state;
    }

    /**
     * Returns the current state of the game
     *
     * @return The current game state, defined in the enum States
     */
    public States getState() {
        return mState;
    }

    /**
     * Sets the running state of the game loop
     *
     * @param running
     */
    public void setRunning(boolean running) {
        mRunning = running;
    }

    /**
     * Adds a entity to the game
     *
     * @param entity
     */
    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    /**
     * Starts the Game Loop
     */
    public void start() {
        setRunning(true);
        new Thread(this).start();
    }

    public void pause() {}

    public void resume() {}

    public void restart() {}

    /**
     * Prepares the game to quit
     */
    public void quit() {}

    /**
     * Updates the game world
     *
     * @param duration Time elapsed since last update
     */
    public void updateGame(double duration) {

        // Moves the map
        mMap.move(duration);

        // Moves the entities
        for(Entity entity : mEntities)
            entity.move(duration);

    }

    /**
     * Render the game world
     */
    public void render() {
        // Creates and gets the Buffer Strategy of this canvas
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(2);
            return;
        }

        // Gets the drawing graphics
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        // Draws the map
        mMap.draw(g);

        // Draws the particles
        for(Entity entity : mEntities)
            entity.draw(g);

        // Shows the info related to the game for debugging purposes
        if(mShowInfo)
            printDebugInfo(g, bs);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        bs.show();

    }

    /**
     * Runs the game loop
     */
    public void run() {

        // Auxiliary to obtain the frame rate
        int frameCount = 0;
        long frameTime = 0;

        long previous = System.nanoTime();
        long current;
        long period = 10000000L;
        long diff;
        long sleepTime;
        double elapsed;

        long t1s, t1e, t2s, t2e, t3s = 0, t3e = 0, t4s, t4e; // Debug info

        // Game loop
        while(mRunning) {

            current = System.nanoTime();
            elapsed = (current - previous) / 1000000000.0;

            sleepTime = (period - (current - previous)) / 1000000 ;

            previous = current;

            t1s = System.nanoTime(); // Updating start
            updateGame(elapsed);
	    t1e = System.nanoTime(); // Updating end
            
	    t2s = System.nanoTime(); // Rendering start
	    render();
	    t2e = System.nanoTime(); // Rendering end

            if(sleepTime < 0)
                sleepTime = 1;

            try {
		t3s = System.nanoTime(); // Sleeping start
                Thread.sleep(1);
		t3e = System.nanoTime(); // Sleeping end
            }
            catch(InterruptedException e) {
                System.out.println("Exception in main loop");
            }

            // Updates the Frame Rate every second
            frameCount++;
            if(frameTime + 1000000000 < current) {
                mFrameRate = frameCount;
                frameCount = 0;
                frameTime = current;
            }

	    
	    // Debug info
	    System.out.printf("Total time elapsed: %.4f\tTime updating: %.4f\tTime rendering: %.4f\tTime Sleeping: %.4f\tFPS: %d\n",
			      elapsed, (t1e - t1s) / 1000000000.0, (t2e - t2s) / 1000000000.0, (t3e - t3s) / 1000000000.0, mFrameRate);

        }
    }

    /**
     * Sets whether to show the debug info or not
     */
    public void toggleDebugInfo() {
        mShowInfo = !mShowInfo;
    }

    /**
     * Prints the debug information that might be useful
     *
     * @param g
     * @param bs
     */
    private void printDebugInfo(Graphics2D g, BufferStrategy bs) {
        int vspace = 15;
        int vstart = 25;
        int i = 1;

        g.setColor(Color.BLACK);
        g.drawString("Press D to show/hide the debugging information", 10, vstart);
        g.drawString("FPS: " + mFrameRate, 10, vspace * i++ + vstart);
        g.drawString("Page Flipping Available: " + bs.getCapabilities().isPageFlipping(), 10, vspace * i++ + vstart);
        g.drawString("Flip Contents: " + bs.getCapabilities().getFlipContents().toString(), 10, vspace * i++ + vstart);
        g.drawString("Full Screen Required: " + bs.getCapabilities().isFullScreenRequired(), 10, vspace * i++ + vstart);
        g.drawString("Multi Buffer Available: " + bs.getCapabilities().isMultiBufferAvailable(), 10, vspace * i++ + vstart);
        g.drawString("Front Buffer Accelerated: " + bs.getCapabilities().getFrontBufferCapabilities().isAccelerated(), 10, vspace * i++ + vstart);
        g.drawString("Back Buffer Accelerated: " + bs.getCapabilities().getBackBufferCapabilities().isAccelerated(), 10, vspace * i++ + vstart);
        g.drawString("Front Buffer True Volatile: " + bs.getCapabilities().getFrontBufferCapabilities().isTrueVolatile(), 10, vspace * i++ + vstart);
        g.drawString("Back Buffer True Volatile: " + bs.getCapabilities().getBackBufferCapabilities().isTrueVolatile(), 10, vspace * i++ + vstart);
    }
}
