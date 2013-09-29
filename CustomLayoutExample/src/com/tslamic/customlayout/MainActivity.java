package com.tslamic.customlayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private static final int[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    private CascadeLayout mLayout;
    private int mViewCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLayout = (CascadeLayout) findViewById(R.id.cascadeLayout);
    }

    public void onAdd(final View view) {
        final View sampleView = new View(this);

        final int width = getResources().getDimensionPixelSize(R.dimen.example_width);
        final int height = getResources().getDimensionPixelSize(R.dimen.example_height);

        final CascadeLayout.LayoutParams params = new CascadeLayout.LayoutParams(width, height);
        sampleView.setLayoutParams(params);

        sampleView.setBackgroundColor(COLORS[mViewCounter++ % COLORS.length]);

        mLayout.addView(sampleView);
        mLayout.invalidate();
    }

    public void onRemove(final View view) {
        final int children = mLayout.getChildCount();
        if (children > 0) {
            mLayout.removeViewAt(children - 1);
            mLayout.invalidate();
        }
    }

}
