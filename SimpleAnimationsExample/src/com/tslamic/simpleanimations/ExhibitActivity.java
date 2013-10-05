package com.tslamic.simpleanimations;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.*;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class ExhibitActivity extends Activity {

    private EditText mDuration;
    private Animation mAnimation;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit);

        final Spinner animationSpinner = (Spinner) findViewById(R.id.main_animation);
        animationSpinner.setOnItemSelectedListener(new AnimationSpinnerListener());

        final Spinner interpolatorSpinner = (Spinner) findViewById(R.id.main_interpolator);
        interpolatorSpinner.setOnItemSelectedListener(new InterpolatorSpinnerListener());

        mDuration = (EditText) findViewById(R.id.main_duration);
        mView = findViewById(R.id.main_view);
    }

    private class AnimationSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mAnimation = new TranslateAnimation(0f, 10f, 0f, 20f);
                    break;
                case 1:
                    mAnimation = new ScaleAnimation(1f, 0f, 1f, 0f);
                    break;
                case 2:
                    mAnimation = new RotateAnimation(0f, 180f);
                    break;
                case 3:
                    mAnimation = new AlphaAnimation(1f, 0f);
                    break;
                default:
                    throw new IllegalStateException("no such position: " + position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            mAnimation = new TranslateAnimation(0f, 10f, 0f, 20f);
        }
    }

    private class InterpolatorSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                    break;
                case 1:
                    mAnimation.setInterpolator(new AccelerateInterpolator());
                    break;
                case 2:
                    mAnimation.setInterpolator(new AnticipateInterpolator());
                    break;
                case 3:
                    mAnimation.setInterpolator(new AnticipateOvershootInterpolator());
                    break;
                case 4:
                    mAnimation.setInterpolator(new BounceInterpolator());
                    break;
                case 5:
                    mAnimation.setInterpolator(new CycleInterpolator(3));
                    break;
                case 6:
                    mAnimation.setInterpolator(new DecelerateInterpolator());
                    break;
                case 7:
                    mAnimation.setInterpolator(new LinearInterpolator());
                    break;
                case 8:
                    mAnimation.setInterpolator(new OvershootInterpolator());
                    break;
                default:
                    throw new IllegalStateException("no such position: " + position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            mAnimation.setInterpolator(new LinearInterpolator());
        }
    }

    public void onAnimate(View view) {
        mAnimation.setDuration(getDuration());
        mView.startAnimation(mAnimation);
    }

    private int getDuration() {
        final String input = mDuration.getText().toString();
        if (TextUtils.isEmpty(input)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                mDuration.setText("300");
                return 300;
            }
        }
    }

}
