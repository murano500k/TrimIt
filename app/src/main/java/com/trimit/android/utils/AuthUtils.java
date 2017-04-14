package com.trimit.android.utils;

import android.content.Context;

/**
 * Created by artem on 4/18/17.
 */

public class AuthUtils {
    public static final int TOKEN_VALID_FOR = 3600000;

    public static boolean hasValidToken(Context context){
        String token = PrefsUtils.getStringValue(context, PrefsUtils.PREFS_KEY_ACCESS_TOKEN);
        long timestamp = PrefsUtils.getLongValue(context, PrefsUtils.PREFS_KEY_TOKEN_TIMESTAMP);
        long currentTime = System.currentTimeMillis();
        if(token!=null && !token.isEmpty() && (currentTime-timestamp)<TOKEN_VALID_FOR) return true;
        else return false;
    }
    public static String getToken(Context context){
        return PrefsUtils.getStringValue(context, PrefsUtils.PREFS_KEY_ACCESS_TOKEN);
    }
    public static void updateToken(Context context,String token){
        PrefsUtils.setStringValue(context,PrefsUtils.PREFS_KEY_ACCESS_TOKEN, token);
        long timestamp = System.currentTimeMillis();
        PrefsUtils.setLongValue(context,PrefsUtils.PREFS_KEY_TOKEN_TIMESTAMP, timestamp);
    }

}
