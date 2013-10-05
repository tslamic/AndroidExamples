package com.tslamic.simpleanimations;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class SlidingPanel extends LinearLayout {

    private boolean mIsOpen;

    public SlidingPanel(Context context) {
        super(context);
        init();
    }

    public SlidingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundColor(Color.RED);
        mIsOpen = View.VISIBLE == getVisibility();
    }

    private Animation openPanelAnimation() {
        final Animation animation = new TranslateAnimation(0, 0, getHeight(), 0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { setVisibility(View.VISIBLE); }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {}
        });
        return animation;
    }

    private Animation closePanelAnimation() {
        final Animation animation = new TranslateAnimation(0, 0, 0, getHeight());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) { setVisibility(View.INVISIBLE); }
        });
        return animation;
    }

    public void toggle() {
        final Animation animation = mIsOpen ? closePanelAnimation() : openPanelAnimation();
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(500);

        mIsOpen = !mIsOpen;
        startAnimation(animation);
    }

}
