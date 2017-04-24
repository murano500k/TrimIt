package com.trimit.android.ui.signup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.trimit.android.R;
import com.trimit.android.model.BarberType;
import com.trimit.android.ui.LoginActivity;
import com.trimit.android.ui.SignupBaseActivity;
import com.trimit.android.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.List;

public class SignupBarberActivity extends SignupBaseActivity {

    private List<BarberType> mBarberTypes;
    private List<String> barberTypeLabels;
    private ArrayAdapter adapter;

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
        barberTypeLabels = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,R.layout.auto_complete_list_item, barberTypeLabels);
        textAutoComplete.setThreshold(1);
        textAutoComplete.setAdapter(adapter);
        textAutoComplete.setOnTouchListener((v, event) -> {
            Log.d(TAG, "showDropDown:"+barberTypeLabels);
            textAutoComplete.showDropDown();
            return true;
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
        getBarberTypes();
    }



    @Override
    public boolean checkFieldsCorrect() {
        String textBarber = textAutoComplete.getText().toString();
        if(textBarber.isEmpty()) {
            textAutoComplete.setError(getString(R.string.error_field_empty));
            return false;
        } else {
            String barberTypeId = getBarberTypeId(textBarber);
            if(barberTypeId!=null)mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_BARBER_TYPE, barberTypeId);
            else {
                Log.e(TAG, "checkFieldsCorrect: error get barber type" );
                return false;
            }
        }
        return true;
    }


    private String getBarberTypeId(String textBarberType) {
        if(mBarberTypes!=null && mBarberTypes.size()>0){
            for(BarberType barberType : mBarberTypes){
                if(TextUtils.equals(barberType.getBarberTypeType(), textBarberType)) return barberType.getBarberTypeId();
            }
        }
        return null;
    }

    @Override
    public void nextActivity() {
        signUp();
    }

    private void signUp() {
        Toast.makeText(this, "registering...", Toast.LENGTH_SHORT).show();
        mDisposables.add(mRetroUtils.createUserFromPrefs().subscribe(userCreateResponce -> {
            Log.d(TAG, "accept: "+userCreateResponce);
            String userId=String.valueOf(userCreateResponce.getUser().getUserId());
            mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_USER_ID, userId);
            Log.d(TAG, "userId: "+userId);
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
        }));
    }

    private void getBarberTypes(){
        Log.d(TAG, "getBarberTypes");
        mDisposables.add(mRetroUtils.getBarberTypesObservable().subscribe(
                responceBarberType -> {
                    Log.d(TAG, "accept: " + responceBarberType);
                    Log.d(TAG, "is success: " + responceBarberType.getSuccess());
                    if (responceBarberType.getSuccess()) {
                        setBarberTypes(responceBarberType.getBarberType());

                    }
                },
                Throwable::printStackTrace
        ));

    }

    private void setBarberTypes(List<BarberType> list) {
        this.mBarberTypes=list;
        for(BarberType barberType : list){
            adapter.add(barberType.getBarberTypeType());
            Log.d(TAG, "add barber type: "+barberType.getBarberTypeType());
        }
        adapter.notifyDataSetChanged();
    }


}
