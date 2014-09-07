package com.tslamic.traein.adapter;

import android.R;
import android.content.Context;
import android.database.MatrixCursor;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import com.tslamic.traein.orm.Station;

import java.util.List;

public class StationAdapter extends ArrayAdapter<String> implements SectionIndexer {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String COLUMN_NAME = "StationAdapter.COLUMN_NAME";
    private static final String[] COLUMNS = {COLUMN_NAME};

    private AlphabetIndexer mAlphabetIndexer;

    public StationAdapter(List<Station> stations, Context context) {
        super(context, R.layout.simple_list_item_1, stationToStringArray(stations));
        mAlphabetIndexer = new AlphabetIndexer(getCursor(), 0, ALPHABET);
    }

    @Override
    public Object[] getSections() {
        return mAlphabetIndexer.getSections();
    }

    @Override
    public int getPositionForSection(int section) {
        return mAlphabetIndexer.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mAlphabetIndexer.getSectionForPosition(position);
    }

    private MatrixCursor getCursor() {
        final int size = getCount();
        final MatrixCursor cursor = new MatrixCursor(COLUMNS, size);

        final Object[] data = new Object[1];
        for (int i = 0; i < size; i++) {
            data[0] = getItem(i).toString();
            cursor.addRow(data);
        }

        return cursor;
    }

    private static String[] stationToStringArray(List<Station> stations) {
        final int size = stations.size();

        final String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = stations.get(i).name;
        }

        return array;
    }

}
