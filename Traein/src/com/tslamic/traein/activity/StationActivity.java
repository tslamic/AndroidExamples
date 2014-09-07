package com.tslamic.traein.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.tslamic.traein.R;
import com.tslamic.traein.fragment.StationFragment;

public class StationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.stations);

        final FragmentManager manager = getSupportFragmentManager();
        StationFragment fragment = (StationFragment) manager.findFragmentByTag(StationFragment.TAG);
        if (null == fragment) {
            fragment = new StationFragment();
            manager.beginTransaction().add(R.id.content, fragment, StationFragment.TAG).commit();
        }
    }

}
