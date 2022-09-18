package be.cyimena.topquiz.controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import be.cyimena.topquiz.R;
import be.cyimena.topquiz.models.Question;
import be.cyimena.topquiz.models.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private QuestionBank questionBank = generateQuestions();
    private Question currentQuestion;
    private int mRemainingQuestions = 2;
    private int mScore = 0;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);


        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        currentQuestion = this.questionBank.getNextQuestion();

        this.displayQuestions();
    }

    private void displayQuestions() {
        this.mQuestionTextView.setText(currentQuestion.getmQuestion());
        this.mAnswerButton1.setText(currentQuestion.getmChoiceList().get(0));
        this.mAnswerButton2.setText(currentQuestion.getmChoiceList().get(1));
        this.mAnswerButton3.setText(currentQuestion.getmChoiceList().get(2));
        this.mAnswerButton4.setText(currentQuestion.getmChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who is the creator of Android?", Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"), 0);
        Question question2 = new Question("When did the first man land on the moon?", Arrays.asList("1958", "1962", "1967", "1969"), 3);
        Question question3 = new Question("What is the house number of The Simpsons?", Arrays.asList("42", "101", "666", "742"), 3);

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == mAnswerButton1) {
            index = 0;
        } else if (view == mAnswerButton2) {
            index = 1;
        } else if (view == mAnswerButton3) {
            index = 2;
        } else if (view == mAnswerButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        if (index == questionBank.getCurrentQuestion().getmAnswerIndex()) {
            mScore++;
            Toast.makeText(this, "La réponse est correct !", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "La réponse est incorrect.", Toast.LENGTH_SHORT).show();
        }

        mRemainingQuestions -= 1;

        if (mRemainingQuestions > 0) {
            currentQuestion = questionBank.getNextQuestion();
            displayQuestions();
        } else {
            // fin du jeu
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();

        }
    }
}