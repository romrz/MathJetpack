package mathjetpack;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ScoresFrame extends JFrame implements ActionListener {

    private String mSourceFile = "target/classes/scores.txt";

    private File mFile;
    private JTextArea mContent;

    public ScoresFrame() {
	super("Puntuaciones");
	
	BufferedReader reader = null;
	String text = "";
	
	try {
	    reader = new BufferedReader(new FileReader(mSourceFile));
	    
	    String line;
	    while((line = reader.readLine()) != null) {
		text += (line + "\n");
	    }
	}
	catch(Exception e) {
	    System.out.println("Error: Failed to load the Scores." + e);
	}
	finally {
	    try { reader.close(); }
	    catch(Exception e) { System.out.println("Error: Failed to load the Scores."); }
	}
	
	mContent = new JTextArea(text, 10, 30);
	mContent.setMargin(new Insets(5, 5, 5, 5));
	mContent.setEditable(false);

	JButton btnExit = new JButton("Salir");

	btnExit.addActionListener(this);
	
	setLayout(new BorderLayout());

	//	String lbl = "Formato de preguntas: pregunta opcion1 opcion2 *opcion_correcta";
	//add(new JLabel(lbl), BorderLayout.NORTH);
	//add(new JScrollPane(mContent), BorderLayout.CENTER);
	add(mContent, BorderLayout.CENTER);
	add(btnExit, BorderLayout.SOUTH);
	
	
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	pack();
	setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
	
	if(e.getActionCommand().equals("salir"))
	    ;

    }

}
