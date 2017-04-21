package com.trimit.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.InputUtils;
import com.trimit.android.utils.PrefsUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ImageView btnLogin, btnBack;
    com.trimit.android.utils.CustomEditText etEmail, etPassword;
    TextView textForgotPwd;
    private ProgressBar progressBar;

    @Inject
    protected RetroUtils mRetroUtils;

    @Inject
    protected PrefsUtils mPrefsUtils;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);
        setContentView(R.layout.activity_login);
        btnBack=(ImageView)findViewById(R.id.btn_back);
        btnLogin=(ImageView)findViewById(R.id.btn_login);
        etEmail=(com.trimit.android.utils.CustomEditText)findViewById(R.id.et_email);
        etPassword=(com.trimit.android.utils.CustomEditText)findViewById(R.id.et_password);
        textForgotPwd=(TextView)findViewById(R.id.text_forgot_pwd);
        progressBar=(ProgressBar) findViewById(R.id.progress);

        setup();
    }

    public void setup(){
        String savedEmail= mPrefsUtils.getStringValue( PrefsUtils.PREFS_KEY_EMAIL);
        if(savedEmail!=null && InputUtils.isValidEmail(savedEmail)) etEmail.setText(savedEmail);

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
        //textForgotPwd.setPaintFlags(textForgotPwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        RxView.clicks(textForgotPwd)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "forgotClicked");
                    startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
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

    private void actionLogin(String email, String password) {
        hideSoftKeyboard();
        progressBar.setVisibility(View.VISIBLE);
        mRetroUtils.loginObservable(email,password).subscribe(responce -> {
            Log.d(TAG, "actionLogin: "+responce.getSuccess());
            progressBar.setVisibility(View.GONE);
            Intent intent=new Intent(LoginActivity.this, AccountActivity.class);
            intent.putExtra(AccountActivity.EXTRA_RESULT,responce.toString());
            startActivity(intent);
            finish();
        }, throwable ->{
            progressBar.setVisibility(View.GONE);
            throwable.printStackTrace();
            Toast.makeText(LoginActivity.this, "error: " + throwable, Toast.LENGTH_SHORT).show();
        });
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
