package com.trimit.android.ui.signup;

/**
 * Created by artem on 4/28/17.
 */



public interface OnSignupFragmentInteractionListener {
    void setQuestion(String question);
    void setTosVisibily(int visibily);
    void onNextPressed(String fieldName);
}