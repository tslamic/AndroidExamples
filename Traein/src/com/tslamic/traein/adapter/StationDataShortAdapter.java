package com.tslamic.traein.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tslamic.traein.R;
import com.tslamic.traein.orm.StationData;

import java.util.List;

public class StationDataShortAdapter extends BaseAdapter {

    private final List<StationData> mList;
    private final Context mContext;

    public StationDataShortAdapter(List<StationData> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.station_data_short_list_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final StationData data = mList.get(position);

        holder.iconLeft.setImageResource(data.getIconResource());
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getTimeDescription());
        holder.due.setText(data.dueIn + " min");

        return convertView;
    }

    private static class Holder {

        public ImageView iconLeft;
        public TextView due;
        public TextView title;
        public TextView description;

        Holder(View view) {
            iconLeft = (ImageView) view.findViewById(R.id.station_data_short_list_item_icon_left);
            due = (TextView) view.findViewById(R.id.station_data_short_list_item_due);
            title = (TextView) view.findViewById(R.id.station_data_short_list_item_title);
            description = (TextView) view.findViewById(R.id.station_data_short_list_item_description);
        }

    }

}
