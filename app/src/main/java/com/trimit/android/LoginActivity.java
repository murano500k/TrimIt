package com.trimit.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.utils.InputUtils;

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
        setup();
    }

    public void setup(){
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etEmail.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                if(InputUtils.isValidEmail(etEmail.getText().toString())) etPassword.requestFocus();
                else etEmail.setError(getString(R.string.error_field_not_valid));

                handled = true;
            }
            return handled;
        });

        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                onClick();
                handled = true;
            }
            return handled;
        });

        RxView.clicks(btnBack)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Log.d(TAG, "backClicked");
                    finish();
                });
        RxView.clicks(btnLogin)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> onClick());
        textForgotPwd.setEnabled(true);
        RxView.clicks(textForgotPwd)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "forgotClicked");
                    boolean emailValid= InputUtils.isValidEmail(etEmail.getText().toString());
                    if (!emailValid) {
                        etEmail.setError("Invalid Email Address");
                    }else actionForgotPassword(etEmail.getText().toString());
                });
    }

    private boolean checkFieldsCorrect(){
        String textEmail=null;
        String textPassword=null;
        textEmail = etEmail.getText().toString();
        if(textEmail.isEmpty()) {
            etEmail.setError(getString(R.string.error_field_empty));
            return false;
        }else if(!InputUtils.isValidEmail(textEmail)){
            etEmail.setError(getString(R.string.error_field_not_valid));
            return false;
        }
        textPassword=etPassword.getText().toString();
        if(textPassword.isEmpty()) {
            etPassword.setError(getString(R.string.error_field_empty));
            return false;
        }
        return true;
    }



    public void onClick(){
        Log.d(TAG, "loginClicked");
        if(checkFieldsCorrect()) actionLogin(etEmail.getText().toString(), etPassword.getText().toString());
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
}
