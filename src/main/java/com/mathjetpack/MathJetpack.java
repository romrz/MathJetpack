package mathjetpack;

import javax.swing.JFrame;

/**
 * Created by rom on 28/09/14.
 */
public class MathJetpack {

    public static void main(String args[]) {

        int width = 960;
        int height = 640;

        JFrame frame = new JFrame("MathJetpack Game");
        Game game = new Game(width, height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setIgnoreRepaint(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

}
