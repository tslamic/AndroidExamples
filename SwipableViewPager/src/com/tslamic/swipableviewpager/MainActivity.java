package com.tslamic.swipableviewpager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ValidationViewPager pager = (ValidationViewPager) findViewById(R.id.pager);
        final CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.pager_indicator);

        pager.setAdapter(new ValidationViewPagerAdapter(getSupportFragmentManager()));
        indicator.setViewPager(pager);
    }

}
