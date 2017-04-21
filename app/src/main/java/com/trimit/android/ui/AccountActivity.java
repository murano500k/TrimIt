package com.trimit.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.trimit.android.R;

public class AccountActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        textResult=(TextView) findViewById(R.id.text_result);
        if(getIntent()!=null){
            String loginResult = getIntent().getStringExtra(EXTRA_RESULT);
            if(loginResult!=null)textResult.setText(loginResult);
        }
    }
}
