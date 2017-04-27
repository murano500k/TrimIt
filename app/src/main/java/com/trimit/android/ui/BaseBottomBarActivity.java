package com.trimit.android.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.trimit.android.App;
import com.trimit.android.R;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

import javax.inject.Inject;

public abstract class BaseBottomBarActivity extends BaseActivity implements OnFragmentInteractionListener {

    private static final String TAG = "BaseBottomBarActivity";

    public static final String EXTRA_PAGE_ID = "EXTRA_PAGE_ID";

    @Inject
    public RetroUtils mRetroUtils;
    @Inject
    public PrefsUtils mPrefsUtils;

    protected int mMenuPageId;
    protected FrameLayout mContent;
    protected BottomNavigationView mNavigationView;
    protected View mContainer;

    protected abstract int getMenuPageId();

    protected abstract void setContent(Bundle savedInstanceState);

    protected abstract boolean openTab(int id);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplicationContext()).getNetComponent().inject(this);
    }

    protected void initLayout(){
        mContent = (FrameLayout) findViewById(R.id.content);
        mContainer = findViewById(R.id.container);
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMenuPageId=getMenuPageId();
    }

    public void setBottomBarStyle(int val){
        switch (val){
            case 1:
                mNavigationView.setItemIconTintList(getResources().getColorStateList(R.color.color_bar_item_white));
                mNavigationView.setItemTextColor(getResources().getColorStateList(R.color.color_bar_item_white));
                mNavigationView.setItemBackgroundResource(R.color.colorBarBg);
                mContainer.setBackgroundResource(R.drawable.bg_gradient);
                break;
            case 2:
                mNavigationView.setItemIconTintList(getResources().getColorStateList(R.color.color_bar_item_green));
                mNavigationView.setItemTextColor(getResources().getColorStateList(R.color.color_bar_item_green));
                mNavigationView.setItemBackgroundResource(android.R.color.transparent);
                mContainer.setBackgroundResource(R.drawable.bg_search);
                break;
            case 3:
                mNavigationView.setItemIconTintList(getResources().getColorStateList(R.color.color_bar_item_green));
                mNavigationView.setItemTextColor(getResources().getColorStateList(R.color.color_bar_item_green));
                mNavigationView.setItemBackgroundResource(android.R.color.transparent);
                mContainer.setBackgroundResource(R.color.colorBarIconWhite);
        }
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
