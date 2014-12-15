package mathjetpack;

import mathjetpack.map.Map;
import mathjetpack.entity.Entity;
import mathjetpack.entity.Player;
import mathjetpack.entity.QuestionBox;
import mathjetpack.entity.Question;
import mathjetpack.entity.Option;
import mathjetpack.entity.Wall;
import mathjetpack.entity.Coin;
import mathjetpack.images.Images;
import mathjetpack.ui.*;
import mathjetpack.sound.*;

import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Game extends Canvas implements Runnable {

    // Canvas dimension
    private int mWidth;
    private int mHeight;

    // Game entities list
    private LinkedList<Entity> mEntities;

    // Player
    private Player mPlayer;

    // The map of the game
    private Map mMap;

    // Game states
    public static enum States {MAINMENU, PLAYING, PAUSED, GAMEOVER};
    private States mState;
        
    // Game Menus
    private HashMap<String, Menu> mMenus;
    private Menu mCurrentMenu;
    private Button mPauseButton;

    // Current Question. If it's not null then it's shown on screen
    private Question mCurrentQuestion;

    /**
     * HUD
     * Shows the number of questions answered correctly
     * and the coins taken
     */
    private HUD mHUD;

    // Sound
    // Loads and stores the game sounds to be played anytime
    private Sound mSound;

    // Frames per second
    private int mFrameRate;

    // Game loop's running state
    private boolean mRunning;

    // Debugging info
    private boolean mShowInfo = false;
    private boolean mPrintInfo = false;

    /**
     * Game Constructor.
     * Initializes the game
     */
    public Game(int width, int height) {

        // Sets the size to the canvas
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

	// Sets the canvas dimensions
        mWidth = width;
        mHeight = height;

	Input input = new Input(this);
        addKeyListener(input);
	addMouseListener(input);

        mEntities = new LinkedList<Entity>();
	
	mMenus = new HashMap<String, Menu>();

	// Creates the Pause Button
	mPauseButton = new Button("Pausar");
	mPauseButton.setWidth(100);
	mPauseButton.setHeight(40);
	mPauseButton.setPosition(mWidth - 60, 30);
    }

    /**
     * Prepares the game to start
     */
    public void init() {

	// Creates and adds the player
	mPlayer = new Player(Images.getImage("/entities/player_spritesheet.png"));
	mPlayer.setVelocity(200, 0);
	mPlayer.setRelativeVelocity(mPlayer.getVelocity());
	addEntity(mPlayer);

	mHUD = new HUD(this);
        mMap = new Map(this, mWidth, mHeight, mPlayer.getVelocity());
	mSound = new Sound();

	loadMenus();
	mainMenu();

	// Starts the background sound
	mSound.play("background", true);
    }

    /**
     * Creates the game menus
     */
    private void loadMenus() {
	
	Menu menu = null;
	
	// Main Menu
	menu = new Menu(this);
	menu.setPosition(mWidth / 2, 3 * mHeight / 4);
	menu.addButton(new Button("Jugar"));
	menu.addButton(new Button("Puntuaciones"));
	menu.addButton(new Button("Preguntas"));
	menu.addButton(new Button("Salir"));
	mMenus.put("MainMenu", menu);

	// Pause Menu
	menu = new Menu(this);
	menu.addButton(new Button("Reanudar"));
	menu.addButton(new Button("Reiniciar"));
	menu.addButton(new Button("Menu Principal"));
	mMenus.put("PauseMenu", menu);

	// Game Over Menu
	menu = new Menu(this);
	menu.setPosition(mWidth / 2, 3 * mHeight / 4);
	menu.addButton(new Button("Jugar"));
	menu.addButton(new Button("Menu Principal"));
	mMenus.put("GameOverMenu", menu);
    }

    public void showQuestionManager() {
	new QuestionManager();
    }

    public void showScores() {
	new ScoresFrame();
    }

    
    /**
     * Updates the scores file
     */
    public void updateScores(String player, int score) {
	BufferedReader reader = null;
	BufferedWriter writer = null;
	
	boolean emptyFile = true;
	String scoresString = "", scoreAux[], line;
	try {
	    reader = new BufferedReader(new FileReader("target/classes/scores.txt"));

	    while((line = reader.readLine()) != null) {
		emptyFile = false;
		scoreAux = line.split(" ");

		if(score >= Integer.parseInt(scoreAux[1])) {
		    scoresString += player + ": " + score + "\n";
		    score = -1;
		}
		else
		    scoresString += scoreAux[0] + scoreAux[1] + "\n";
	    }
	    
	    if(emptyFile)
		scoresString += player + ": " + score + "\n";

	}
	catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	finally {
	    try { reader.close(); }
	    catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	}
	
	try {
	    writer = new BufferedWriter(new FileWriter("target/classes/scores.txt"));
	    writer.write(scoresString, 0, scoresString.length());
	}
	catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	finally {
	    try { writer.close(); }
	    catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	}
    }

    /**
     * Set the current menu.
     * If the current menu is not null, it is shown to the screen
     *
     * @param menu The name of the menu, in the mMenus HashMap
     */
    public void setMenu(String menu) {
	mCurrentMenu = mMenus.get(menu);
    }

    public Menu getCurrentMenu() {
	return mCurrentMenu;
    }

    public Button getPauseButton() {
	return mPauseButton;
    }

    public Player getPlayer() {
	return mPlayer;
    }

    /**
     * Plays a sound previously loaded
     *
     * @param sound The name of the sound
     * @param loop True for infinite looping
     */
    public void playSound(String sound, boolean loop) {
	mSound.play(sound, loop);
    }

    public void stopSound(String sound) {
	mSound.stop(sound);
    }    

    /**
     * Sets the game state
     *
     * @param state A state in the enum States
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

    public boolean isInQuestion() {
	return mCurrentQuestion != null;
    }

    /**
     * Adds an entity to the game
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
    
    /**
     * Shows the main menu
     */
    public void mainMenu() {
	reset();
	setState(States.MAINMENU);
	setMenu("MainMenu");
    }

    /**
     * Starts a new Game
     */
    public void play() {
	reset();
	setState(States.PLAYING);
	addEntity(mPlayer);
    }

    /**
     * Pauses the game and shows the Pause Menu
     */
    public void pause() {
	setState(States.PAUSED);
	setMenu("PauseMenu");
    }

    /**
     * Resumes the game after being paused
     */
    public void resume() {
	setState(States.PLAYING);
	mCurrentMenu = null;
    }
    
    /**
     * Called when the player lose
     */
    public void gameOver() {
	mCurrentQuestion = null;
	setState(States.GAMEOVER);
	setMenu("GameOverMenu");
	updateScores(mPlayer.getName(), mPlayer.getPoints());
    }

    /**
     * Prepares the game to quit
     */
    public void quit() {
	reset();
	System.exit(0);
    }

    /**
     * Resets the game as it's first started
     */
    public void reset() {
	mCurrentQuestion = null;
	mCurrentMenu = null;
	mEntities.clear();
	mMap.reset();
	mPlayer.reset();
    }

    /**
     * Checks and handles the collision between entities
     */
    public void collisionHandling() {
	if(mState != States.PLAYING) return;

	// Iterates through the list
	Entity entity = null;

	ListIterator<Entity> it = mEntities.listIterator();
	while(it.hasNext()) {
	    entity = it.next();

	    // Checks collision between the player and the entities
	    if(entity.collidesWith(mPlayer)) {
		entity.testCollition(); // Prints a rectangle around the entity
		
		if(entity.getType() == Entity.Type.WALL) {
		    mSound.play("hurt");
		    gameOver();
		}
		else if(entity.getType() == Entity.Type.COIN) {
		    mSound.play("coin");
		    entity.setAlive(false);
		    mPlayer.addCoin();
		}
		else if(entity.getType() == Entity.Type.QBOX) {
		    mSound.play("question");
		    mCurrentQuestion = ((QuestionBox) entity).getQuestion();
		    entity.setAlive(false);
		
		}
	    }

	    // Removes all the QuestionBox if a Question is being shown
	    if(entity.getType() == Entity.Type.QBOX && mCurrentQuestion != null)
		entity.setAlive(false);

	    // Checks if the entity has passed the screen
	    if(entity.getRight() < 0)
		entity.setAlive(false); // Removes the entity from the list

	    if(!entity.isAlive()) it.remove();

	}

	// Checks collision between the question's options and the player
	if(mCurrentQuestion != null && !mCurrentQuestion.isAnswered()) {
	    mCurrentQuestion.checkCollision(mPlayer);
	    
	    // If the player selected the correct option 
	    if(mCurrentQuestion.getState() == Question.State.CORRECT) {
		mSound.play("correct");
		mPlayer.addPoint();
		mPlayer.setVX(mPlayer.getVX() + 10);
		mCurrentQuestion = null;
	    }
	    // If the player selected an incorrect option
	    else if(mCurrentQuestion.getState() == Question.State.WRONG) {
		mSound.play("wrong");
		gameOver();
		mCurrentQuestion = null;
	    }
	}

	// Player and Map collision. Keeps the player between the Map Bounds
	if(mPlayer.getBottom() > mMap.getBottomBound()) {
	    mPlayer.setBottom(mMap.getBottomBound());
	    mPlayer.setVY(0);
	    mPlayer.running(true);
	    //mSound.play("run", true);
	}
	else if(mPlayer.getTop() < mMap.getTopBound()) {
	    mPlayer.setTop(mMap.getTopBound());
	    mPlayer.setVY(0);
	}
    }

    /**
     * Updates the game world
     *
     * @param duration Time elapsed since last update
     */
    public void updateGame(double duration) {
	if(mState != States.PLAYING && mState != States.MAINMENU) return;
	    
        // Moves the map
        mMap.move(duration);

        // Moves the entities
        for(Entity entity : mEntities)
            entity.move(duration);

	// Moves the question
	if(mCurrentQuestion != null)
	    mCurrentQuestion.move(duration);

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

	// Draws the menu if it's set
	if(mCurrentMenu != null)
	    mCurrentMenu.draw(g);
	
	if(mState == States.PLAYING || mState == States.PAUSED) {
	    mPauseButton.draw(g);
	    
	    // Draws the HUD
	    mHUD.draw(g);
	}
	else if(mState == States.GAMEOVER) {
	    // Draws the score
	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
	    g.setColor(Color.WHITE);
	    g.drawString("!Perdiste!", 330, 150);
	    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
	    g.drawString("Puntuacion: " + mPlayer.getPoints(), 360, 200);

	}
	
	// Draws the question
	if(mCurrentQuestion != null)
	    mCurrentQuestion.draw(g);

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

	init();

        // Auxiliary to obtain the frame rate
        int frameCount = 0;
        long frameTime = 0;

        long previous = System.nanoTime();
        long current = previous;
        long period = 1000000000L / 80L;
        long diff;
        long sleepTime;
        double elapsed;

        long t1s = 0, t1e = 0, t2s = 0, t2e = 0, t3s = 0, t3e = 0, t4s = 0, t4e = 0; // Debug info

        // Game loop
        while(mRunning) {

            current = System.nanoTime();
            elapsed = (current - previous) / 1000000000.0;
	    previous = current;

            t1s = System.nanoTime(); // Updating start
            updateGame(elapsed);
	    t1e = System.nanoTime(); // Updating end
            
	    collisionHandling();

	    t2s = System.nanoTime(); // Rendering start
	    render();
	    t2e = System.nanoTime(); // Rendering end

	    sleepTime = (period - (System.nanoTime() - current)) / 1000000;            

            if(sleepTime < 0)
                sleepTime = 0;

            try {
		t3s = System.nanoTime(); // Sleeping start
                Thread.sleep(sleepTime);
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
	    if(mPrintInfo) {
		System.out.printf(
		"Total time elapsed: %.4f\tTime updating: %.4f\tTime rendering: %.4f\tTime Sleeping: %.4f\tFPS: %d\tSleepTime: %.4f\n",
		elapsed, (t1e - t1s) / 1000000000.0, (t2e - t2s) / 1000000000.0, (t3e - t3s) / 1000000000.0, mFrameRate, sleepTime / 1000.0);
	    }
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

	g.setFont(null);
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
