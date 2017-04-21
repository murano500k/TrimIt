package com.trimit.android.ui.signup;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.trimit.android.R;
import com.trimit.android.ui.SignupBaseActivity;
import com.trimit.android.utils.PrefsUtils;

public class SignupGenderActivity extends SignupBaseActivity {

    @Override
    public void setupFields() {
        Log.d(TAG, "setupFields: ");
        setQuestion(getString(R.string.text_signup_gender));

        textAutoComplete.setVisibility(View.VISIBLE);
        textAutoComplete.setHint(getString(R.string.et_gender));

        String[] genders=getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.auto_complete_list_item, genders);

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
        String textGender = textAutoComplete.getText().toString();
        if(textGender.isEmpty()) {
            textAutoComplete.setError(getString(R.string.error_field_empty));
            return false;
        } else {
            mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_GENDER, textGender);
        }
        return true;
    }


    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupBarberActivity");
        startActivity(new Intent(this, SignupBarberActivity.class));
    }
}
