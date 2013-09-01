package com.tslamic.unitcircle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private UnitCircleView mUnitCircleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mUnitCircleView = (UnitCircleView) findViewById(R.id.unitCircle);
        if (null != savedInstanceState) {
            final float x = savedInstanceState.getFloat(Util.KEY_CURRENT_POINT_X);
            final float y = savedInstanceState.getFloat(Util.KEY_CURRENT_POINT_Y);
            mUnitCircleView.setCurrentPoint(new PointF(x, y));
        }

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        applyPreferences(preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        applyPreferences(sharedPreferences);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        final PointF point = mUnitCircleView.getCurrentPoint();
        outState.putFloat(Util.KEY_CURRENT_POINT_X, point.x);
        outState.putFloat(Util.KEY_CURRENT_POINT_Y, point.y);
        super.onSaveInstanceState(outState);
    }

    public void onShowSettings(final View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void applyPreferences(final SharedPreferences preferences) {
        final boolean showSin = preferences.getBoolean(Util.KEY_SHOW_SINE, true);
        final boolean showCos = preferences.getBoolean(Util.KEY_SHOW_COSINE, true);
        final String angleUnit = preferences.getString(Util.KEY_ANGLE_UNIT, String.valueOf(Util.ANGLE_UNIT_DEGREES));

        mUnitCircleView.showSine(showSin);
        mUnitCircleView.showCosine(showCos);
        mUnitCircleView.setAngleUnit(Integer.valueOf(angleUnit));
    }

}
