package com.trimit.android.signup;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;

public class SignupBarberActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextButtonBg(R.drawable.btn_signup_finished);
        showTermsMessage();
    }
    @Override
    public void setupFields() {
        etField1.setVisibility(View.INVISIBLE);
        etField2.setHint(getString(R.string.et_barber_type));
    }

    @Override
    public void setBg() {
        content.setBackground(getResources().getDrawable(R.drawable.bg_signup_barber));
    }

    @Override
    public boolean checkFieldsCorrect() {
        // TODO: 4/18/17 check barber type
        String textBarber = etField2.getText().toString();
        if(textBarber.isEmpty()) {
            etField2.setError(getString(R.string.error_field_empty));
            return false;
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_BARBER_TYPE, textBarber).apply();
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Toast.makeText(this, getString(R.string.not_implemented), Toast.LENGTH_SHORT).show();
        /*
        Log.d(TAG, "nextActivity: SignupPasswordActivity");
        startActivity(new Intent(this, SignupPasswordActivity.class));*/
        // TODO: 4/18/17 finish register
    }
}
