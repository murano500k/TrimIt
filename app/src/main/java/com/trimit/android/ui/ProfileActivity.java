package com.trimit.android.ui;

import android.view.View;

import com.trimit.android.R;

public class ProfileActivity extends MainActivity {
    private static final String TAG = "ProfileActivity";
    public View container;
    public ProfileActivity() {
        mMenuPageId=R.id.navigation_profile;
    }

    @Override
    public void showContent() {
        mNavigationView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
        getLayoutInflater().inflate(R.layout.content_profile, mContent);
    }
}
