package mathjetpack.entity;

import mathjetpack.Game;
import mathjetpack.map.Map;

import java.util.Random;

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
    
    public void generateCoins() {
	Entity e = new Coin(Game.mCoinImage);
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, r.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
    }

    public void generateQuestionBox() {
	Entity e = new QuestionBox(Game.mQuestionBoxImage);
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, r.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
    }
    public void generateWall() {
	Entity e = new Wall(Game.mWallImage);
	e.setRelativeVelocity(mGame.getPlayer().getVelocity());
	e.setPosition(1000, r.nextInt(mMap.getBottomBound()));
	mGame.addEntity(e);
    }

    public void generate(int i) {
	
	if(i == 1) generateCoins();
	else if(i == 2) generateQuestionBox();
	else if(i == 3) generateWall();

	x += 400;

    }

}
