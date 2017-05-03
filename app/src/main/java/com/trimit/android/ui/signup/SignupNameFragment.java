package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.trimit.android.R;
import com.trimit.android.utils.CustomEditText;
import com.trimit.android.utils.PrefsUtils;

/**
 * Created by artem on 4/28/17.
 */

public class SignupNameFragment extends SignupBaseFragment {
    public static final String TAG = "SignupNameFragment";

    CustomEditText mEtFirstName;
    CustomEditText mEtLastName;

    public SignupNameFragment() {

    }

    public static SignupNameFragment newInstance() {
        return new SignupNameFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_name, container, false);
        mListener.setQuestion(getString(R.string.text_signup_name));
        mEtFirstName=(CustomEditText) view.findViewById(R.id.et_firstname);
        mEtLastName=(CustomEditText) view.findViewById(R.id.et_lastname);

        mEtFirstName.setHint(getString(R.string.et_first_name));
        mEtFirstName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        mEtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEtFirstName.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if(checkFieldNotEmpty(mEtFirstName)) mEtLastName.requestFocus();
                handled = true;
            }
            return handled;
        });

        mEtLastName.setHint(getString(R.string.et_last_name));
        mEtLastName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        mEtLastName.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEtLastName.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(mFieldsValidator.checkFieldsValid(PrefsUtils.PREFS_KEY_FIRST_NAME, mEtFirstName,mEtLastName)){
                    mDataProvider.getPrefs()
                            .setStringValue(PrefsUtils.PREFS_KEY_FIRST_NAME, mEtFirstName.getText().toString());
                    mDataProvider.getPrefs()
                            .setStringValue(PrefsUtils.PREFS_KEY_LAST_NAME, mEtLastName.getText().toString());
                    mListener.onNextPressed(PrefsUtils.PREFS_KEY_FIRST_NAME);
                }
                handled = true;
            }
            return handled;
        });
        setUpNextButton(view);
        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldsValid(PrefsUtils.PREFS_KEY_FIRST_NAME, mEtFirstName,mEtLastName)){
                mDataProvider.getPrefs()
                        .setStringValue(PrefsUtils.PREFS_KEY_FIRST_NAME, mEtFirstName.getText().toString());
                mDataProvider.getPrefs()
                        .setStringValue(PrefsUtils.PREFS_KEY_LAST_NAME, mEtLastName.getText().toString());
                mListener.onNextPressed(PrefsUtils.PREFS_KEY_FIRST_NAME);
            }
        });
        return view;
    }

}
