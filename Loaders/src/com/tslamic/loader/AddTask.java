package com.tslamic.loader;

import android.content.Context;
import android.os.AsyncTask;

public class AddTask extends AsyncTask<Void, Void, Void> {

    private static final int SLEEP = 2000;
    private static final int COUNT = 5;

    private final Context context;

    /** Constructor. */
    public AddTask(final Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i < COUNT; i++) {
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                // Do nothing.
            }
            context.getContentResolver()
                   .insert(DummyContentProvider.URI_ITEM, DummyDatabase.getRandomlyPopulatedContentValues());
        }

        return null;
    }

}
