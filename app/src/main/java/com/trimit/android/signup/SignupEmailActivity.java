package com.trimit.android.signup;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.trimit.android.R;
import com.trimit.android.utils.InputUtils;
import com.trimit.android.utils.PrefsUtils;

public class SignupEmailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupFields() {
        etField2.setVisibility(View.VISIBLE);
        etField2.setHint(getString(R.string.et_email));
        etField2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etField2.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etField2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    hideSoftKeyboard();
                    onClick(btnNext);
                    handled = true;
                }
                return handled;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        setBg(R.drawable.bg_signup_email);
    }

    @Override
    public boolean checkFieldsCorrect() {
        String textEmail = etField2.getText().toString();
        if(textEmail.isEmpty()) {
            etField2.setError(getString(R.string.error_field_empty));
            return false;
        } else if(!InputUtils.isValidEmail(textEmail)){
            etField2.setError(getString(R.string.error_field_not_valid));
            return false;
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_EMAIL, textEmail).apply();
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupPasswordActivity");
        startActivity(new Intent(this, SignupPasswordActivity.class));
    }
    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
