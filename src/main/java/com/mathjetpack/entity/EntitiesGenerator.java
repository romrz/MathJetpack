package mathjetpack.entity;

import mathjetpack.Game;
import mathjetpack.map.Map;
import mathjetpack.images.Images;

import java.util.Random;

/**
 * 
 */
public class EntitiesGenerator {

    protected Game mGame;
    protected Map mMap;

    protected double x;

    protected Random r;

    public EntitiesGenerator(Game game, Map map) {
	mGame = game;
	mMap = map;

	x = 0;
	
	r = new Random();
    }

    public void tick(double distance) {
	x -= distance;
    
	if(x <= 0) {
	    generate(r.nextInt(3) + 1);
	}
    }
   
    /**
     * Generates one coin 1000 pixels ahead form the player
     */
    public void generateCoins() {
	Entity e = new Coin(Images.getImage("/entities/coin.png"));
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, r.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
    }

    /**
     * Generates one question box 1000 pixels ahead form the player
     */
    public void generateQuestionBox() {
	Entity e = new QuestionBox(Images.getImage("/entities/question_box.png"));
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, r.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
    }

    /**
     * Generates one wall 1000 pixels ahead form the player
     */
    public void generateWall() {
	Entity e = new Wall(Images.getImage("/entities/wall.png"));
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, mMap.getBottomBound() - e.getHeight());
	mGame.addEntity(e);
    }

    /**
     * Generates a random entity
     */
    public void generate(int i) {
	
	if(i == 1) generateCoins();
	else if(i == 2) generateQuestionBox();
	else if(i == 3) generateWall();

	x += 400;

    }

}
