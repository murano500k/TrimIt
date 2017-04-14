package com.trimit.android.signup;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;

public class SignupGenderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupFields() {
        etField1.setVisibility(View.GONE);
        etField2.setVisibility(View.GONE);
        textAutoComplete.setVisibility(View.VISIBLE);
        textAutoComplete.setHint(getString(R.string.et_gender));
        String[] genders = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genders);
        textAutoComplete.setAdapter(adapter);
    }

    @Override
    public void setBg() {
        content.setBackground(getResources().getDrawable(R.drawable.bg_signup_gender));
    }

    @Override
    public boolean checkFieldsCorrect() {
        String textGender = etField2.getText().toString();
        if(textGender.isEmpty()) {
            etField2.setError(getString(R.string.error_field_empty));
            return false;
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_GENDER, textGender).apply();
        }
        return true;
    }


    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupBarberActivity");
        startActivity(new Intent(this, SignupBarberActivity.class));
    }
}
