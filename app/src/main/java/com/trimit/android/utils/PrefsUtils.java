package com.trimit.android.utils;

import android.content.Context;
import android.preference.PreferenceManager;

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


    public static void setStringValue(Context  context, String key , String value){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key,value).apply();
    }
    public static String getStringValue(Context  context, String key){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
    }

    public static long getLongValue(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, Long.MIN_VALUE);
    }

    public static void setLongValue(Context context, String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key,value).apply();
    }
}
