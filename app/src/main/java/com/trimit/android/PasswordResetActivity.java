package com.trimit.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.InputUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PasswordResetActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ImageView btnReset, btnBack;
    com.trimit.android.utils.CustomEditText etEmail;
    private ProgressBar progressBar;

    @Inject
    RetroUtils mRetroUtils;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication()).getNetComponent().inject(this);
        setContentView(R.layout.activity_password_reset);
        btnBack=(ImageView)findViewById(R.id.btn_back);
        btnReset =(ImageView)findViewById(R.id.btn_reset);
        etEmail=(com.trimit.android.utils.CustomEditText)findViewById(R.id.et_email);
        progressBar=(ProgressBar) findViewById(R.id.progress);
        setup();
    }

    public void setup(){
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etEmail.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etEmail.setOnEditorActionListener((v, actionId, event) -> {
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
        RxView.clicks(btnReset)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> onClick());

    }

    private boolean checkFieldsCorrect(){

        String textEmail;
        textEmail = etEmail.getText().toString();
        if(textEmail.isEmpty()) {
            etEmail.setError(getString(R.string.error_field_empty));
            return false;
        }else if(!InputUtils.isValidEmail(textEmail)){
            etEmail.setError(getString(R.string.error_field_not_valid));
            return false;
        }
        return true;
    }



    public void onClick(){
        Log.d(TAG, "onClick actionForgotPassword");
        if(checkFieldsCorrect()) actionForgotPassword(etEmail.getText().toString());
    }

    private void actionForgotPassword(String email) {
        hideSoftKeyboard();
        progressBar.setVisibility(View.VISIBLE);
        mRetroUtils.checkEmailObservable(email).zipWith(mRetroUtils.forgotPasswordObservable(email), (emailExistsResponce, responce) -> {
            String result;
            if(!emailExistsResponce.getEmailExists() || !TextUtils.equals(responce.getSuccess(),"true")) result=getString(R.string.error);
            else result=getString(R.string.reset_password_success)+email;
            Log.d(TAG, "forgot result "+result);
            return result;
        }).subscribe(s -> {
                    Toast.makeText(PasswordResetActivity.this, s, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    Log.d(TAG, "actionForgotPassword: "+s);
                },
                throwable -> {
                        throwable.printStackTrace();
                        Toast.makeText(this, "error: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                });
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
