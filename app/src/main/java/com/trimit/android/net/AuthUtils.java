package com.trimit.android.net;

import com.trimit.android.utils.PrefsUtils;


public class AuthUtils {

    PrefsUtils mPrefsUtils;

    public static final int TOKEN_VALID_FOR = 3600000;

    public AuthUtils(PrefsUtils mPrefsUtils) {
        this.mPrefsUtils = mPrefsUtils;
    }

    public boolean hasValidToken(){
        String token = mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_ACCESS_TOKEN);
        long timestamp = mPrefsUtils.getLongValue(PrefsUtils.PREFS_KEY_TOKEN_TIMESTAMP);
        long currentTime = System.currentTimeMillis();
        if(token!=null && !token.isEmpty() && (currentTime-timestamp)<TOKEN_VALID_FOR) return true;
        else return false;
    }
    public String getToken(){
        return mPrefsUtils.getStringValue(PrefsUtils.PREFS_KEY_ACCESS_TOKEN);
    }
    public void updateToken(String token){
        mPrefsUtils.setStringValue(PrefsUtils.PREFS_KEY_ACCESS_TOKEN, token);
        long timestamp = System.currentTimeMillis();
        mPrefsUtils.setLongValue(PrefsUtils.PREFS_KEY_TOKEN_TIMESTAMP, timestamp);
    }

}
