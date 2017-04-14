package com.trimit.android.signup;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;

public class SignupPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void setupFields() {
        etField1.setHint(getString(R.string.et_password));
        etField1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etField1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etField1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    etField1.setSelected(false);
                    etField2.setSelected(true);
                    handled = true;
                }
                return handled;
            }
        });

        etField2.setHint(getString(R.string.et_password_confirm));
        etField2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
    public boolean checkFieldsCorrect(){
        String textField1=null;
        String textField2=null;
        textField1 = etField1.getText().toString();
        if(textField1.isEmpty()) {
            etField1.setError(getString(R.string.error_field_empty));
            return false;
        }
        textField2= etField2.getText().toString();
        if(textField2.isEmpty()) {
            etField2.setError(getString(R.string.error_field_empty));
            return false;
        }else if(!TextUtils.equals(textField1, textField2)){
            etField2.setError(getString(R.string.error_passwords_not_match));
            return false;
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_LAST_NAME, textField2).apply();
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupBirthdayActivity");
        startActivity(new Intent(this, SignupBirthdayActivity.class));
    }

    @Override
    public void setBg() {
        content.setBackground(getResources().getDrawable(R.drawable.bg_signup_password));
    }
}
