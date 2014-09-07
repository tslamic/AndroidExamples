package com.tslamic.traein.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.tslamic.traein.R;

/** This adapter is used with AutoCompleteTextView where you select the desired station. */
public class StationsListAdapter extends ArrayAdapter<String> {

    public StationsListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.stations));
    }

    public boolean contains(String station) {
        for (int i = 0, size = getCount(); i < size; i++) {
            if (station.equalsIgnoreCase(getItem(i))) return true;
        }
        return false;
    }

}
