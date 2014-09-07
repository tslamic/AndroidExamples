package com.tslamic.traein.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tslamic.traein.R;
import com.tslamic.traein.adapter.FavoritesAdapter;
import com.tslamic.traein.adapter.StationsListAdapter;
import com.tslamic.traein.util.Util;
import com.tslamic.traein.web.response.ResponseAction;
import com.tslamic.traein.web.response.ResponseActionFactory;

public class IndexFragment extends Fragment implements FavoritesAdapter.FavoriteListener {

    public static final String TAG = IndexFragment.class.getName();

    private StationsListAdapter mStationAdapter;
    private FavoritesAdapter mFavoritesAdapter;

    private AutoCompleteTextView mStation;
    private ImageView mAddToFavorites;
    private Button mAction;
    private ListView mFavorites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.index, container, false);

        mStationAdapter = new StationsListAdapter(getActivity());
        mStation = (AutoCompleteTextView) view.findViewById(R.id.favorite_text);
        mStation.addTextChangedListener(new FavoriteTextWatcher());
        mStation.setAdapter(mStationAdapter);

        mAddToFavorites = (ImageView) view.findViewById(R.id.favorite_star);
        mAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String station = mStation.getText().toString();
                if (!mFavoritesAdapter.contains(station)) {
                    mFavoritesAdapter.add(mStation.getText().toString());
                }
            }
        });

        mAction = (Button) view.findViewById(R.id.index_action);
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execRequestFor(mStation.getText().toString());
            }
        });

        mFavorites = (ListView) view.findViewById(R.id.index_favorites);
        if (null == mFavoritesAdapter) {
            mFavoritesAdapter = new FavoritesAdapter(Util.getFavorites(getActivity()), this, getActivity());
        }
        mFavorites.setAdapter(mFavoritesAdapter);
        mFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                execRequestFor(mFavoritesAdapter.getFavorite(position));
            }
        });

        return view;
    }

    @Override
    public void onRemove(String station, int position) {
        mFavoritesAdapter.remove(position);
    }

    private void execRequestFor(String station) {
        final String url = Util.getStationDataByNameUrl(station);
        final ResponseAction action = ResponseActionFactory.forStationData();
        final TaskFragment fragment = TaskFragment.newInstance(url, action);
        fragment.show(getActivity().getSupportFragmentManager(), TaskFragment.TAG);
    }

    private class FavoriteTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mStationAdapter.contains(s.toString())) {
                mAction.setEnabled(true);
                mAddToFavorites.setVisibility(View.VISIBLE);
            } else {
                mAddToFavorites.setVisibility(View.GONE);
                mAction.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing.
        }

    }

}
