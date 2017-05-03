package com.trimit.android.ui.signup;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.trimit.android.R;
import com.trimit.android.ui.DataProvider;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class SignupBaseFragment extends Fragment {

    public static final String TAG="SignupBaseFragment";

    protected OnSignupFragmentInteractionListener mListener;
    protected DataProvider mDataProvider;
    protected FieldsValidator mFieldsValidator;
    protected ImageButton mBtnNext;


    public SignupBaseFragment() {
        // Required empty public constructor
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignupFragmentInteractionListener) {
            mListener = (OnSignupFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof OnSignupFragmentInteractionListener) {
            mDataProvider = (DataProvider) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof OnSignupFragmentInteractionListener) {
            mFieldsValidator = (FieldsValidator) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDataProvider=null;
        mFieldsValidator=null;
    }

    protected void setUpNextButton(View v){
        mBtnNext=(ImageButton) v.findViewById(R.id.btn_next);
    }

    public boolean checkFieldNotEmpty(EditText et){
        if(et.getText().toString().isEmpty()) {
            et.setError(getString(R.string.error_field_empty));
            return false;
        }else return true;
    }

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


}
