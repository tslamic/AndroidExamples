package com.tslamic.loader.cursor;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import com.tslamic.loader.DummyContentProvider;
import com.tslamic.loader.DummyDatabase;
import com.tslamic.loader.LoaderActivity;
import com.tslamic.loader.R;

public class CursorLoaderActivity extends LoaderActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] DB_FIELDS = new String[]{DummyDatabase.DB_INFO, DummyDatabase.DB_JSON};
    private static final int[] RESOURCE_FIELDS = {R.id.listItemTitle, R.id.listItemJson};

    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mAdapter = new SimpleCursorAdapter(this, R.layout.list_item, null, DB_FIELDS, RESOURCE_FIELDS, 0);
        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, DummyContentProvider.URI_ITEM, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

}
