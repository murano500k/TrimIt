package com.trimit.android.signup;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
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
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        setBg(R.drawable.bg_signup_barber);
    }

    @Override
    public void setupFields() {
        textAutoComplete.setVisibility(View.VISIBLE);
        textAutoComplete.setHint(getString(R.string.et_barber_type));
        // TODO: 4/19/17 change barber types
        String[] barbers=getResources().getStringArray(R.array.barber_type_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_singlechoice, barbers);
        textAutoComplete.setThreshold(1);
        textAutoComplete.setAdapter(adapter);
        textAutoComplete.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                textAutoComplete.showDropDown();
                return true;
            }
        });
    }



    @Override
    public boolean checkFieldsCorrect() {
        String textBarber = textAutoComplete.getText().toString();
        if(textBarber.isEmpty()) {
            textAutoComplete.setError(getString(R.string.error_field_empty));
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
