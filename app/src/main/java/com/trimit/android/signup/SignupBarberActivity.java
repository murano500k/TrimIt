package com.trimit.android.signup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.trimit.android.LoginActivity;
import com.trimit.android.R;
import com.trimit.android.model.ResponceUserCreate;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PrefsUtils.PREFS_KEY_BARBER_TYPE, textBarber).apply();
        }
        return true;
    }

    @Override
    public void nextActivity() {
        Toast.makeText(this, "registering...", Toast.LENGTH_SHORT).show();
        RetroUtils retroUtils =new RetroUtils(this);
        retroUtils.createUserFromPrefs().subscribe(new Consumer<ResponceUserCreate>() {
            @Override
            public void accept(@NonNull ResponceUserCreate userCreateResponce) throws Exception {
                Log.d(TAG, "accept: "+userCreateResponce);
                Log.d(TAG, "userId: "+userCreateResponce.getUserCreateResponce().getUserId());
                hideSoftKeyboard();
                startActivity(new Intent(SignupBarberActivity.this,LoginActivity.class));
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "error: "+throwable.getMessage());
                throwable.printStackTrace();
                new AlertDialog.Builder(SignupBarberActivity.this)
                        .setTitle("error")
                        .setMessage(throwable.getMessage())
                        .create().show();
                hideSoftKeyboard();
            }
        });
    }
}
