package mathjetpack.entity;

import mathjetpack.Game;
import mathjetpack.map.Map;
import mathjetpack.Vector2;
import mathjetpack.QuestionManager;

import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * 
 */
public class EntitiesGenerator {

    protected Game mGame;
    protected Map mMap;

    // The next position where a new entity is going to be generated
    protected double next;

    // A random Object useful throughout the class
    protected Random rand;

    protected QuestionManager mQuestionManager;
    
    public EntitiesGenerator(Game game, Map map) {
	mGame = game;
	mMap = map;

	next = 1000;
	
	rand = new Random();

	mQuestionManager = new QuestionManager(mGame);
    }

    public void showQuestionManager() {
	mQuestionManager.showFrame();
    }

    /**
     * Resets the entities generator
     */
     public void reset() {
	next = 1000;
    }

    /**
     * Checks the map position. If it's greater than the next
     * position, generates a new entity randomly
     */
    public void update() {
	if(mGame.getState() != Game.States.PLAYING) return;

	if(mMap.getX() >= next) {
	    generate(rand.nextInt(3) + 1);
	}
    }
   
    /**
     * Generates one coin 1000 pixels ahead form the player
     * 
     * @return The width of the generated entities
     */
    public int generateCoins() {
	
	Entity e = new Coin();
	
	int c = rand.nextInt(6) + 1;
	int r = rand.nextInt(c) + 1;

	int w = e.getWidth();
	int h = e.getHeight();
	
	int y = rand.nextInt(mMap.getBottomBound() - r * h);
	int x = 1000;

	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(x, y);
	mGame.addEntity(e);

	// Generates a group of coins
	for(int i = 0; i < r; i++) {
	    for(int j = 0; j < c; j++) {
		if(i == 0 && j == 0) continue;

		e = new Coin();
		e.setRelativeVelocity(mGame.getPlayer().getVelocity());
		e.setPosition(x + w * j, y + h * i);
		mGame.addEntity(e);
	    }
	}

	return w * c;
    }

    /**
     * Generates one question box 1000 pixels ahead form the player
     *
     * @return The width of the generated entities
     */
    public int generateQuestionBox() {

	if(mGame.isInQuestion()) return 0;

	Vector2 relVel = mGame.getPlayer().getVelocity();

	QuestionBox e = new QuestionBox();
	e.setRelativeVelocity(relVel);
	e.setPosition(1000, rand.nextInt(mMap.getBottomBound()));

	Question question = mQuestionManager.getQuestion(); 
	question.setVelocity(relVel.x, relVel.y);
	question.setPosition(mGame.getWidth() / 4, 20);
	question.setOptionsPosition(mGame.getWidth() - 40);
	question.reset();

	e.setQuestion(question);

	mGame.addEntity(e);
	return e.getWidth();
    }

    /**
     * Generates one wall 1000 pixels ahead form the player
     *
     * @return The width of the generated entities 
     */
    public int generateWall() {
	Entity e = new Wall(rand.nextInt(10) + 1);
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, mMap.getBottomBound() - e.getHeight());
	mGame.addEntity(e);

	return e.getWidth();
    }

    /**
     * Generates a random entity
     */
    public void generate(int i) {
	
	if(i == 1) next += generateCoins();
	else if(i == 2) next += generateQuestionBox();
	else if(i == 3) next += generateWall();

	next += 400;
    }
}
