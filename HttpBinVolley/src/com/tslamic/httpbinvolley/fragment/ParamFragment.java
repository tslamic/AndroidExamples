package com.tslamic.httpbinvolley.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tslamic.httpbinvolley.R;
import com.tslamic.httpbinvolley.param.Param;
import com.tslamic.httpbinvolley.param.ParamAdapter;

import java.util.List;

class ParamFragment extends ListFragment {

    private ParamAdapter mParamAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == mParamAdapter) {
            // We are retaining the instance, this might already be initialized.
            mParamAdapter = new ParamAdapter(getActivity());
            setListAdapter(mParamAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.param, null);

        view.findViewById(R.id.param_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParamAdapter.addEmptyParam();
                mParamAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    List<Param> getParams() {
        return mParamAdapter.getAll();
    }

}
