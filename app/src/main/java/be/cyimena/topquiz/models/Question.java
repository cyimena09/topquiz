package be.cyimena.topquiz.models;

import java.util.List;

public class Question {

    private final String mQuestion;
    private final List<String> mChoiceList;
    private final int mAnswerIndex;

    public Question(String mQuestion, List<String> mChoiceList, int mAnswerIndex) {
        this.mQuestion = mQuestion;
        this.mChoiceList = mChoiceList;
        this.mAnswerIndex = mAnswerIndex;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public List<String> getmChoiceList() {
        return mChoiceList;
    }

    public int getmAnswerIndex() {
        return mAnswerIndex;
    }
}
