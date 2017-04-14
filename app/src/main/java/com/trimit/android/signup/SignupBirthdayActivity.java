package com.trimit.android.signup;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;

public class SignupBirthdayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupFields() {
        etField1.setVisibility(View.INVISIBLE);

        etField2.setHint(getString(R.string.et_birthday));
        etField2.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
    }

    @Override
    public void setBg() {
        content.setBackground(getResources().getDrawable(R.drawable.bg_signup_birthday));
    }

    @Override
    public boolean checkFieldsCorrect() {
        String textBirthday = etField2.getText().toString();
        if(textBirthday.isEmpty()) {
            etField2.setError(getString(R.string.error_field_empty));
            return false;
        } else if(false){
            // TODO: 4/18/17 date validation and convert to timestamp
            return false;
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_BIRTHDAY, textBirthday).apply();
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupGenderActivity");
        startActivity(new Intent(this, SignupGenderActivity.class));
    }
}
