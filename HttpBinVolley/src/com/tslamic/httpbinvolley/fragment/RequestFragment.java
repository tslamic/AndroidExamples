package com.tslamic.httpbinvolley.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.tslamic.httpbinvolley.R;
import com.tslamic.httpbinvolley.Web;
import com.tslamic.httpbinvolley.otto.BusProvider;
import com.tslamic.httpbinvolley.otto.event.ErrorEvent;
import com.tslamic.httpbinvolley.otto.event.ProgressEvent;
import com.tslamic.httpbinvolley.otto.event.ResponseEvent;
import com.tslamic.httpbinvolley.param.Param;
import com.tslamic.httpbinvolley.volley.HttpBinRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class RequestFragment extends ListFragment {

    private ProgressBar mProgressView;
    private ResponseAdapter mAdapter;
    private ListView mContentView;
    private TextView mErrorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == mAdapter) {
            // We are retaining the instance, this might already be initialized.
            mAdapter = new ResponseAdapter(getActivity());
            setListAdapter(mAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.request, null);

        view.findViewById(R.id.request_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(ProgressEvent.STARTED);
                Web.exec(new HttpBinRequest(Web.getJson()));

            }
        });

        view.findViewById(R.id.request_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(ProgressEvent.STARTED);
                Web.exec(new HttpBinRequest(null));
            }
        });

        mProgressView = (ProgressBar) view.findViewById(R.id.progress);
        mErrorView = (TextView) view.findViewById(R.id.request_error);
        mContentView = (ListView) view.findViewById(android.R.id.list);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        Web.cancelAll(this);
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent event) {
        mErrorView.setText(event.toString() + " : " + event.error.getMessage());
        BusProvider.getInstance().post(ProgressEvent.ERROR);
    }

    @Subscribe
    public void onResponseEvent(ResponseEvent event) {
        mAdapter.setContent(event.jsonObject);
    }

    @Subscribe
    public void onProgressEvent(ProgressEvent event) {
        switch (event) {
            case COMPLETED:
                mProgressView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mContentView.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                mProgressView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                mContentView.setVisibility(View.GONE);
                break;
            case STARTED:
                mProgressView.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                mContentView.setVisibility(View.GONE);
                break;
        }
    }

    private static class ResponseAdapter extends BaseAdapter {

        private SparseArray<Param> mData;
        private final Context mContext;

        public ResponseAdapter(final Context context) {
            mData = new SparseArray<Param>();
            mContext = context;
        }

        public void setContent(JSONObject object) {
            mData = new SparseArray<Param>(object.length());
            @SuppressWarnings("unchecked") final Iterator<String> iterator = object.keys();
            int position = 0;

            while (iterator.hasNext()) {
                final Param param = new Param();
                param.key = iterator.next();
                try {
                    param.value = object.getString(param.key);
                } catch (JSONException e) {
                    param.value = "Failed to parse value.";
                }
                mData.put(position++, param);
            }

            notifyDataSetChanged();
            BusProvider.getInstance().post(ProgressEvent.COMPLETED);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position).value;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;

            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.request_list_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            final Param param = mData.get(position);
            holder.key.setText(param.key);
            holder.value.setText(param.value);

            return view;
        }

        private static class ViewHolder {

            public final TextView key;
            public final TextView value;

            public ViewHolder(final View view) {
                key = (TextView) view.findViewById(R.id.list_item_key);
                value = (TextView) view.findViewById(R.id.list_item_value);
            }

        }

    }

}
