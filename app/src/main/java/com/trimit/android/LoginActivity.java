package com.trimit.android;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.model.Responce;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.InputUtils;
import com.trimit.android.utils.PrefsUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ImageView btnLogin, btnBack;
    EditText etEmail, etPassword;
    TextView textForgotPwd;
    RetroUtils retroUtils;
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
        String savedEmail= PrefsUtils.getStringValue(this, PrefsUtils.PREFS_KEY_EMAIL);
        if(savedEmail!=null && InputUtils.isValidEmail(savedEmail)) etEmail.setText(savedEmail);
        retroUtils=new RetroUtils(this);
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
        textForgotPwd.setPaintFlags(textForgotPwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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

    private void actionLogin(String email, String password) {
        hideSoftKeyboard();
        retroUtils.loginObservable(email,password).subscribe(responce -> {
            Log.d(TAG, "actionLogin: "+responce.getSuccess());
            Toast.makeText(LoginActivity.this,
                    responce.getSuccess(),
                    Toast.LENGTH_SHORT).show();
        }, throwable ->{
            throwable.printStackTrace();
            Toast.makeText(LoginActivity.this, "error: " + throwable, Toast.LENGTH_SHORT).show();
        });
    }

    private void actionForgotPassword(String email) {
        hideSoftKeyboard();
        retroUtils.checkEmailObservable(email).zipWith(retroUtils.forgotPasswordObservable(email), new BiFunction<EmailExistsResponce, Responce, String>() {
            @Override
            public String apply(@NonNull EmailExistsResponce emailExistsResponce, @NonNull Responce responce) throws Exception {
                String result;
                if(!emailExistsResponce.getEmailExists() || !TextUtils.equals(responce.getSuccess(),"true")) result=getString(R.string.error);
                else result=getString(R.string.reset_password_success)+email;
                Log.d(TAG, "forgot result "+result);
                return result;
            }
        }).subscribe(s -> {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "actionForgotPassword: "+s);
                },
                throwable -> {
                        throwable.printStackTrace();
                        Toast.makeText(this, "error: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    private void showError(){
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
