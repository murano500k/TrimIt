package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;


public class SignupGenderFragment extends SignupBaseFragment {
    public static final String TAG = "SignupGenderFragment";

    AutoCompleteTextView mEtGender;

    public SignupGenderFragment() {

    }

    public static SignupGenderFragment newInstance() {
        return new SignupGenderFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_gender, container, false);
        setUpNextButton(view);

        mListener.setQuestion(getString(R.string.text_signup_gender));



        mEtGender=(AutoCompleteTextView) view.findViewById(R.id.et_gender);
        mEtGender.setHint(getString(R.string.et_gender));


        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_GENDER, mEtGender)){
                mDataProvider.getPrefs()
                        .setStringValue(PrefsUtils.PREFS_KEY_GENDER, mEtGender.getText().toString());
                mListener.onNextPressed(PrefsUtils.PREFS_KEY_GENDER);
            }
        });

        String[] genders=getResources().getStringArray(R.array.gender_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),R.layout.auto_complete_list_item, genders);

        mEtGender.setThreshold(1);
        mEtGender.setAdapter(adapter);

        mEtGender.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mEtGender.showDropDown();
                return true;
            }
        });
        mEtGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_GENDER, mEtGender)){
                    mDataProvider.getPrefs()
                            .setStringValue(PrefsUtils.PREFS_KEY_GENDER, mEtGender.getText().toString());
                    mListener.onNextPressed(PrefsUtils.PREFS_KEY_GENDER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


}
