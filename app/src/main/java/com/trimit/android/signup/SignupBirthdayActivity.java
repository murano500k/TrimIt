package com.trimit.android.signup;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.trimit.android.R;
import com.trimit.android.SignupBaseActivity;
import com.trimit.android.utils.PrefsUtils;

import java.util.Calendar;

public class SignupBirthdayActivity extends SignupBaseActivity {



    @Override
    public void setupFields() {
        setQuestion(getString(R.string.text_signup_dob));
        etField2.setVisibility(View.VISIBLE);
        etField2.setHint(getString(R.string.et_birthday));
        etField2.setInputType(InputType.TYPE_CLASS_NUMBER);
        etField2.addTextChangedListener(tw);
        etField2.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etField2.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideSoftKeyboard();
                onClick(btnNext);
                handled = true;
            }
            return handled;
        });
    }

    @Override
    public boolean checkFieldsCorrect() {
        String textBirthday = etField2.getText().toString();
        Log.d(TAG, "textBD: "+textBirthday+" "+textBirthday.matches("^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$"));
        if(textBirthday.matches("^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$")){
            mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_BIRTHDAY, textBirthday);
            return true;
        }
        else return false;
    }

    @Override
    public void nextActivity() {
        Log.d(TAG, "nextActivity: SignupGenderActivity");
        startActivity(new Intent(this, SignupGenderActivity.class));
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
                etField2.setText(current);
                etField2.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
