package com.tslamic.simpleanimations;

import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DevicesActivity extends ListActivity {

    private static final Random RANDOM = new Random();

    private static final String[] DEVICES = {
            "HTC One",
            "Samsung Galaxy S4",
            "LG Electronics Optimus G Pro",
            "Samsung Galaxy Note II",
            "Motorola Moto X",
            "Sony Xperia Z",
            "Motorola DROID RAZR HD",
            "HTC Droid DNA",
            "Google Nexus 4",
            "LG Electronics Optimus G",
            "Google Nexus 7",
            "Asus Transformer Pad Infinity",
            "Samsung Galaxy Note",
            "Sony Xperia Tablet Z",
            "LG G2"
    };

    private int mSelectedRow = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ArrayList<String> list = new ArrayList<String>(Arrays.asList(DEVICES));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        final Animation animation;
        if (Build.VERSION.SDK_INT > 10) {
            animation = AnimationUtils.loadAnimation(this, R.anim.custom);
        } else {
            animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        }

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                adapter.remove(adapter.getItem(mSelectedRow));
                adapter.add(DEVICES[RANDOM.nextInt(DEVICES.length)]);
                adapter.notifyDataSetChanged();
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(animation);
                mSelectedRow = position;
            }

        });
    }

}
