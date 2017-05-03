package com.trimit.android.ui.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trimit.android.App;
import com.trimit.android.R;
import com.trimit.android.model.BarberType;
import com.trimit.android.net.RetroUtils;
import com.trimit.android.ui.BaseActivity;
import com.trimit.android.ui.LoginActivity;
import com.trimit.android.utils.InputUtils;
import com.trimit.android.utils.PrefsUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends BaseActivity implements View.OnClickListener, OnSignupFragmentInteractionListener, FieldsValidator{

    protected static final String TAG = "BaseActivity";

    private static final String BUNDLE_FIELD1 = "BUNDLE_FIELD1";
    private static final String BUNDLE_FIELD2 = "BUNDLE_FIELD2";

    protected View mContent;
    protected ImageView mBtnBack;
    protected TextView mTextAcceptTerms;
    private TextView mTextQuestion;
    private ProgressBar mProgressBar;

    @Inject
    protected RetroUtils mRetroUtils;
    @Inject
    protected PrefsUtils mPrefsUtils;

    private FragmentManager mFragmentManager;
    private String mCurrentTag;
    private ArrayList<BarberType> mBarberTypes;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);
        mFragmentManager=getSupportFragmentManager();
        setContentView(R.layout.activity_signup);
        mContent =findViewById(R.id.content);
        mTextQuestion =(TextView) findViewById(R.id.text_question);
        mBtnBack =(ImageView) findViewById(R.id.btn_back);
        mTextAcceptTerms =(TextView) findViewById(R.id.text_accept_terms);
        mBtnBack.setOnClickListener(this);
        mProgressBar =(ProgressBar) findViewById(R.id.progress);
        showFirst();
    }

    private void showFirst() {
        SignupBaseFragment fragment=SignupNameFragment.newInstance();
        mCurrentTag=SignupNameFragment.TAG;
        fragment.setRetainInstance(true);
        mFragmentManager.beginTransaction()
                .replace(R.id.signup_fragment_container, fragment, mCurrentTag)
                .commit();
    }


    public void setQuestion(String question){
        mTextQuestion.setText(question);
    }

    @Override
    public void setTosVisibily(int visibily) {
        mTextAcceptTerms.setVisibility(visibily);
    }


    @Override
    public void onNextPressed(String fieldName) {
        SignupBaseFragment newFragment;

        if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_FIRST_NAME)){
            newFragment=SignupEmailFragment.newInstance();
            mCurrentTag=SignupEmailFragment.TAG;

        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_EMAIL)){

            newFragment=SignupPasswordFragment.newInstance();
            mCurrentTag=SignupPasswordFragment.TAG;

        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_PASSWORD)){

            newFragment=SignupBirthdayFragment.newInstance();
            mCurrentTag=SignupBirthdayFragment.TAG;

        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_BIRTHDAY)){
            newFragment=SignupGenderFragment.newInstance();
            mCurrentTag=SignupGenderFragment.TAG;

        } else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_GENDER)){
            getBarberTypes();
            return;
        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_BARBER_TYPE)){
            signUp();
            return;

        }else {
            Log.e(TAG, "onNextPressed: error "+mCurrentTag+ " not found" );
            return;

        }
        if(!TextUtils.equals(mCurrentTag, SignupBarberTypeFragment.TAG)){
            setTosVisibily(View.GONE);
        }
        newFragment.setRetainInstance(true);
        mFragmentManager.beginTransaction()
                .replace(R.id.signup_fragment_container, newFragment,mCurrentTag)
                .addToBackStack(mCurrentTag)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            default:
                Log.w(TAG, "onClick: view not found");
                break;
        }
    }


    @Override
    public void onBackPressed() {
        int entryCount = mFragmentManager.getBackStackEntryCount();
        Log.d(TAG, "onBackPressed: "+entryCount);
        if(entryCount>0) {
            mCurrentTag=mFragmentManager.getBackStackEntryAt(entryCount-1).getName();
            mFragmentManager.popBackStack();
        }
        else super.onBackPressed();
    }

    private void signUp() {
        Toast.makeText(this, "registering...", Toast.LENGTH_SHORT).show();
        mDisposables.add(mRetroUtils.createUserFromPrefs().subscribe(userCreateResponce -> {
            Log.d(TAG, "accept: "+userCreateResponce);
            String userId=String.valueOf(userCreateResponce.getUser().getUserId());
            mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_USER_ID, userId);
            Log.d(TAG, "userId: "+userId);
            hideSoftKeyboard();
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, throwable -> {
            Log.e(TAG, "error: "+throwable.getMessage());
            throwable.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("error")
                    .setMessage(throwable.getMessage())
                    .create().show();
            hideSoftKeyboard();
        }));
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean checkFieldsValid(String fieldName, EditText editText1, EditText editText2) {
        if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_PASSWORD)){
            return checkPasswordValid(editText1, editText2);
        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_FIRST_NAME)){
            return checkFieldNotEmpty(editText1) && checkFieldNotEmpty(editText2);
        }else
        return false;
    }

    @Override
    public boolean checkFieldValid(String fieldName, EditText editText) {
        if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_EMAIL)){
            return checkEmailValid(editText);
        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_GENDER)){
            return checkFieldNotEmpty(editText);
        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_BARBER_TYPE)){
            return checkFieldNotEmpty(editText);
        }else if(TextUtils.equals(fieldName, PrefsUtils.PREFS_KEY_BIRTHDAY)){
            return checkBirthdayValid(editText);
        }
        return false;
    }

    public boolean checkEmailValid(EditText mEtEmail) {
        mProgressBar.setVisibility(View.VISIBLE);
        String textEmail = mEtEmail.getText().toString();
        if(!InputUtils.isValidEmail(textEmail)){
            mEtEmail.setError(getString(R.string.error_field_not_valid));
            return false;
        } else {
                mDisposables.add(getRetro().checkEmailObservable(textEmail).subscribe(emailExistsResponce -> {
                    if (emailExistsResponce.getEmailExists()) {
                        mEtEmail.setError(getString(R.string.error_email_in_use));
                    } else {
                        getPrefs().setStringValue(PrefsUtils.PREFS_KEY_EMAIL, textEmail);
                        onNextPressed(PrefsUtils.PREFS_KEY_EMAIL);
                    }
                    mProgressBar.setVisibility(View.GONE);
                }, throwable -> {
                    throwable.printStackTrace();
                    mEtEmail.setError(getString(R.string.error_field_not_valid));
                    mProgressBar.setVisibility(View.GONE);
                }));
        }
        return false;
    }

    public boolean checkPasswordValid(EditText editTextPassword, EditText editTextConfirm) {
        String textField1=null;
        String textField2=null;
        textField1 = editTextPassword.getText().toString();
        if(textField1.isEmpty()) {
            editTextPassword.setError(getString(R.string.error_field_empty));
            return false;
        }
        textField2= editTextConfirm.getText().toString();
        if(textField2.isEmpty()) {
            editTextConfirm.setError(getString(R.string.error_field_empty));
            return false;
        }else if(!TextUtils.equals(textField1, textField2)){
            editTextConfirm.setError(getString(R.string.error_passwords_not_match));
            editTextPassword.setError(getString(R.string.error_passwords_not_match));
            return false;
        } else {
            getPrefs().setStringValue(PrefsUtils.PREFS_KEY_PASSWORD, textField2);
        }
        return true;
    }

    public boolean checkFieldNotEmpty(EditText text) {
        if(text.getText().length()==0) {
            text.setError(getString(R.string.error_field_empty));
            return false;
        }
        return true;
    }

    private void getBarberTypes(){
        Log.d(TAG, "getBarberTypes");
        mDisposables.add(getRetro().getBarberTypesObservable().subscribe(
                responceBarberType -> {
                    Log.d(TAG, "accept: " + responceBarberType);
                    Log.d(TAG, "is success: " + responceBarberType.getSuccess());
                    if (responceBarberType.getSuccess()) {
                        showBarberFragment(responceBarberType.getBarberType());
                    }else Log.e(TAG, "getBarberTypes: error" );
                },
                Throwable::printStackTrace
        ));
    }

    private void showBarberFragment(ArrayList<BarberType> list) {
        this.mBarberTypes=list;
        SignupBarberTypeFragment newFragment=SignupBarberTypeFragment.newInstance(list);
        mCurrentTag=SignupBarberTypeFragment.TAG;
        newFragment.setRetainInstance(true);
        mFragmentManager.beginTransaction()
                .replace(R.id.signup_fragment_container, newFragment,mCurrentTag)
                .addToBackStack(mCurrentTag)
                .commit();
        mCurrentTag=SignupBarberTypeFragment.TAG;
        setTosVisibily(View.VISIBLE);
    }

    public boolean checkBirthdayValid(EditText mETBirthday) {
        String textBirthday = mETBirthday.getText().toString();
        Log.d(TAG, "textBD: "+textBirthday+" "+textBirthday.matches("^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$"));
        if(textBirthday.matches("^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$")){
            return true;
        }
        else return false;
    }
}
