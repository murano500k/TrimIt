package com.trimit.android.utils;

import android.text.TextUtils;

/**
 * Created by artem on 4/19/17.
 */

public class InputUtils {
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
