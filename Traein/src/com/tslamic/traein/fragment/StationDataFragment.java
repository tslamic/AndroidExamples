package com.tslamic.traein.fragment;

import android.os.Bundle;
import com.tslamic.traein.adapter.LiveFeedAdapter;
import com.tslamic.traein.orm.TrainPositions;
import com.tslamic.traein.util.Util;

import java.util.ArrayList;

public class StationDataFragment extends TraeinListFragment {

    public static final String TAG = StationDataFragment.class.getName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<TrainPositions> positions = getActivity().getIntent()
                .getParcelableArrayListExtra(Util.KEY_DATA);
        final LiveFeedAdapter adapter = new LiveFeedAdapter(positions, getActivity());
        getListView().setAdapter(adapter);
    }

}
