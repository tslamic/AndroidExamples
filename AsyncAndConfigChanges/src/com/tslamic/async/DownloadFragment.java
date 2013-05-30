package com.tslamic.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;

public class DownloadFragment extends Fragment {

    public static final String TAG = "DownloadFragment";

    private Callback<Void> mCallback;
    private DownloadTask mTask;
    private boolean mIsRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback<Void>) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException("Activity must implement DownloadTask.Callback!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void start() {
        if (!mIsRunning) {
            mTask = new DownloadTask();
            mTask.execute();
            mIsRunning = true;
        }
    }

    public void cancel() {
        if (mIsRunning) {
            mTask.cancel(true);
            mTask = null;
            mIsRunning = false;
        }
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }

    public static interface Callback<T> {

        void onDownloadStart();

        void onDownloadComplete(T result);

        void onDownloadCancelled();

        void onDownloadProgressUpdate(int percent);

    }

    private class DownloadTask extends AsyncTask<Void, Integer, Void> {

        private static final int SLEEP = 200;

        @Override
        protected void onPreExecute() {
            if (null != mCallback) mCallback.onDownloadStart();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(SLEEP);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            final int percent = values[0];
            if (null != mCallback) mCallback.onDownloadProgressUpdate(percent);
        }

        @Override
        protected void onCancelled() {
            if (null != mCallback) mCallback.onDownloadCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (null != mCallback) mCallback.onDownloadComplete(result);
        }

    }

}
