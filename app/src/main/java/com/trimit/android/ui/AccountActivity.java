package com.trimit.android.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.trimit.android.App;
import com.trimit.android.R;
import com.trimit.android.utils.net.RetroUtils;

import javax.inject.Inject;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    TextView textResult;
    FloatingActionButton fab;


    @Inject
    public RetroUtils mRetroUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplicationContext()).getNetComponent().inject(this);
        setContentView(R.layout.activity_account);
        textResult=(TextView) findViewById(R.id.text_result);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> getUserData());
        if(getIntent()!=null){
            String loginResult = getIntent().getStringExtra(EXTRA_RESULT);
            if(loginResult!=null)textResult.setText(loginResult);
        }
    }

    private void getUserData() {
        mRetroUtils.getUserObservable().subscribe(
                o -> Log.d(TAG, "accept: " + o.toString()),
                Throwable::printStackTrace);
    }
}
