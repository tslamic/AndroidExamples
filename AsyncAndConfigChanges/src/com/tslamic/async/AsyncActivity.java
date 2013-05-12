package com.tslamic.async;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncActivity extends FragmentActivity implements DownloadFragment.Callback<Void> {

    private static final String UPDATE_STRING = "%d%% downloaded";
    private static final String BUNDLE_KEY_MESSAGE = "msg";
    private static final String BUNDLE_KEY_BUTTON = "btn";
    private ProgressBar mProgress;
    private TextView mMessage;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initComponents(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_MESSAGE, mMessage.getText().toString());
        outState.putString(BUNDLE_KEY_BUTTON, mButton.getText().toString());
    }

    @Override
    public void onDownloadStart() {
        resetProgress();
        mMessage.setText("Download started!");
    }

    @Override
    public void onDownloadComplete(Void result) {
        resetProgress();
        mMessage.setText("Download complete!");
    }

    @Override
    public void onDownloadCancelled() {
        resetProgress();
        mMessage.setText("Download cancelled!");
    }

    @Override
    public void onDownloadProgressUpdate(int percent) {
        if (getDownloadFragment().isRunning()) {
            mProgress.setProgress(percent);
            mMessage.setText(String.format(UPDATE_STRING, percent));
        }
    }

    public void onClick(final View view) {
        final DownloadFragment downloadFragment = getDownloadFragment();
        if (downloadFragment.isRunning()) {
            downloadFragment.cancel();
            mButton.setText("Start");
        } else {
            downloadFragment.start();
            mButton.setText("Cancel");
        }
    }

    private DownloadFragment getDownloadFragment() {
        final FragmentManager manager = getSupportFragmentManager();
        DownloadFragment fragment = (DownloadFragment) manager.findFragmentByTag(DownloadFragment.TAG);
        if (null == fragment) {
            fragment = new DownloadFragment();
            manager.beginTransaction().add(fragment, DownloadFragment.TAG).commit();
        }
        return fragment;
    }

    private void initComponents(Bundle bundle) {
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mMessage = (TextView) findViewById(R.id.message);
        mButton = (Button) findViewById(R.id.button);

        if (null != bundle) {
            final String msg = bundle.getString(BUNDLE_KEY_MESSAGE);
            mMessage.setText(msg);

            final String btn = bundle.getString(BUNDLE_KEY_BUTTON);
            mButton.setText(btn);
        } else {
            mMessage.setText("Idle!");
        }
    }

    private void resetProgress() {
        mProgress.setProgress(0);
    }

}
