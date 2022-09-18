package be.cyimena.topquiz.models;

import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex = 0;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        this.mQuestionList = questionList;
    }

    public Question getCurrentQuestion() {
        // Loop over the questions and return a new one at each call
        return mQuestionList.get(mNextQuestionIndex);
    }

    public Question getNextQuestion() {
        // Loop over the questions and return a new one at each call
        mNextQuestionIndex += 1;
        return mQuestionList.get(mNextQuestionIndex);
    }
}
