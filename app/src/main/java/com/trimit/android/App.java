package com.trimit.android;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by artem on 4/14/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gotham-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
