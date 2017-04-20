package com.trimit.android.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.trimit.android.R;

/**
 * Created by artem on 4/20/17.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    private boolean isError=false;
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        isError=true;
        if(error!=null && error.length()>0) {
            setBackground(getResources().getDrawable(R.drawable.et_bg_error));
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(isError) setBackground(getResources().getDrawable(R.drawable.et_bg));
        isError=false;
    }
}
