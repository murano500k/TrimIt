package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.trimit.android.R;
import com.trimit.android.utils.CustomEditText;
import com.trimit.android.utils.PrefsUtils;

import java.util.Calendar;


public class SignupBirthdayFragment extends SignupBaseFragment {
    public static final String TAG = "SignupBirthdayFragment";

    CustomEditText mEtBirthday;

    public SignupBirthdayFragment() {

    }

    public static SignupBirthdayFragment newInstance() {
        return new SignupBirthdayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_birthday, container, false);
        setUpNextButton(view);
        mEtBirthday=(CustomEditText) view.findViewById(R.id.et_dob);
        mListener.setQuestion(getString(R.string.text_signup_dob));
        mEtBirthday.setVisibility(View.VISIBLE);
        mEtBirthday.setHint(getString(R.string.et_birthday));
        mEtBirthday.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEtBirthday.addTextChangedListener(tw);
        mEtBirthday.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEtBirthday.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideSoftKeyboard();
                if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_BIRTHDAY, mEtBirthday)){
                    mDataProvider.getPrefs().setStringValue(PrefsUtils.PREFS_KEY_BIRTHDAY, mEtBirthday.getText().toString());
                    mListener.onNextPressed(PrefsUtils.PREFS_KEY_BIRTHDAY);
                }
                handled = true;
            }
            return handled;
        });
        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_BIRTHDAY, mEtBirthday)){
                mDataProvider.getPrefs().setStringValue(PrefsUtils.PREFS_KEY_BIRTHDAY, mEtBirthday.getText().toString());
                mListener.onNextPressed(PrefsUtils.PREFS_KEY_BIRTHDAY);
            }
        });
        return view;
    }


    private TextWatcher tw = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                mEtBirthday.setText(current);
                mEtBirthday.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
