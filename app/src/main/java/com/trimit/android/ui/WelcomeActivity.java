package com.trimit.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trimit.android.App;
import com.trimit.android.R;
import com.trimit.android.ui.signup.SignupNameActivity;
import com.trimit.android.utils.PrefsUtils;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LinearLayout mDotsLayout;
    private ViewPager mViewPager;
    private int[] mLayouts;
    private TextView[] mDots;
    private MyViewPagerAdapter mViewPagerAdapter;

    @Inject
    PrefsUtils mPrefsUtils;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplicationContext()).getNetComponent().inject(this);
        setContentView(R.layout.activity_welcome);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mDotsLayout = (LinearLayout) findViewById(R.id.layout_dots);
        mLayouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4
        };

        // adding bottom mDots
        addBottomDots(0);

        mViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        checkUserLoggedIn();
    }

    private void checkUserLoggedIn(){
        String userId = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_USER_ID);
        Log.d(TAG, "checkUserLoggedIn: userId="+userId);
        if(userId!=null && !userId.isEmpty()){
            Log.d(TAG, "checkUserLoggedIn: user logged in");
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }else {
            Log.w(TAG, "checkUserLoggedIn: user NOT logged in" );
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(WelcomeActivity.this, SignupNameActivity.class));
                break;
        }
    }
    private void addBottomDots(int currentPage) {
        mDots = new TextView[mLayouts.length];

        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(".");
            mDots[i].setTextSize(64);
            mDots[i].setTextColor(getResources().getColor(R.color.colorInactive));
            mDots[i].setAlpha(0.6f);
            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0)
            mDots[currentPage].setTextColor(getResources().getColor(R.color.colorActive));
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(mLayouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}
