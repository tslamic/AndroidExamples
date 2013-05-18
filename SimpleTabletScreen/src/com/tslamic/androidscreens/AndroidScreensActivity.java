package com.tslamic.androidscreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class AndroidScreensActivity extends FragmentActivity implements QuestionListener {

    private QuestionFragment questionsFragment;
    private AnswersFragment answersFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final FragmentManager manager = getSupportFragmentManager();

        // Retrieve Fragments (it's not guaranteed they exist)
        questionsFragment = (QuestionFragment) manager.findFragmentById(R.id.questions);
        answersFragment = (AnswersFragment) manager.findFragmentById(R.id.answers);

        // Add Questions
        if (null == questionsFragment) {
            questionsFragment = new QuestionFragment();
            manager.beginTransaction().add(R.id.questions, questionsFragment).commit();
        }

        // This checks if a container for answers is available. If it is, it means we're using layout-large-land
        // thus able to add answersFragment.
        final boolean shouldDisplayAnswers = (null != findViewById(R.id.answers));
        if (shouldDisplayAnswers && null == answersFragment) {
            answersFragment = new AnswersFragment();
            manager.beginTransaction().add(R.id.answers, answersFragment).commit();
        }
    }

    @Override
    public void onQuestionSelected(int index) {
        final String answer = getResources().getStringArray(R.array.answers)[index];
        if (null != answersFragment && answersFragment.isVisible()) {
            answersFragment.setAnswer(answer);
        } else {
            final Intent i = new Intent(this, AnswerActivity.class);
            i.putExtra(QuestionListener.BUNDLE_ANSWER_KEY, answer);
            startActivity(i);
        }
    }

}
