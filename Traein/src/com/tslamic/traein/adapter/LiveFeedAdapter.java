package com.tslamic.traein.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tslamic.traein.R;
import com.tslamic.traein.orm.TrainPositions;

import java.util.List;


public class LiveFeedAdapter extends BaseAdapter {

    private static final int ORANGE_BACKGROUND = Color.parseColor("#FF8800");
    private final List<TrainPositions> mList;
    private final Context mContext;

    public LiveFeedAdapter(List<TrainPositions> list, Context context) {
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
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.live_feed_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final String message = mList.get(position).publicMessage;
        final String[] feed = message.split("\\\\n");

        holder.title.setText(feed[0] + " " + feed[1]);
        holder.description.setText(feed[2]);

        if ((position & 1) == 0) {
            holder.background.setBackgroundColor(ORANGE_BACKGROUND);
        } else {
            holder.background.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    private static class Holder {

        public final View background;
        public final TextView title;
        public final TextView description;

        Holder(View view) {
            background = view.findViewById(R.id.live_feed_item_background);
            title = (TextView) view.findViewById(R.id.live_feed_item_title);
            description = (TextView) view.findViewById(R.id.live_feed_item_description);
        }

    }

}
