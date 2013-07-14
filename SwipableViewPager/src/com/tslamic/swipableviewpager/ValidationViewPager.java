package com.tslamic.swipableviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ValidationViewPager extends ViewPager {

    public ValidationViewPager(Context context) {
        super(context);
    }

    public ValidationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof ValidationViewPagerAdapter) {
            super.setAdapter(adapter);
        } else {
            throw new IllegalArgumentException("ValidationViewPagerAdapter required");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSlide() && super.onTouchEvent(ev);
    }

    private boolean canSlide() {
        final ValidationViewPagerAdapter adapter = (ValidationViewPagerAdapter) getAdapter();
        return adapter.isFragmentValid(getCurrentItem());
    }

}
