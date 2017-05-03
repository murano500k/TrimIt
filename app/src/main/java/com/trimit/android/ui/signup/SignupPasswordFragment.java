package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
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

public class SignupPasswordFragment extends SignupBaseFragment {
    public static final String TAG = "SignupPasswordFragment";

    CustomEditText mEtPassword;
    CustomEditText mEtPasswordConfirm;

    public SignupPasswordFragment() {
    }

    public static SignupPasswordFragment newInstance() {
        return new SignupPasswordFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_password, container, false);

        mListener.setQuestion(getString(R.string.text_signup_password));

        mEtPassword =(CustomEditText) view.findViewById(R.id.et_password);
        mEtPasswordConfirm =(CustomEditText) view.findViewById(R.id.et_password_confirm);
        mEtPassword.setHint(getString(R.string.et_password));
        mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEtPassword.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if(checkFieldNotEmpty(mEtPassword)) mEtPasswordConfirm.requestFocus();
                handled = true;
            }
            return handled;
        });

        mEtPasswordConfirm.setHint(getString(R.string.et_password_confirm));
        mEtPasswordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        mEtPasswordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPasswordConfirm.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEtPasswordConfirm.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(mFieldsValidator.checkFieldsValid(PrefsUtils.PREFS_KEY_PASSWORD, mEtPassword,mEtPasswordConfirm)){
                    mDataProvider.getPrefs()
                            .setStringValue(PrefsUtils.PREFS_KEY_PASSWORD, mEtPassword.getText().toString());
                    mListener.onNextPressed(PrefsUtils.PREFS_KEY_PASSWORD);
                }
                handled = true;
            }
            return handled;
        });
        setUpNextButton(view);
        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldsValid(PrefsUtils.PREFS_KEY_PASSWORD, mEtPassword,mEtPasswordConfirm)){
                mDataProvider.getPrefs()
                        .setStringValue(PrefsUtils.PREFS_KEY_PASSWORD, mEtPassword.getText().toString());
                mListener.onNextPressed(PrefsUtils.PREFS_KEY_PASSWORD);
            }
        });
        return view;
    }

}
