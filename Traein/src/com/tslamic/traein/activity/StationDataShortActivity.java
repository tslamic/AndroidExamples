package com.tslamic.traein.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.tslamic.traein.R;
import com.tslamic.traein.fragment.StationDataShortFragment;

public class StationDataShortActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentManager manager = getSupportFragmentManager();
        StationDataShortFragment fragment = (StationDataShortFragment) manager
                .findFragmentByTag(StationDataShortFragment.TAG);
        if (null == fragment) {
            fragment = new StationDataShortFragment();
            manager.beginTransaction().add(R.id.content, fragment, StationDataShortFragment.TAG).commit();
        }
    }

}
