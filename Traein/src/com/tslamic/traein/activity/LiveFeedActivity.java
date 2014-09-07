package com.tslamic.traein.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.tslamic.traein.R;
import com.tslamic.traein.fragment.StationDataFragment;

public class LiveFeedActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.live_feed);

        final FragmentManager manager = getSupportFragmentManager();
        StationDataFragment fragment = (StationDataFragment) manager.findFragmentByTag(StationDataFragment.TAG);
        if (null == fragment) {
            fragment = new StationDataFragment();
            manager.beginTransaction().add(R.id.content, fragment, StationDataFragment.TAG).commit();
        }
    }

}
