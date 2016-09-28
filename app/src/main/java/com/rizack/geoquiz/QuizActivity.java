package com.rizack.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private static final String TAG = "QuizActivity";
    private static final String Q_INDEX = "index";
    private static final int CHEAT_CODE = 0;

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int questionId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(questionId);
    }

    private void checkAnswer(boolean userPressedTrue) {
        Toast.makeText(
            this,
            mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue ? R.string.correct_message : R.string.wrong_message,
            Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "savedInstanceState");
        Log.d(TAG, Integer.toString(mCurrentIndex));
        savedInstanceState.putInt(Q_INDEX, mCurrentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, CHEAT_CODE);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1);
                if (mCurrentIndex < 0) mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(Q_INDEX, 0);
        }

        updateQuestion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() fired");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() fired");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() fired");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() fired");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() fired");
    }
}
