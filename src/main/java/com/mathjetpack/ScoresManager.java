package mathjetpack;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.ListIterator;

public class ScoresManager implements ActionListener {

    private LinkedList<String> mPlayers;
    private LinkedList<Integer> mScores;

    private String mSourceFile = "target/classes/scores.txt";
    private File mFile;

    public ScoresManager() {

	mPlayers = new LinkedList<String>();
	mScores = new LinkedList<Integer>();
	
	loadScores();
    }

    private void loadScores() {

    	BufferedReader reader = null;
	String[] playerScore = null;
	String line;	

	try {
	    reader = new BufferedReader(new FileReader(mSourceFile));
	    while((line = reader.readLine()) != null) {
		playerScore = line.split(": ");
		mPlayers.add(playerScore[0]);
		mScores.add(Integer.parseInt(playerScore[1]));
	    }
	}
	catch(Exception e) {
	    System.out.println("Error: Failed to load the Scores." + e);
	}
	finally {
	    try { reader.close(); }
	    catch(Exception e) { System.out.println("Error: Failed to load the Scores."); }
	}

    }

    private void saveScores() {
	BufferedWriter writer = null;
	
	String scoresString = "";

	try {
	    writer = new BufferedWriter(new FileWriter(mSourceFile));

	    ListIterator playerIt = mPlayers.listIterator();
	    ListIterator scoreIt = mScores.listIterator();

	    while(playerIt.hasNext() && scoreIt.hasNext()) {
		scoresString += playerIt.next() + ": " + scoreIt.next() + "\n";
	    }

	    writer.write(scoresString, 0, scoresString.length());
	}
	catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	finally {
	    try { writer.close(); }
	    catch(Exception e) { System.out.println("Error: Failed to save the scores." + e); }
	}
    }

    public void addScore(String player, int score) {
	ListIterator playerIt = mPlayers.listIterator();
	ListIterator scoreIt = mScores.listIterator();
	boolean add = true;
	
	while(playerIt.hasNext() && scoreIt.hasNext()) {

	    playerIt.next();
	    if(score >= (Integer) scoreIt.next()) {

		playerIt.previous();
		scoreIt.previous();

		playerIt.add(player);
		scoreIt.add(score);

		add = false;
		break;
	    }

	}

	if(add) {
	    playerIt.add(player);
	    scoreIt.add(score);
	}

	saveScores();
    }

    public void showScores() {
	
	JFrame frame = new JFrame("Puntuaciones");
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	ListIterator playerIt = mPlayers.listIterator();
	ListIterator scoreIt = mScores.listIterator();	
	while(playerIt.hasNext() && scoreIt.hasNext()) {
	    panel.add(new JLabel(playerIt.next() + ": " + scoreIt.next()));
	}

	JButton btnExit = new JButton("Salir");
	btnExit.addActionListener(this);
	
	frame.setLayout(new BorderLayout());
	frame.add(new JLabel("PUNTUACIONES:"), BorderLayout.NORTH);
	frame.add(new JScrollPane(panel), BorderLayout.CENTER);
	frame.add(btnExit, BorderLayout.SOUTH);

	frame.setPreferredSize(new Dimension(200, 200));

	frame.setLocationRelativeTo(null);
	frame.pack();
	frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
	
	if(e.getActionCommand().equals("salir"))
	    ;

    }

}
