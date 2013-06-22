package com.tslamic.dynamiclistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class IntegerAdapter extends ArrayAdapter<Integer> {

    private final DynamicLoadListener mListener;

    public IntegerAdapter(Context context, List<Integer> data, DynamicLoadListener listener) {
        super(context, android.R.layout.simple_list_item_1, data);
        mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // This is the only juicy part:
        // Using the DEFAULT_LOAD_FACTOR, we determine at which position should a request for more data be triggered.
        // The reloadPosition serves as a safety net.
        final int reload = Math.round(getCount() * DynamicLoadListener.DEFAULT_LOAD_FACTOR);
        final int reloadPosition = Math.max(position, reload);
        if (position == reloadPosition) {
            if (null != mListener) mListener.onLoadRequested();
        }

        return super.getView(position, convertView, parent);
    }

}
