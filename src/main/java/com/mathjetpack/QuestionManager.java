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

public class QuestionManager extends JFrame implements ActionListener {

    private String mSourceFile = "target/classes/questions/questions.txt";

    private File mFile;
    private JTextArea mContent;

    public QuestionManager() {
	super("Administrador de Preguntas");
	
	mFile = new File(mSourceFile);

	BufferedReader reader = null;
	String text = "";
	
	try {
	    reader = new BufferedReader(new FileReader(mFile));
	    
	    String line;
	    while((line = reader.readLine()) != null) {
		text += (line + "\n");
	    }
	}
	catch(Exception e) {
	    System.out.println("Error: Failed to load the Questions." + e);
	}
	finally {
	    try {
		reader.close();
	    }
	    catch(Exception e) {
		System.out.println("Error: Failed to load the Questions.");
	    }
	}
	
	mContent = new JTextArea(text, 20, 50);
	mContent.setMargin(new Insets(5, 5, 5, 5));

	JButton btnSave = new JButton("Guardar");
	JButton btnExit = new JButton("Salir");

	btnSave.addActionListener(this);
	btnExit.addActionListener(this);
	
	setLayout(new BorderLayout());

	JPanel panel = new JPanel();
	panel.add(btnSave);
	panel.add(btnExit);


	String lbl = "Formato de preguntas: pregunta opcion1 opcion2 *opcion_correcta";
	add(new JLabel(lbl), BorderLayout.NORTH);
	add(new JScrollPane(mContent), BorderLayout.CENTER);
	add(panel, BorderLayout.SOUTH);
	
	
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	pack();
	setVisible(true);

    }

    public void save() {
	
	BufferedWriter writer = null;

	try {
	    writer = new BufferedWriter(new FileWriter(mFile));
	    String string = mContent.getText();
	    writer.write(string, 0, string.length());
	}
	catch(Exception e) {
	    JOptionPane.showMessageDialog(this, "Error al guardar", "Administrador de preguntas", JOptionPane.ERROR_MESSAGE);
	}
	finally {
	    try {
		writer.close();
	    }
	    catch(Exception e) {
		JOptionPane.showMessageDialog(this, "Error al guardar", "Administrador de preguntas", JOptionPane.ERROR_MESSAGE);
	    }
	}

	JOptionPane.showMessageDialog(this, "Se ha guardado correctamente", "Administrador de preguntas", JOptionPane.INFORMATION_MESSAGE);

    }

    public void actionPerformed(ActionEvent e) {
	
	if(e.getActionCommand().equals("Guardar"))
	    save();
	else if(e.getActionCommand().equals("salir"))
	    ;

    }

}
