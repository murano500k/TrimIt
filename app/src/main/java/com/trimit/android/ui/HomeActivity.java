package com.trimit.android.ui;

import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by artem on 4/24/17.
 */

public class HomeActivity extends MainActivity {
    private static final String TAG = "HomeActivity";

    public HomeActivity() {
        mMenuPageId=R.id.navigation_home;
    }

    @Override
    public void showContent() {
        mNavigationView.setBackgroundColor(getResources().getColor(R.color.colorBgBottomBar));
        getLayoutInflater().inflate(R.layout.content_home, mContent);
        RxView.clicks(findViewById(R.id.img_nearby))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "nearby Clicked");
                    Toast.makeText(this, "nearby not implemented", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
                });
        RxView.clicks(findViewById(R.id.img_trims))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "trims Clicked");
                    Toast.makeText(this, "trims not implemented", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
                });
    }
}
