package com.trimit.android.signup;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.trimit.android.R;
import com.trimit.android.SignupBaseActivity;
import com.trimit.android.utils.InputUtils;
import com.trimit.android.utils.PrefsUtils;

public class SignupEmailActivity extends SignupBaseActivity {


    @Override
    public void setupFields() {
        setQuestion(getString(R.string.text_signup_email));
        etField2.setVisibility(View.VISIBLE);
        etField2.setHint(getString(R.string.et_email));
        etField2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etField2.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etField2.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideSoftKeyboard();
                onClick(btnNext);
                handled = true;
            }
            return handled;
        });
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
            mRetroUtils.checkEmailObservable(textEmail).subscribe(emailExistsResponce -> {
                if (emailExistsResponce.getEmailExists()) {
                    etField2.setError(getString(R.string.error_email_in_use));
                } else {
                    mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_EMAIL, textEmail);
                    nextActivity();
                }
            }, throwable -> {
                throwable.printStackTrace();
                etField2.setError(getString(R.string.error_field_not_valid));
            });
        }
        return false;
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
