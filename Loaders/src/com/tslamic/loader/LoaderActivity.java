package com.tslamic.loader;

import android.support.v4.app.FragmentActivity;
import android.view.View;

public class LoaderActivity extends FragmentActivity {

    public void onAddContent(final View view) {
        new AddTask(this).execute();
    }

    public void onDeleteContent(final View view) {
        getContentResolver().delete(DummyContentProvider.URI_ITEM, null, null);
    }

}
