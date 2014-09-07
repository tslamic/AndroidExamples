package com.tslamic.traein;

import android.app.Application;
import com.tslamic.traein.web.TraeinWebClient;

/** Initializes the web client. onCreate should never be called manually. */
public class TraeinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TraeinWebClient.init(getApplicationContext());
    }

}