package com.tslamic.blog.visibility;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mSecondTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSecondTextView = (TextView) findViewById(R.id.snd);
    }

    public void onVisible(final View view) {
        mSecondTextView.setVisibility(View.VISIBLE);
    }

    public void onInvisible(final View view) {
        mSecondTextView.setVisibility(View.INVISIBLE);
    }

    public void onGone(final View view) {
        mSecondTextView.setVisibility(View.GONE);
    }

}
