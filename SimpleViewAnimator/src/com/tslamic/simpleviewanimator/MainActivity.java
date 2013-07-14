package com.tslamic.simpleviewanimator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

public class MainActivity extends Activity {

    private static final int[] COLORS = {
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.TRANSPARENT,
            Color.WHITE,
            Color.YELLOW,
    };

    private ViewAnimator mAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAnimator = (ViewAnimator) findViewById(R.id.animator);
        populateAnimator();
    }

    public void onBack(final View view) {
        mAnimator.showPrevious();
    }

    public void onNext(final View view) {
        mAnimator.showNext();
    }

    private void populateAnimator() {
        for (int i = 0; i < COLORS.length; i++) {
            final ImageView view = new ImageView(this);
            view.setBackgroundColor(COLORS[i]);
            mAnimator.addView(view);
        }
    }

}
