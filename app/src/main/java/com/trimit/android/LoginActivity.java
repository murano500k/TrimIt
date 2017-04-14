package com.trimit.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ImageView btnLogin, btnBack;
    EditText etEmail, etPassword;
    TextView textForgotPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnBack=(ImageView)findViewById(R.id.btn_back);
        btnLogin=(ImageView)findViewById(R.id.btn_login);
        etEmail=(EditText)findViewById(R.id.et_email);
        etPassword=(EditText)findViewById(R.id.et_password);
        textForgotPwd=(TextView)findViewById(R.id.text_forgot_pwd);

        RxView.clicks(btnBack)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Log.d(TAG, "backClicked");
                    finish();
                });
        RxView.clicks(btnLogin)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "loginClicked");
                    boolean emailValid=isValidEmail(etEmail.getText().toString());
                    boolean pwdValid=etPassword.getText().toString().length()>0;
                    if (!emailValid) {
                        etEmail.setError("Invalid Email Address");
                    }
                    if(!pwdValid){
                        etPassword.setError("Invalid Password");
                    }
                    if(emailValid && pwdValid) actionLogin(etEmail.getText().toString(), etPassword.getText().toString());
                });
        RxView.clicks(textForgotPwd)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "forgotClicked");
                    boolean emailValid=isValidEmail(etEmail.getText().toString());
                    if (!emailValid) {
                        etEmail.setError("Invalid Email Address");
                    }
                    if(emailValid) actionForgotPassword(etEmail.getText().toString());
                });
        RxView.hovers(btnLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(motionEvent -> {
                    if(motionEvent.getAction()== MotionEvent.ACTION_DOWN) btnLogin.setImageResource(R.drawable.btn_login);
                    else if(motionEvent.getAction()==MotionEvent.ACTION_UP) btnLogin.setImageResource(R.drawable.img_login_yellow);
                });
    }

    private void actionLogin(String s, String s1) {
        Toast.makeText(this, "actionLogin", Toast.LENGTH_SHORT).show();
    }

    private void actionForgotPassword(String email) {
        Toast.makeText(this, "actionForgotPassword", Toast.LENGTH_SHORT).show();
    }
    private void showError(){
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    public final boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
