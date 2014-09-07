package com.tslamic.traein.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.tslamic.traein.R;
import com.tslamic.traein.util.Util;
import com.tslamic.traein.web.TraeinRequest;
import com.tslamic.traein.web.TraeinWebClient;
import com.tslamic.traein.web.VolleyStringResponse;
import com.tslamic.traein.web.response.ResponseAction;

public class TaskFragment extends DialogFragment implements VolleyStringResponse {

    public static final String TAG = TaskFragment.class.getName();

    private String mUrl;
    private ResponseAction mResponseAction;
    private boolean mIsRequestRunning;
    private Handler mHandler;

    /** Always use this for instantiation. */
    public static TaskFragment newInstance(String url, ResponseAction responseAction) {
        final TaskFragment fragment = new TaskFragment();

        fragment.setCancelable(false);
        fragment.mUrl = url.replace(" ", "%20");
        fragment.mResponseAction = responseAction;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mHandler = new Handler();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.task_dialog_layout);

        final Button cancel = (Button) dialog.findViewById(R.id.task_dialog_layout_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Don't re-execute it if there's an orientation change.
        if (!mIsRequestRunning) {
            TraeinWebClient.getInstance().add(getRequest());
            mIsRequestRunning = true;
        }

        return dialog;
    }

    @Override
    public void onDestroyView() {
        // There is an issue with the support library and DialogFragments. To preserve the Dialog when
        // doing an orientation change, use the code below to avoid the bug.
        // See more at http://stackoverflow.com/a/12434038/905349.
        if (getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Error occurred. Are you online?", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onResponse(final String response) {
        Util.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                mResponseAction.onResponseAction(getActivity(), TaskFragment.this, mHandler, response);
            }
        });
    }

    @Override
    public void dismiss() {
        mIsRequestRunning = false;
        TraeinWebClient.getInstance().cancelAll(TAG);
        super.dismiss();
    }

    private TraeinRequest getRequest() {
        return new TraeinRequest(mUrl, TAG, this);
    }

}
