package com.tslamic.traein.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.tslamic.traein.R;
import com.tslamic.traein.fragment.IndexFragment;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentManager manager = getSupportFragmentManager();
        IndexFragment fragment = (IndexFragment) manager.findFragmentByTag(IndexFragment.TAG);
        if (null == fragment) {
            fragment = new IndexFragment();
            manager.beginTransaction().add(R.id.content, fragment, IndexFragment.TAG).commit();
        }
    }

}
