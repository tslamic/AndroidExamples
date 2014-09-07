package com.tslamic.traein.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import com.tslamic.traein.R;
import com.tslamic.traein.activity.StationDataActivity;
import com.tslamic.traein.adapter.StationDataShortAdapter;
import com.tslamic.traein.orm.StationData;
import com.tslamic.traein.util.Util;

import java.util.List;

public class StationDataShortFragment extends TraeinListFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = StationDataShortFragment.class.getName();
    private List<StationData> mStations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStations = getActivity().getIntent().getParcelableArrayListExtra(Util.KEY_DATA);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStationTitle();

        final StationDataShortAdapter adapter = new StationDataShortAdapter(mStations, getActivity());
        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final StationData data = mStations.get(position);

        final Intent intent = new Intent(getActivity(), StationDataActivity.class);
        intent.putExtra(Util.KEY_DATA, data);

        getActivity().startActivity(intent);
    }

    private void setStationTitle() {
        if (getActivity() instanceof ActionBarActivity) {
            final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
            if (mStations.isEmpty()) {
                actionBar.setTitle(R.string.station_empty);
            } else {
                actionBar.setTitle(mStations.get(0).stationFullName);
            }
        }
    }

}
