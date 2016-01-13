package mathjetpack;

import mathjetpack.entity.Question;
import mathjetpack.entity.Option;

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
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Random;
import java.util.ArrayList;

public class QuestionManager implements ActionListener {

    private Game mGame;
    private ArrayList<Question> mQuestions;
    private JFrame mFrame;
    private String mFile1 = "/questions/questions.txt";
    private String mFile2 = "./questions.txt";
    private JTextArea content;

    public QuestionManager(Game game) {
        mGame = game;
        mQuestions = new ArrayList<Question>();

        load();
    }

    public Question getQuestion() {
        Random rand = new Random();
        return mQuestions.get(rand.nextInt(mQuestions.size()));
    }

    public void showFrame() {

        BufferedReader reader = null;
        String text = "";
	
        try {

            try {
                reader = new BufferedReader(new FileReader(mFile2));
            } catch(Exception e) {
                reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(mFile1)));
            }
	    
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

        mFrame = new JFrame("Preguntas");
        content = new JTextArea(text, 20, 50);
        content.setMargin(new Insets(5, 5, 5, 5));

        JButton btnSave = new JButton("Guardar");
        JButton btnExit = new JButton("Salir");

        btnSave.addActionListener(this);
        btnExit.addActionListener(this);
	
        mFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(btnSave);
        panel.add(btnExit);

        String lbl = "Formato de preguntas: pregunta opcion1 opcion2 *opcion_correcta";
        mFrame.add(new JLabel(lbl), BorderLayout.NORTH);
        mFrame.add(new JScrollPane(content), BorderLayout.CENTER);
        mFrame.add(panel, BorderLayout.SOUTH);	
	
        //setDefaultCloseOperation(JMFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null);
        mFrame.pack();
        mFrame.setVisible(true);

    }

    private void load() {

        Question question = null;
        Option option = null;

        String questionInfo[] = null;
        boolean correctOption;
        String line;
        String opt;

        Vector2 relVel = mGame.getPlayer().getVelocity();

        mQuestions.clear();

        BufferedReader reader = null;
        try {
	    
            try {
                reader = new BufferedReader(new FileReader(mFile2));
            } catch(Exception e) {
                reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(mFile1)));
            }

            while((line = reader.readLine()) != null) {
	    
                questionInfo = line.split(" ");
		
                question = new Question(questionInfo[0]);

                for(int i = 1; i < 4; i++) {
		
                    correctOption = false;
    
                    opt = questionInfo[i];

                    if(opt.startsWith("*")) {
                        opt = opt.substring(1);
                        correctOption = true;
                    }

                    option = new Option(opt);
                    option.setPosition(mGame.getWidth() - option.getWidth() - 20, (i) * mGame.getHeight() / 5);
                    option.setCorrect(correctOption);
                    option.setRelativeVelocity(relVel);
                    question.addOption(option);
                }
		
                mQuestions.add(question);		
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
                System.out.println("Error: Failed to load the Questionss.");
            }
        }
    }

    public void save() {
	
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(mFile2));

            String string = content.getText();

            writer.write(string, 0, string.length());
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(mFrame, "Error al guardar", "Administrador de preguntas", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                writer.close();
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(mFrame, "Error al guardar", "Administrador de preguntas", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(mFrame, "Se ha guardado correctamente", "Administrador de preguntas", JOptionPane.INFORMATION_MESSAGE);

    }

    public void actionPerformed(ActionEvent e) {
	
        if(e.getActionCommand().equals("Guardar")) {
            save();
            load();
        }
        else if(e.getActionCommand().equals("salir"))
            ;

    }

}
