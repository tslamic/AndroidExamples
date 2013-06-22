package com.tslamic.dynamiclistview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "com.tslamic.dynamiclistview.DynamicFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager manager = getSupportFragmentManager();
        DynamicFragment fragment = (DynamicFragment) manager.findFragmentByTag(TAG);
        if (null == fragment) {
            fragment = new DynamicFragment();
            manager.beginTransaction().add(R.id.fragment, fragment, TAG).commit();
        }
    }

}
