package com.trimit.android.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.trimit.android.R;
import com.trimit.android.ui.barber.BarberDetailsFragment;
import com.trimit.android.ui.barber.BarberListFragment;
import com.trimit.android.ui.barber.BarberMapFragment;
import com.trimit.android.ui.barber.BarberReviewsFragment;
import com.trimit.android.ui.home.HomeFragment;
import com.trimit.android.ui.other.TosFragment;
import com.trimit.android.ui.profile.ProfileFragment;
import com.trimit.android.ui.search.SearchFragment;
import com.trimit.android.utils.UriUtils;

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
    }


    @Override
    protected boolean openTab(int id) {
        mMenuPageId = id;
        switch (id) {
            case R.id.navigation_home:
                Log.d(TAG, "show home");
                onFragmentInteraction(UriUtils.getUri(HomeFragment.TAG));
                return true;
            case R.id.navigation_search:
                Log.d(TAG, "show search");
                onFragmentInteraction(UriUtils.getUri(SearchFragment.TAG));
                return true;
            case R.id.navigation_profile:
                Log.d(TAG, "show profile");
                onFragmentInteraction(UriUtils.getUri(ProfileFragment.TAG));
                return true;
            case R.id.navigation_appointment:
                Log.d(TAG, "showAppointment");
                Toast.makeText(this, "showAppointment not implemented", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.navigation_loyalty:
                Log.d(TAG, "showLoyalty");
                Toast.makeText(this, "showLoyalty not implemented", Toast.LENGTH_SHORT).show();
                return false;
        }
        return false;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        String screen = uri.getLastPathSegment();
        String param = uri.getFragment();
        Fragment fragment;
        String tag;
        if(TextUtils.equals(screen, TosFragment.TAG)){

            fragment=TosFragment.newInstance();
            tag=TosFragment.TAG;

            setBottomBarSelectedItem(R.id.navigation_home);

        }else if(TextUtils.equals(screen, HomeFragment.TAG)){

            fragment=HomeFragment.newInstance();
            tag=HomeFragment.TAG;

            setBottomBarSelectedItem(R.id.navigation_home);

        }else if(TextUtils.equals(screen, ProfileFragment.TAG)){

            fragment=ProfileFragment.newInstance();
            tag=ProfileFragment.TAG;

            setBottomBarSelectedItem(R.id.navigation_profile);

        }else if(TextUtils.equals(screen, SearchFragment.TAG)){

            fragment=SearchFragment.newInstance();
            tag=SearchFragment.TAG;

            setBottomBarSelectedItem(R.id.navigation_search);

        }else if(TextUtils.equals(screen, BarberListFragment.TAG)){

            fragment=BarberListFragment.newInstance();
            tag=BarberListFragment.TAG;

        }else if(TextUtils.equals(screen, BarberDetailsFragment.TAG)){
            if(param==null) throw new NullPointerException("param is required for "+screen);
            int id = Integer.valueOf(param);

            fragment=BarberDetailsFragment.newInstance(id);
            tag=BarberDetailsFragment.TAG;

        }else if(TextUtils.equals(screen, BarberMapFragment.TAG)){
            if(param==null) throw new NullPointerException("param is required for "+screen);
            int id = Integer.valueOf(param);

            fragment=BarberMapFragment.newInstance(id);
            tag=BarberMapFragment.TAG;

        }else {
            String msg = screen + getString(R.string.not_implemented);
            Log.w(TAG, msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "onFragmentInteraction: "+tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment);

        String barberString = getString(R.string.title_barber);
        if(tag.contains(barberString) || tag.contains(TosFragment.TAG)) {
            Log.d(TAG, "onFragmentInteraction: add to stack");
            transaction.addToBackStack(tag);
        }
        if(tag.contains(barberString)){
            setBottomBarStyle(3);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count>0){
            String tag = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            if(
                    TextUtils.equals(tag, HomeFragment.TAG)
                    ){
                setBottomBarStyle(1);
            } else if(
                    TextUtils.equals(tag, ProfileFragment.TAG)
                    ){
                setBottomBarStyle(3);
            } else if(
                    TextUtils.equals(tag, SearchFragment.TAG)
                    ){
                setBottomBarStyle(2);
            } else if(
                            TextUtils.equals(tag, BarberDetailsFragment.TAG) ||
                            TextUtils.equals(tag, BarberListFragment.TAG) ||
                            TextUtils.equals(tag, BarberMapFragment.TAG) ||
                            TextUtils.equals(tag, BarberReviewsFragment.TAG)
                    ){
                setBottomBarStyle(3);
            } else {
                Log.e(TAG, "onBackPressed: TAG no match"+tag);
                super.onBackPressed();
                return;
            }
            Log.d(TAG, "popBackStack: "+tag);
            getSupportFragmentManager().popBackStack();
        } else super.onBackPressed();
    }

    private void setBottomBarSelectedItem(int itemId) {
        Log.d(TAG, "setBottomBarSelectedItem: "+itemId);
        switch (itemId) {
            case R.id.navigation_home:
                setBottomBarStyle(1);
                break;
            case R.id.navigation_search:
                setBottomBarStyle(2);
                break;
            case R.id.navigation_profile:
                setBottomBarStyle(3);
                break;
            default:
                Log.e(TAG, "setBottomBarSelectedItem: " +itemId +" no such item");
                return;
        }
        mMenuPageId=itemId;
        mNavigationView.setSelectedItemId(mMenuPageId);
    }
}
