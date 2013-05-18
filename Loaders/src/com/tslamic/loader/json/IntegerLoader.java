package com.tslamic.loader.json;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import com.tslamic.loader.content.DummyContentProvider;
import com.tslamic.loader.content.DummyDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// This loader extracts an Integer value from JSON saved in the database.
// Follow the comments in the code to get the feel of what's happening.
// Also, make sure you understand this: http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
public class IntegerLoader extends AsyncTaskLoader<List<Integer>> {

    private static final String[] PROJECTION = new String[]{DummyDatabase.Field.JSON};
    private static final int DEFAULT_INTEGER = -1;

    // This is our observer which will be listening for changes.
    private final ForceLoadContentObserver mObserver;

    // This is the current data in this Loader.
    private List<Integer> mData;

    // Constructor.
    public IntegerLoader(final Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    @Override
    public List<Integer> loadInBackground() {
        Cursor cursor = null;
        try {
            // First, we need to get the data form our database. We do this by querying our ContentResolver.
            // Note that our custom DummyContentProvider must be registered in the manifest.
            cursor = getContext().getContentResolver().query(DummyContentProvider.URI_JSON, PROJECTION, null, null,
                                                             null);
            return getIntegersFromCursor(cursor);
        } finally {
            if (null != cursor) cursor.close(); // Never forget to close the cursor.
        }
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

        getContext().getContentResolver().registerContentObserver(DummyContentProvider.URI_ITEM, true, mObserver);

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

    private static List<Integer> getIntegersFromCursor(final Cursor cursor) {
        final List<Integer> list = new ArrayList<Integer>();

        // Let's make sure our cursor actually contains any interesting data. If it doesn't, we'll just
        // return the empty list we prepared above.
        if (null == cursor || 0 == cursor.getCount()) {
            return list;
        }

        // So it looks like we've got something in the cursor. We should iterate through and extract the integers.
        // Remember, what we're getting from the cursor is the JSON value, so we need to parse it. Something may
        // go wrong during that time, so if an exception is thrown, we'll just return the default integer.
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

}
