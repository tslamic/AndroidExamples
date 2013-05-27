package com.tslamic.httpbinvolley;

import android.app.Application;

public class WebApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Web.init(getApplicationContext());
    }

}
