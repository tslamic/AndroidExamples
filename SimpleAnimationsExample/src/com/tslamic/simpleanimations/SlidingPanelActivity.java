package com.tslamic.simpleanimations;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SlidingPanelActivity extends Activity {

    private SlidingPanel mPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_panel);
        mPanel = (SlidingPanel) findViewById(R.id.panel);
    }

    public void onToggle(View view) {
        mPanel.toggle();
    }

}
