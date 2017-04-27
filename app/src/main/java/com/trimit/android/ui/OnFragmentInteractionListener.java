package com.trimit.android.ui;

import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

/**
 * Created by artem on 4/26/17.
 */

public interface OnFragmentInteractionListener {
    void onFragmentInteraction(int barberId, String screenId);



    PrefsUtils getPrefs();

    RetroUtils getRetro();
}