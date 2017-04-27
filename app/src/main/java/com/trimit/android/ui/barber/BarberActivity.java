package com.trimit.android.ui.barber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.R;
import com.trimit.android.ui.BaseBottomBarActivity;
import com.trimit.android.ui.BottomBarActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class BarberActivity extends BaseBottomBarActivity {
    private static final String TAG = "BarberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);
        initLayout();
        setContent(savedInstanceState);
    }

    @Override
    protected void setContent(Bundle savedInstanceState) {
        RxView.clicks(findViewById(R.id.btn_back))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "back Clicked");
                    finish();
                });
        showBarberList();
        mNavigationView.setSelectedItemId(mMenuPageId);
    }


    @Override
    protected int getMenuPageId() {
        return R.id.navigation_search;
    }
    protected void showBarberList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, BarberListFragment.newInstance())
                .commit();
        setBottomBarStyle(3);
    }

    @Override
    protected boolean openTab(int id) {
        Intent intent=new Intent(this, BottomBarActivity.class);
        intent.putExtra(EXTRA_PAGE_ID, id);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onFragmentInteraction(int barberId, String screenName) {
        Log.d(TAG, "onFragmentInteraction: barberId="+barberId+" screen="+screenName);
        if(TextUtils.equals(screenName, BarberDetailsFragment.class.getName())){
            getSupportFragmentManager().beginTransaction().replace(R.id.content, BarberDetailsFragment.newInstance(barberId)).commit();
        }else if(TextUtils.equals(screenName, BarberMapFragment.class.getName())){
            getSupportFragmentManager().beginTransaction().replace(R.id.content, BarberMapFragment.newInstance(barberId)).commit();
        }else {
            Log.e(TAG, "onFragmentInteraction: error "+screenName );
        }
    }
}
