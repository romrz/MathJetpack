package mathjetpack;

import mathjetpack.entity.Question;
import mathjetpack.entity.Option;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class QuestionManager {

    private Game mGame;
    private ArrayList<Question> mQuestions;

    public QuestionManager(Game game) {
        mGame = game;
        mQuestions = new ArrayList<Question>();
        
    }

    public Question getQuestion() {
        Random random = new Random();

        int number1 = random.nextInt(4) + 1;
        int number2 = random.nextInt(4) + 1;
        int result = number1 + number2;

        int option1 = result + random.nextInt(2) + 1;
        int option2 = result - random.nextInt(2) + 1;
        
        List<Integer> options = new ArrayList();
        options.add(result);
        options.add(option1);
        options.add(option2);
        Collections.shuffle(options);

        Question q = new Question(number1 + "+" + number2 + "=");
        for(int option : options) {
            if(option == result) {
                q.addOption(new Option(""+option, true));
            }
            else {
                q.addOption(new Option(""+option));
            }
        }
        
        return q;
    }
}
