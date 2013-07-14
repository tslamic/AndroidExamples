package com.tslamic.swipableviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public class ValidationViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int SIZE = 10;
    private SparseArray<Validator> mValidatorFragments = new SparseArray<Validator>(SIZE);

    public ValidationViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new DemoFragment();
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        final Validator validator = (Validator) fragment;
        mValidatorFragments.append(position, validator);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mValidatorFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public boolean isFragmentValid(int position) {
        return mValidatorFragments.get(position).isValid();
    }
}
