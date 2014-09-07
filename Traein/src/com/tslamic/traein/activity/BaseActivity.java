package com.tslamic.traein.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import com.tslamic.traein.R;
import com.tslamic.traein.fragment.TaskFragment;
import com.tslamic.traein.util.Util;
import com.tslamic.traein.web.response.ResponseAction;
import com.tslamic.traein.web.response.ResponseActionFactory;

/** Base Activity for all other Activities in this package. */
class BaseActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.open,
                                                  R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void execTaskFragment(String url, ResponseAction action) {
        mDrawerLayout.closeDrawers();

        final FragmentManager manager = getSupportFragmentManager();
        TaskFragment fragment = (TaskFragment) manager.findFragmentByTag(TaskFragment.TAG);
        if (null != fragment) {fragment.dismiss();}

        fragment = TaskFragment.newInstance(url, action);
        fragment.show(manager, TaskFragment.TAG);
    }

    ////////////////////
    // Drawer Actions //
    ////////////////////

    public void onLiveFeed(View view) {
        execTaskFragment(Util.URL_LIVE_FEED, ResponseActionFactory.forLiveFeed());
    }

    public void onAll(View view) {
        execTaskFragment(Util.URL_STATIONS_ALL, ResponseActionFactory.forStation());
    }

    public void onMainline(View view) {
        execTaskFragment(Util.URL_STATIONS_MAINLINE, ResponseActionFactory.forStation());
    }

    public void onDart(View view) {
        execTaskFragment(Util.URL_STATIONS_DART, ResponseActionFactory.forStation());
    }

    public void onSuburb(View view) {
        execTaskFragment(Util.URL_STATIONS_SUBURBAN, ResponseActionFactory.forStation());
    }

}