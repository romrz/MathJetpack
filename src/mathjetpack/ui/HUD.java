package mathjetpack.ui;

import mathjetpack.Game;
import mathjetpack.images.Images;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class HUD {
    
    private Game mGame;

    private BufferedImage mPointsImage;
    private BufferedImage mCoinsImage;

    public HUD(Game game) {
	mGame = game;

	mPointsImage = Images.getImage("/entities/question_box.png");	
	mCoinsImage = Images.getImage("/entities/single_coin.png");
    }

    public void draw(Graphics2D g) {
	g.setColor(Color.WHITE);	
	g.drawImage(mPointsImage, 10, 10, mPointsImage.getWidth(), mPointsImage.getHeight(), null);
	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	g.drawString("" + mGame.getPlayer().getPoints(), 20 + mPointsImage.getWidth(), 5 + mPointsImage.getHeight());
	g.drawImage(mCoinsImage, 10, 20 + mPointsImage.getHeight(), mCoinsImage.getWidth(), mCoinsImage.getHeight(), null);
	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	g.drawString("" + mGame.getPlayer().getCoins(), 20 + mCoinsImage.getWidth(), 60 + mCoinsImage.getHeight());
    }
}
