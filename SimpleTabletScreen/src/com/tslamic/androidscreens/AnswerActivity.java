package com.tslamic.androidscreens;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AnswerActivity extends Activity {

    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = new TextView(this);
        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String answer = getIntent().getStringExtra(QuestionListener.BUNDLE_ANSWER_KEY);
        view.setText(answer);
    }

}
