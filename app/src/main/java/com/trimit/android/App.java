package com.trimit.android;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by artem on 4/14/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());

    }
}
