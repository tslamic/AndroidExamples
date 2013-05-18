package com.tslamic.loader.generic;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import com.tslamic.loader.content.DummyContentProvider;
import com.tslamic.loader.content.DummyDatabase;

public class AddTask extends AsyncTask<Context, Void, Void> {

    private static final int SLEEP = 2000;
    private static final int COUNT = 5;

    @Override
    protected Void doInBackground(Context... ctx) {
        final Context context = ctx[0];

        for (int i = 0; !isCancelled() && i < COUNT; i++) {
            SystemClock.sleep(SLEEP);
            final ContentValues values = DummyDatabase.getRandomlyPopulatedContentValues();
            context.getContentResolver().insert(DummyContentProvider.URI_ITEM, values);
        }

        return null;
    }

}
