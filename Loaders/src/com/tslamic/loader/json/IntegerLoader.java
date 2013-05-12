package com.tslamic.loader.json;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import com.tslamic.loader.DummyContentProvider;
import com.tslamic.loader.DummyDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IntegerLoader extends AsyncTaskLoader<List<Integer>> {

    private static final String[] PROJECTION = new String[]{DummyDatabase.DB_JSON};
    private static final int DEFAULT_INTEGER = -1;

    private final ForceLoadContentObserver mObserver;
    private List<Integer> mData;

    public IntegerLoader(final Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    @Override
    public List<Integer> loadInBackground() {
        final List<Integer> list = getEmptyList();
        Cursor cursor = null;

        try {
            cursor = getContext().getContentResolver()
                    .query(DummyContentProvider.URI_JSON, PROJECTION, null, null, null);
            list.addAll(parseJsonFromCursor(cursor));
        } finally {
            if (null != cursor) cursor.close();
        }

        return list;
    }

    @Override
    public void deliverResult(List<Integer> data) {
        if (isReset()) {
            mData = null;
            return;
        }

        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (null != mData) {
            deliverResult(mData);
        }

        getContext().getContentResolver().registerContentObserver(DummyContentProvider.URI_ITEM, false, mObserver);

        if (takeContentChanged() || null == mData) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mData = null;
        getContext().getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    public void onCanceled(List<Integer> data) {
        super.onCanceled(data);
        mData = null;
    }

    private static List<Integer> parseJsonFromCursor(final Cursor cursor) {
        final List<Integer> list = getEmptyList();

        if (null == cursor || 0 == cursor.getCount()) {
            return list;
        }

        while (cursor.moveToNext()) {
            try {
                JSONObject jsonObject = new JSONObject(cursor.getString(0));
                list.add(jsonObject.getInt("integer"));
            } catch (JSONException e) {
                list.add(DEFAULT_INTEGER);
            }
        }

        return list;
    }

    private static List<Integer> getEmptyList() {
        return new ArrayList<Integer>();
    }

}
