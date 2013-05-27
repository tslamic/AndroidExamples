package com.tslamic.httpbinvolley;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import com.tslamic.httpbinvolley.fragment.ArgsFragment;
import com.tslamic.httpbinvolley.fragment.JsonFragment;
import com.tslamic.httpbinvolley.fragment.RequestFragment;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("reqs").setIndicator("Request"), RequestFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("args").setIndicator("Args"), ArgsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("json").setIndicator("JSON"), JsonFragment.class, null);
    }

}