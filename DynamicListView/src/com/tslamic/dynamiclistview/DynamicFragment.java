package com.tslamic.dynamiclistview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicFragment extends ListFragment implements DynamicLoadListener {

    private IntegerAdapter mAdapter;
    private View mFooterView;
    private LoaderTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFooterView = inflater.inflate(R.layout.footer, null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView mListView = getListView();
        mListView.addFooterView(mFooterView);

        if (null == mAdapter) {
            final List<Integer> data = generateRandomIntegerList(10);
            mAdapter = new IntegerAdapter(getActivity(), data, this);
        }

        setListAdapter(mAdapter);
    }

    @Override
    public void onLoadRequested() {
        if (!isLoaderTaskRunning()) {
            mTask = new LoaderTask();
            mTask.execute();
        }
    }

    private boolean isLoaderTaskRunning() {
        return (null != mTask) && mTask.isRunning();
    }

    private static List<Integer> generateRandomIntegerList(final int size) {
        final ArrayList<Integer> list = new ArrayList<Integer>(size);
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt());
        }
        return list;
    }

    private class LoaderTask extends AsyncTask<Void, Void, List<Integer>> {
        @Override
        protected List<Integer> doInBackground(Void... voids) {
            SystemClock.sleep(3000);
            return generateRandomIntegerList(10);
        }

        @Override
        protected void onPostExecute(List<Integer> integers) {
            for (Integer integer : integers) mAdapter.add(integer);
        }

        public boolean isRunning() {
            return Status.RUNNING == getStatus();
        }
    }

}
