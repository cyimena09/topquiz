package be.cyimena.topquiz.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.cyimena.topquiz.R;
import be.cyimena.topquiz.models.User;

public class MainActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mScoreTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User user = new User();
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    public static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    public static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    public static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "La réponse est correct !", Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mTitleTextView = findViewById(R.id.main_textview_greeting);
                mScoreTextView = findViewById(R.id.main_textview_score);
                mNameEditText = findViewById(R.id.main_edittext_name);
                mPlayButton = findViewById(R.id.main_button_play);
                mPlayButton.setEnabled(false);

                int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, 0);
                String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);

                String scoreMessage = "Votre précédent score est : " + score;
                mScoreTextView.setText(scoreMessage);


                if (firstName != null) {
                    mNameEditText.setText(firstName);
                }

                mNameEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // This is where we'll check the user input
                        user.setmFirstName(mNameEditText.getText().toString());
                        mPlayButton.setEnabled(true);
                    }
                });

                mPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                                .edit()
                                .putString(SHARED_PREF_USER_INFO_NAME, user.getmFirstName())
                                .apply();
                        Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                        startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

                    }
                });








            }
        },5000);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();
        }
    }
}