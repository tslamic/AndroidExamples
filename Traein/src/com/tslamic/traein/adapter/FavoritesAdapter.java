package com.tslamic.traein.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tslamic.traein.R;
import com.tslamic.traein.util.Util;

import java.util.List;

public class FavoritesAdapter extends BaseAdapter {

    private final FavoriteListener mListener;
    private final List<String> mList;
    private final Context mContext;

    public FavoritesAdapter(List<String> list, FavoriteListener listener, Context context) {
        mList = list;
        mListener = listener;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return getFavorite(position);
    }

    public String getFavorite(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.favorite_list_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final String station = mList.get(position);

        holder.station.setText(station);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) mListener.onRemove(station, position);
            }
        });

        return convertView;
    }

    public boolean contains(String station) {
        return mList.contains(station);
    }

    public void add(String station) {
        mList.add(station);
        notifyDataSetChanged();
        updateFavorites();
    }

    public void remove(int position) {
        mList.remove(position);
        notifyDataSetChanged();
        updateFavorites();
    }

    private void updateFavorites() {
        Util.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                Util.setFavorites(mContext, mList);
            }
        });
    }

    public static interface FavoriteListener {
        void onRemove(String station, int position);
    }

    private static class Holder {

        public final TextView station;
        public final ImageView delete;

        Holder(View view) {
            station = (TextView) view.findViewById(R.id.favorite_list_item_station);
            delete = (ImageView) view.findViewById(R.id.favorite_list_item_delete);
        }

    }

}
