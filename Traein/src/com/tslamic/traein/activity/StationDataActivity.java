package com.tslamic.traein.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import com.tslamic.traein.adapter.StationDataAdapter;
import com.tslamic.traein.orm.StationData;
import com.tslamic.traein.util.Util;

public class StationDataActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final StationData data = getIntent().getParcelableExtra(Util.KEY_DATA);

        getSupportActionBar().setTitle(data.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listView = new ListView(this);
        listView.setAdapter(new StationDataAdapter(data.toMap(), this));

        setContentView(listView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
