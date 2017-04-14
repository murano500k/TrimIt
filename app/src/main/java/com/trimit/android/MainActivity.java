package com.trimit.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.trimit.android.net.NetworkUtils;
import com.trimit.android.signup.SignupNameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NetworkUtils(this).getUsers();
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(MainActivity.this, SignupNameActivity.class));
                break;
        }
    }
}
