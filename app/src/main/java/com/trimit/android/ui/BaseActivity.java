package com.trimit.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by artem on 4/24/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public CompositeDisposable mDisposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposables=new CompositeDisposable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisposables.dispose();
    }
}
