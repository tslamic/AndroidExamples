package com.tslamic.loader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.tslamic.loader.cursor.CursorLoaderActivity;
import com.tslamic.loader.json.JsonLoaderActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onJson(final View view) {
        final Intent intent = new Intent(this, JsonLoaderActivity.class);
        startActivity(intent);
    }

    public void onCursor(final View view) {
        final Intent intent = new Intent(this, CursorLoaderActivity.class);
        startActivity(intent);
    }

}
