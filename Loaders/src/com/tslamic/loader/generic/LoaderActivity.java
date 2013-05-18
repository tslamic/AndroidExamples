package com.tslamic.loader.generic;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.tslamic.loader.content.DummyContentProvider;

public class LoaderActivity extends FragmentActivity {

    public void onAddContent(final View view) {
        new AddTask().execute(this);
    }

    public void onDeleteContent(final View view) {
        getContentResolver().delete(DummyContentProvider.URI_ITEM, null, null);
    }

}
