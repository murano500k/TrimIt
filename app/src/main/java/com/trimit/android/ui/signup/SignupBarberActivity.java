package com.trimit.android.ui.signup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.trimit.android.ui.LoginActivity;
import com.trimit.android.R;
import com.trimit.android.ui.SignupBaseActivity;
import com.trimit.android.utils.PrefsUtils;

public class SignupBarberActivity extends SignupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNextButtonBg(R.drawable.btn_signup_finished);
        showTermsMessage();
    }


    @Override
    public void setupFields() {
        setQuestion(getString(R.string.text_signup_barber));
        textAutoComplete.setVisibility(View.VISIBLE);
        textAutoComplete.setHint(getString(R.string.et_barber_type));
        // TODO: 4/19/17 change barber types
        String[] barbers=getResources().getStringArray(R.array.barber_type_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.auto_complete_list_item, barbers);
        textAutoComplete.setThreshold(1);
        textAutoComplete.setAdapter(adapter);
        textAutoComplete.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                textAutoComplete.showDropDown();
                return true;
            }
        });
        textAutoComplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClick(textAutoComplete);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_BARBER_TYPE, textBarber);
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Toast.makeText(this, "registering...", Toast.LENGTH_SHORT).show();
        mRetroUtils.createUserFromPrefs().subscribe(userCreateResponce -> {
            Log.d(TAG, "accept: "+userCreateResponce);
            Log.d(TAG, "userId: "+userCreateResponce.getUserCreateResponce().getUserId());
            hideSoftKeyboard();
            Intent intent=new Intent(SignupBarberActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, throwable -> {
            Log.e(TAG, "error: "+throwable.getMessage());
            throwable.printStackTrace();
            new AlertDialog.Builder(SignupBarberActivity.this)
                    .setTitle("error")
                    .setMessage(throwable.getMessage())
                    .create().show();
            hideSoftKeyboard();
        });
    }
}
