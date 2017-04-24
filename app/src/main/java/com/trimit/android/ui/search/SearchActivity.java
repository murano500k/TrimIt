package com.trimit.android.ui.search;

import com.trimit.android.R;
import com.trimit.android.ui.MainActivity;

/**
 * Created by artem on 4/24/17.
 */

public class SearchActivity extends MainActivity {
    private static final String TAG = "SearchActivity";

    public SearchActivity() {
        mMenuPageId= R.id.navigation_search;
    }

    @Override
    public void showContent() {
        getLayoutInflater().inflate(R.layout.content_search, mContent);
        mContainer =findViewById(R.id.container);
        mContainer.setBackground(getResources().getDrawable(R.drawable.bg_search));
    }
}
