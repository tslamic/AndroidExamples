package com.tslamic.navigationdrawer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends FragmentActivity implements ListView.OnItemClickListener {

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

    private ArrayAdapter<String> mDrawerAdapter;
    private FrameLayout mContent;
    private ListView mDrawerList;
    private TextView mMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mDrawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COLOR_KEY);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(this);

        mContent = (FrameLayout) findViewById(R.id.content);
        mMessage = (TextView) findViewById(R.id.message);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        final int color = COLOR_VALUE[position];
        mContent.setBackgroundColor(color);
        mMessage.setTextColor(~color | (0xFF << 24)); // Invert color of the text, so it's always visible.
    }

}
