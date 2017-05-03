package com.trimit.android.ui.barber;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trimit.android.R;

/**
 * Created by artem on 5/3/17.
 */

@SuppressLint("ValidFragment")
public abstract class BarberBaseFragment extends Fragment {
    private static final String TAG = "BarberBaseFragment";
    ImageButton mBtnBack;
    View mToolbar;
    TextView mTextTitle;

    public void setBackButton(View v) {
        mBtnBack = (ImageButton) v.findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(v1 -> {
            Log.d(TAG, "onBackPressed");
            getActivity().onBackPressed();
        });

        mTextTitle = (TextView) v.findViewById(R.id.toolbar_title);
        mToolbar = v.findViewById(R.id.toolbar);

        mToolbar.setVisibility(View.VISIBLE);
        mTextTitle.setText(getString(R.string.title_barber));
    }
}
