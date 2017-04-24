package com.trimit.android.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trimit.android.App;
import com.trimit.android.R;
import com.trimit.android.utils.CustomEditText;
import com.trimit.android.utils.PrefsUtils;
import com.trimit.android.utils.net.RetroUtils;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class SignupBaseActivity extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "BaseActivity";

    private static final String BUNDLE_FIELD1 = "BUNDLE_FIELD1";
    private static final String BUNDLE_FIELD2 = "BUNDLE_FIELD2";

    protected View content;
    protected CustomEditText etField1, etField2;
    protected AutoCompleteTextView textAutoComplete;
    protected ImageView btnNext, btnBack;
    protected TextView textAcceptTerms;
    private TextView textQuestion;
    private ProgressBar progressBar;

    @Inject
    protected RetroUtils mRetroUtils;
    @Inject
    protected PrefsUtils mPrefsUtils;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);
        setContentView(R.layout.activity_base);
        content=findViewById(R.id.content);
        textQuestion=(TextView) findViewById(R.id.text_question);
        etField1=(CustomEditText)findViewById(R.id.et_field1);
        etField2=(CustomEditText)findViewById(R.id.et_field2);
        textAutoComplete =(AutoCompleteTextView)findViewById(R.id.text_auto_complete);
        btnNext=(ImageView) findViewById(R.id.btn_next);
        btnBack=(ImageView) findViewById(R.id.btn_back);
        textAcceptTerms=(TextView) findViewById(R.id.text_accept_terms);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        progressBar=(ProgressBar) findViewById(R.id.progress);
        setupFields();
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(BUNDLE_FIELD1)){
                etField1.setText(savedInstanceState.getString(BUNDLE_FIELD1, null));
            }
            if(savedInstanceState.containsKey(BUNDLE_FIELD2)){
                etField2.setText(savedInstanceState.getString(BUNDLE_FIELD2, null));
            }
        }
    }


    public void setQuestion(String question){
        textQuestion.setText(question);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(etField1.getVisibility()==View.VISIBLE && !etField1.getText().toString().isEmpty()) outState.putString(BUNDLE_FIELD1, etField1.getText().toString());
        if(etField2.getVisibility()==View.VISIBLE && !etField2.getText().toString().isEmpty()) outState.putString(BUNDLE_FIELD2, etField2.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public abstract void setupFields();
    public abstract boolean checkFieldsCorrect();
    public abstract void nextActivity();

    public boolean checkFieldNotEmpty(EditText et){
        if(et.getText().toString().isEmpty()) {
            et.setError(getString(R.string.error_field_empty));
            return false;
        }else return true;
    }




    public void showTermsMessage(){
        textAcceptTerms.setVisibility(View.VISIBLE);
    }

    public void setNextButtonBg(int id){
        btnNext.setImageResource(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                Log.d(TAG, "onNextClick");
                if(checkFieldsCorrect()) nextActivity();
                break;
            case R.id.btn_back:
                Log.d(TAG, "onBackClick");
                finish();
                break;
        }
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
