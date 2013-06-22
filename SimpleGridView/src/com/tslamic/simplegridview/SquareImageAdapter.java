package com.tslamic.simplegridview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SquareImageAdapter extends BaseAdapter {

    private static final String[] COLOR_KEY = {
            "Color.BLACK",
            "Color.BLUE",
            "Color.CYAN",
            "Color.DKGRAY",
            "Color.GRAY",
            "Color.GREEN",
            "Color.LTGRAY",
            "Color.MAGENTA",
            "Color.RED",
            "Color.TRANSPARENT",
            "Color.WHITE",
            "Color.YELLOW",
    };

    private static final int[] COLOR_VALUE = {
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.TRANSPARENT,
            Color.WHITE,
            Color.YELLOW,
    };

    private final Context mContext;

    public SquareImageAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return COLOR_KEY.length;
    }

    @Override
    public Object getItem(int position) {
        return COLOR_KEY[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.image.setBackgroundColor(COLOR_VALUE[position]);
        holder.text.setText(COLOR_KEY[position]);

        return view;
    }

    private static class Holder {
        public final SquareImageView image;
        public final TextView text;

        public Holder(final View view) {
            image = (SquareImageView) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.text);
        }
    }

}
