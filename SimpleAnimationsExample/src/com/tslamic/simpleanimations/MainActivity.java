package com.tslamic.simpleanimations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onExhibit(View view) {
        startActivity(getIntentFor(ExhibitActivity.class));
    }

    public void onList(View view) {
        startActivity(getIntentFor(DevicesActivity.class));
    }

    public void onSlidingPanel(View view) {
        startActivity(getIntentFor(SlidingPanelActivity.class));
    }

    private Intent getIntentFor(Class<? extends Activity> clazz) {
        final Intent intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

}
