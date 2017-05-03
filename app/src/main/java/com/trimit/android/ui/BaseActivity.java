package com.trimit.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trimit.android.App;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by artem on 4/24/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements DataProvider{
    public CompositeDisposable mDisposables;

    @Inject
    public RetroUtils mRetroUtils;
    @Inject
    public PrefsUtils mPrefsUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplicationContext()).getNetComponent().inject(this);
        mDisposables=new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }


    @Override
    public PrefsUtils getPrefs() {
        return mPrefsUtils;
    }

    @Override
    public RetroUtils getRetro() {
        return mRetroUtils;
    }
}
