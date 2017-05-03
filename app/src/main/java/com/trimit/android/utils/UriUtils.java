package com.trimit.android.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by artem on 4/28/17.
 */

public class UriUtils {
    private static final String TAG = "UriUtils";
    public static final String CONTENT_AUTHORITY = "com.trimit.android";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);




    //<scheme>://<authority><absolute path>?<query>#<fragment>
     //<relative or absolute path>?<query>#<fragment>
    public static Uri getUri(String tag){
        Log.d(TAG, "getUri: class="+tag);
        Uri uri = BASE_CONTENT_URI.buildUpon().appendPath(tag).build();
        Log.d(TAG, "getUri: uri="+uri);
        return uri;
    }

    public static Uri getUri(String tag, String param){
        Log.d(TAG, "getUri: class="+tag +" param="+param);
        Uri uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(tag)
                .fragment(param)
                .build();
        Log.d(TAG, "getUri: uri="+uri);
        return uri;
    }
    public static Uri getUri(String tag, int param){
        return getUri(tag, String.valueOf(param));
    }


}
