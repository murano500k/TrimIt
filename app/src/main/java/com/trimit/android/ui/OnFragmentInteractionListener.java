package com.trimit.android.ui;

import android.net.Uri;

import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

/**
 * Created by artem on 4/26/17.
 */

public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);



    PrefsUtils getPrefs();

    RetroUtils getRetro();
}