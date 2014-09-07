package com.tslamic.traein.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.tslamic.traein.R;
import com.tslamic.traein.adapter.StationAdapter;
import com.tslamic.traein.orm.Station;
import com.tslamic.traein.util.Util;
import com.tslamic.traein.web.response.ResponseAction;
import com.tslamic.traein.web.response.ResponseActionFactory;

import java.util.List;

public class StationFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = StationFragment.class.getName();

    private ArrayAdapter<String> mStationsAdapter;
    private List<Station> mStations;
    private EditText mFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStations = getActivity().getIntent().getParcelableArrayListExtra(Util.KEY_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_filterable, container, false);

        mFilter = (EditText) view.findViewById(R.id.list_filterable_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStationsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStationsAdapter = new StationAdapter(mStations, getActivity());
        setListAdapter(mStationsAdapter);

        getListView().setFastScrollEnabled(true);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String station = mStationsAdapter.getItem(position);

        final String url = Util.getStationDataByNameUrl(station);
        final ResponseAction action = ResponseActionFactory.forStationData();

        final TaskFragment fragment = TaskFragment.newInstance(url, action);
        fragment.show(getActivity().getSupportFragmentManager(), TaskFragment.TAG);
    }

}
