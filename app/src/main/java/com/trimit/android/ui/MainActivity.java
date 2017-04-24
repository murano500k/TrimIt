package com.trimit.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.trimit.android.R;
import com.trimit.android.ui.search.SearchActivity;

public abstract class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    protected int mMenuPageId;

    protected FrameLayout mContent;

    protected BottomNavigationView mNavigationView;
    protected View mContainer;


    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==mMenuPageId){
                Log.d(TAG, "onNavigationItemSelected: already on tab "+mMenuPageId);
                return true;
            }
            return openTab(item.getItemId());
        }
    };

    protected boolean openTab(int id){
        if(id==mMenuPageId){
            Log.d(TAG, "onNavigationItemSelected: already on tab "+mMenuPageId);
            return true;
        }
        mMenuPageId=id;
        switch (id) {
            case R.id.navigation_home:
                showHome();
                return true;
            case R.id.navigation_search:
                showSearch();
                return true;
            case R.id.navigation_profile:
                showProfile();
                return true;
            case R.id.navigation_appointment:
                showAppointment();
                return true;
            case R.id.navigation_loyalty:
                showLoyalty();
                return true;
        }
        return false;
    }

    protected void showSearch() {
        startActivity(new Intent(this,SearchActivity.class));
        finish();
    }
    protected void showProfile() {
        startActivity(new Intent(this,ProfileActivity.class));
        finish();
    }
    protected void showAppointment() {
        Log.d(TAG, "showAppointment");
        Toast.makeText(this, "showAppointment not implemented", Toast.LENGTH_SHORT).show();
    }
    protected void showLoyalty() {
        Log.d(TAG, "showLoyalty");
        Toast.makeText(this, "showLoyalty not implemented", Toast.LENGTH_SHORT).show();
    }

    protected void showHome(){
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = (FrameLayout) findViewById(R.id.content);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(mMenuPageId);
        showContent();
    }

    public abstract void showContent();


}
