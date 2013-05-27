package com.tslamic.httpbinvolley.param;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import com.tslamic.httpbinvolley.R;

import java.util.ArrayList;
import java.util.List;

public class ParamAdapter extends BaseAdapter {

    private final List<Param> mData;
    private final Context mContext;

    public ParamAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<Param>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return getParam(position);
    }

    Param getParam(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ParamHolder holder;

        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.param_list_item, null);
            holder = new ParamHolder(view);
            view.setTag(holder);
        } else {
            holder = (ParamHolder) view.getTag();
        }

        final Param param = getParam(position);

        holder.currentPosition = position;
        holder.key.setText(param.key);
        holder.value.setText(param.value);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        System.out.println(mData.toString());
    }

    public void addEmpty() {
        mData.add(new Param());
    }

    public List<Param> getAll() {
        return mData;
    }

    private class ParamHolder {

        public final EditText key;
        public final EditText value;
        public final ImageButton delete;
        public int currentPosition;

        public ParamHolder(View view) {
            key = (EditText) view.findViewById(R.id.param_key);
            key.addTextChangedListener(new AbstractParamWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    mData.get(currentPosition).key = charSequence.toString();
                    notifyDataSetChanged();
                }
            });

            value = (EditText) view.findViewById(R.id.param_value);
            value.addTextChangedListener(new AbstractParamWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    mData.get(currentPosition).value = charSequence.toString();
                    notifyDataSetChanged();
                }
            });

            delete = (ImageButton) view.findViewById(R.id.param_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mData.remove(currentPosition);
                    notifyDataSetChanged();
                }
            });
        }

    }

    private static abstract class AbstractParamWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // Do nothing.
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Do nothing.
        }

    }

}
