package com.trimit.android.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.trimit.android.R;
import com.trimit.android.ui.home.HomeFragment;
import com.trimit.android.ui.profile.ProfileFragment;
import com.trimit.android.ui.search.SearchFragment;

public class BottomBarActivity extends BaseBottomBarActivity implements OnFragmentInteractionListener {
    private static final String TAG = "BottomBarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        initLayout();
        setContent(savedInstanceState);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_PAGE_ID, mNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected int getMenuPageId() {
        return R.id.navigation_home;
    }

    @Override
    protected void setContent(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(EXTRA_PAGE_ID)) {
            mMenuPageId = getIntent().getIntExtra(EXTRA_PAGE_ID, mMenuPageId);
        } else {
            if (savedInstanceState != null &&
                    savedInstanceState.containsKey(EXTRA_PAGE_ID)) {
                mMenuPageId = savedInstanceState.getInt(EXTRA_PAGE_ID);
            }
        }
        openTab(mMenuPageId);
        mNavigationView.setSelectedItemId(mMenuPageId);
    }



    protected boolean openTab(int id) {
        mMenuPageId = id;
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
                return false;
            case R.id.navigation_loyalty:
                showLoyalty();
                return false;
        }
        return false;
    }

    protected void showAppointment() {
        Log.d(TAG, "showAppointment");
        Toast.makeText(this, "showAppointment not implemented", Toast.LENGTH_SHORT).show();
    }

    protected void showLoyalty() {
        Log.d(TAG, "showLoyalty");
        Toast.makeText(this, "showLoyalty not implemented", Toast.LENGTH_SHORT).show();
    }

    protected void showHome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, HomeFragment.newInstance())
                .commit();
        setBottomBarStyle(1);
    }

    protected void showSearch() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, SearchFragment.newInstance())
                .commit();
        setBottomBarStyle(2);
    }

    protected void showProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, ProfileFragment.newInstance())
                .commit();
        setBottomBarStyle(3);
    }
}
