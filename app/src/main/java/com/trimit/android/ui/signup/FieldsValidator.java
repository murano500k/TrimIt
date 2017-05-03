package com.trimit.android.ui.signup;

import android.widget.EditText;

/**
 * Created by artem on 4/28/17.
 */

public interface FieldsValidator {
    boolean checkFieldsValid(String fieldName, EditText editText1, EditText editText2);

    boolean checkFieldValid(String fieldName, EditText editText);
}
