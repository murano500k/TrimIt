package com.trimit.android.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.trimit.android.R;
import com.trimit.android.model.BarberType;
import com.trimit.android.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class SignupBarberTypeFragment extends SignupBaseFragment {
    public static final String TAG = "SignupBarberFragment";
    private static final String ARG_BARBER_TYPES = "ARG_BARBER_TYPES";


    AutoCompleteTextView mEtBarberType;
    private List<BarberType> mBarberTypes;
    private CompositeDisposable mDisposables;
    private List<String> mBarberTypeLabels;

    public SignupBarberTypeFragment() {
        mDisposables=new CompositeDisposable();
    }

    public static SignupBarberTypeFragment newInstance(ArrayList<BarberType> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BARBER_TYPES, list);
        SignupBarberTypeFragment f=new SignupBarberTypeFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()==null || !getArguments().containsKey(ARG_BARBER_TYPES)) throw new NullPointerException("Barber types not provided");
        mBarberTypes=getArguments().getParcelableArrayList(ARG_BARBER_TYPES);
        mBarberTypeLabels = new ArrayList<>();
        for(BarberType barberType : mBarberTypes){
            mBarberTypeLabels.add(barberType.getBarberTypeType());
            Log.d(TAG, "add label: "+barberType.getBarberTypeType());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup_barber_type, container, false);
        setUpNextButton(view);

        mListener.setQuestion(getString(R.string.text_signup_barber_type));

        mEtBarberType=(AutoCompleteTextView) view.findViewById(R.id.et_barber_type);
        mEtBarberType.setHint(getString(R.string.et_barber_type));

        mBtnNext.setOnClickListener(v -> {
            if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_BARBER_TYPE, mEtBarberType)){
                String barberTypeId=getBarberTypeId(mEtBarberType.getText().toString());
                if(barberTypeId!=null) {
                    mDataProvider.getPrefs()
                            .setStringValue(PrefsUtils.PREFS_KEY_BARBER_TYPE, barberTypeId);
                    mListener.onNextPressed(PrefsUtils.PREFS_KEY_BARBER_TYPE);
                }else Log.e(TAG, "onCreateView: error");
            }
        });
        Log.d(TAG, "barber types drop down: "+mBarberTypeLabels.size());
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),R.layout.auto_complete_list_item, mBarberTypeLabels);

        mEtBarberType.setThreshold(1);
        mEtBarberType.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        mEtBarberType.setOnTouchListener((v, event) -> {
            mEtBarberType.showDropDown();
            return true;
        });
        mEtBarberType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mFieldsValidator.checkFieldValid(PrefsUtils.PREFS_KEY_BARBER_TYPE, mEtBarberType)){
                    String barberTypeId=getBarberTypeId(mEtBarberType.getText().toString());
                    if(barberTypeId!=null) {
                        mDataProvider.getPrefs()
                                .setStringValue(PrefsUtils.PREFS_KEY_BARBER_TYPE, barberTypeId);
                        mListener.onNextPressed(PrefsUtils.PREFS_KEY_BARBER_TYPE);
                    }else Log.e(TAG, "onCreateView: error");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    private String getBarberTypeId(String textBarberType) {
        if(mBarberTypes!=null && mBarberTypes.size()>0){
            for(BarberType barberType : mBarberTypes){
                if(TextUtils.equals(barberType.getBarberTypeType(), textBarberType)) return barberType.getBarberTypeId();
            }
        }
        return null;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mDisposables.dispose();
    }




}
