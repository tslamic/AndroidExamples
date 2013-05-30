package com.tslamic.loader.json;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tslamic.loader.R;
import com.tslamic.loader.generic.LoaderActivity;

import java.util.ArrayList;
import java.util.List;

public class JsonLoaderActivity extends LoaderActivity implements LoaderManager.LoaderCallbacks<List<Integer>> {

    private ArrayAdapter<Integer> mAdapter;
    private List<Integer> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mList = new ArrayList<Integer>();
        mAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, mList);
        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Integer>> onCreateLoader(int i, Bundle bundle) {
        return new IntegerLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Integer>> listLoader, List<Integer> integers) {
        mList.clear();
        mList.addAll(integers);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Integer>> listLoader) {
        mList.clear();
        mAdapter.notifyDataSetChanged();
    }

}
