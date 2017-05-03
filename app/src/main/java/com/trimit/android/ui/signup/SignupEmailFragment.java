package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.trimit.android.R;
import com.trimit.android.utils.CustomEditText;
import com.trimit.android.utils.PrefsUtils;

import io.reactivex.disposables.CompositeDisposable;


public class SignupEmailFragment extends SignupBaseFragment {
    public static final String TAG = "SignupEmailFragment";

    private CompositeDisposable mDisposables;
    CustomEditText mEtEmail;
    private ProgressBar mProgress;

    public SignupEmailFragment() {
        mDisposables=new CompositeDisposable();
    }

    public static SignupEmailFragment newInstance() {
        return new SignupEmailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_email, container, false);
        mListener.setQuestion(getString(R.string.text_signup_email));
        mEtEmail=(CustomEditText) view.findViewById(R.id.et_email);

        mEtEmail.setVisibility(View.VISIBLE);
        mEtEmail.setHint(getString(R.string.et_email));
        mEtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEtEmail.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEtEmail.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideSoftKeyboard();

                handled = true;
            }
            return handled;
        });
        setUpNextButton(view);
        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_EMAIL, mEtEmail)){
                mDataProvider.getPrefs().setStringValue(PrefsUtils.PREFS_KEY_EMAIL, mEtEmail.getText().toString());
                mListener.onNextPressed(PrefsUtils.PREFS_KEY_EMAIL);
            }
        });
        return view;
    }

    private void nextClicked(){

        if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_EMAIL, mEtEmail)){
            mDataProvider.getPrefs().setStringValue(PrefsUtils.PREFS_KEY_EMAIL, mEtEmail.getText().toString());
            mListener.onNextPressed(PrefsUtils.PREFS_KEY_EMAIL);
        }
    }

}
