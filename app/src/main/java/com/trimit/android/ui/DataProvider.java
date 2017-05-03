package com.trimit.android.ui;

import com.trimit.android.net.RetroUtils;
import com.trimit.android.utils.PrefsUtils;

/**
 * Created by artem on 4/27/17.
 */

public interface DataProvider {
    PrefsUtils getPrefs();

    RetroUtils getRetro();
}
