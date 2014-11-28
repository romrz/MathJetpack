package mathjetpack.entity;

import mathjetpack.Game;
import mathjetpack.map.Map;

import java.util.Random;

/**
 * 
 */
public class EntitiesGenerator {

    protected Game mGame;
    protected Map mMap;

    protected double next;

    protected Random rand;

    public EntitiesGenerator(Game game, Map map) {
	mGame = game;
	mMap = map;

	next = 1000;
	
	rand = new Random();
    }

    public void update() {
	if(mMap.getX() >= next) {
	    generate(rand.nextInt(3) + 1);
	}
    }
   
    /**
     * Generates one coin 1000 pixels ahead form the player
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

	for(int i = 0; i < r; i++) {
	    for(int j = 0; j < c; j++) {
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
     */
    public int generateQuestionBox() {
	Entity e = new QuestionBox();
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, rand.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
	
	return e.getWidth();
    }

    /**
     * Generates one wall 1000 pixels ahead form the player
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
