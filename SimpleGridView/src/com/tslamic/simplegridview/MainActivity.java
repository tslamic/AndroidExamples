package com.tslamic.simplegridview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final GridView view = (GridView) findViewById(R.id.gridView);
        view.setAdapter(new SquareImageAdapter(this));
    }

}
