package com.tslamic.traein.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tslamic.traein.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationDataAdapter extends BaseAdapter {

    private final Map<String, String> mMap;
    private final List<String> mKeys;
    private final Context mContext;

    public StationDataAdapter(Map<String, String> map, Context context) {
        mMap = map;
        mKeys = new ArrayList<String>(map.keySet());
        mContext = context;
    }

    @Override
    public int getCount() {
        return mKeys.size();
    }

    @Override
    public Object getItem(int position) {
        return mMap.get(mKeys.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.station_data_list_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final String key = mKeys.get(position);
        holder.title.setText(key);
        holder.description.setText(mMap.get(key));

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class Holder {

        public final TextView title;
        public final TextView description;

        Holder(View view) {
            title = (TextView) view.findViewById(R.id.station_data_list_item_title);
            description = (TextView) view.findViewById(R.id.station_data_list_item_description);
        }

    }

}
