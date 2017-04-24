package com.trimit.android.utils;

import android.content.SharedPreferences;

/**
 * Created by artem on 4/18/17.
 */

public class PrefsUtils {
    
    public static final String PREFS_KEY_FIRST_NAME = "PREFS_KEY_FIRST_NAME";
    public static final String PREFS_KEY_LAST_NAME = "PREFS_KEY_LAST_NAME";
    public static final String PREFS_KEY_EMAIL = "PREFS_KEY_EMAIL";
    public static final String PREFS_KEY_BIRTHDAY = "PREFS_KEY_BIRTHDAY";
    public static final String PREFS_KEY_GENDER = "PREFS_KEY_GENDER";
    public static final String PREFS_KEY_BARBER_TYPE = "PREFS_KEY_BARBER_TYPE";
    public static final String PREFS_KEY_ACCESS_TOKEN = "PREFS_KEY_ACCESS_TOKEN";
    public static final String PREFS_KEY_TOKEN_TIMESTAMP = "PREFS_KEY_TOKEN_TIMESTAMP";
    public static final String PREFS_KEY_PASSWORD = "PREFS_KEY_PASSWORD";
    public static final String PREFS_KEY_USER_ID = "PREFS_KEY_USER_ID";
    public static final String PREFS_KEY_CUSTOMER_ID = "PREFS_KEY_CUSTOMER_ID";
    private final SharedPreferences mPrefs;

    public PrefsUtils(SharedPreferences preferences) {
        this.mPrefs = preferences;
    }

    public void setStringValue(String key , String value){
        mPrefs.edit().putString(key,value).apply();
    }
    public String getStringValue(String key){
        return mPrefs.getString(key, null);
    }

    public long getLongValue(String key) {
        return mPrefs.getLong(key, Long.MIN_VALUE);
    }

    public void setLongValue(String key, long value) {
        mPrefs.edit().putLong(key,value).apply();
    }
}
