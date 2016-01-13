package mathjetpack;

import mathjetpack.entity.Question;
import mathjetpack.entity.Option;

import java.util.Random;
import java.util.ArrayList;

public class QuestionManager {

    private Game mGame;
    private ArrayList<Question> mQuestions;

    public QuestionManager(Game game) {
        mGame = game;
        mQuestions = new ArrayList<Question>();
        
    }

    public Question getQuestion() {
        Question q = new Question("¿3x4?");
        q.addOption(new Option("23"));
        q.addOption(new Option("12", true));
        q.addOption(new Option("6"));
        
        return q;
    }
}
