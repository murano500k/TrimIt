package com.trimit.android;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.trimit.android.dagger.AppModule;
import com.trimit.android.dagger.DaggerNetComponent;
import com.trimit.android.dagger.NetComponent;
import com.trimit.android.dagger.NetModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    private NetComponent mNetComponent;

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
    @Override
    public void onCreate() {
        super.onCreate();


        AndroidNetworking.initialize(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gotham-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(getString(R.string.base_url)))
                .build();
    }
}
