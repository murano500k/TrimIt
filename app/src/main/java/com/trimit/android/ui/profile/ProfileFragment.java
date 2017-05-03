package com.trimit.android.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;
import com.trimit.android.R;
import com.trimit.android.model.getuser.User;
import com.trimit.android.ui.DataProvider;
import com.trimit.android.ui.OnFragmentInteractionListener;
import com.trimit.android.ui.WelcomeActivity;
import com.trimit.android.ui.other.TosFragment;
import com.trimit.android.utils.UriUtils;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";
    public static final String ARG_USERNAME = "ARG_USERNAME";
    private static final String ARG_PROFILE_PIC_URL = "ARG_PROFILE_PIC_URL";

    String mUserName;
    String mProfilePicUrl;

    private OnFragmentInteractionListener mListener;
    private DataProvider mDataProvider;
    private TextView mLabelWelcome;
    private CircleImageView mProfilePic;
    private CompositeDisposable mDisposables;


    public ProfileFragment() {
        // Required empty public constructor
        mDisposables=new CompositeDisposable();
    }

    public static ProfileFragment newInstance(String username, String profilePicUrl) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_PROFILE_PIC_URL, profilePicUrl);
        fragment.setArguments(args);
        return fragment;
    }
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mUserName=getArguments().getString(ARG_USERNAME);
            mProfilePicUrl=getArguments().getString(ARG_PROFILE_PIC_URL);
        }else {
            loadUserData();
        }
    }
    private void loadUserData() {
        Log.d(TAG, "loadUserData");
        if(mUserName!=null){
            String label  = getString(R.string.welcome)+" "+mUserName;
            mLabelWelcome.setText(label);
        }
        if(mProfilePicUrl!=null){
            Picasso.with(getContext()).load(mProfilePicUrl).into(mProfilePic);
        }
    }

    private int setUserData(User user) throws Exception{
        mUserName = user.getAccount().get(0).getFirstName();
        mProfilePicUrl=user.getPhoto().getProfilePicture().getUrl();
        if(mUserName==null || mProfilePicUrl==null) return -1;
        else return 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        mLabelWelcome = (TextView)view.findViewById(R.id.label_welcome);
        mProfilePic = (CircleImageView) view.findViewById(R.id.img_profile);
        setupMenuItems(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof DataProvider) {
            mDataProvider = (DataProvider) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDataProvider = null;
        mDisposables.dispose();
    }

    private void setupMenuItems(View v){
        RxView.clicks(v.findViewById(R.id.item_my_trims))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "item_my_trims clicked");
                    notImplemented();
                });
        RxView.clicks(v.findViewById(R.id.item_my_wallet))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "item_my_wallet clicked");
                    notImplemented();
                });
        RxView.clicks(v.findViewById(R.id.item_tos))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "item_tos clicked");
                    mListener.onFragmentInteraction(UriUtils.getUri(TosFragment.TAG));
                });
        RxView.clicks(v.findViewById(R.id.item_help))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "item_help clicked");
                    notImplemented();
                });
        RxView.clicks(v.findViewById(R.id.item_signout))
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    Log.d(TAG, "item_signout clicked");
                    signOut();
                });

    }

    private void notImplemented() {
        Log.w(TAG, "notImplemented");
        Toast.makeText(getContext(), getString(R.string.not_implemented), Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        Log.d(TAG, "signOut: ");
        mDataProvider.getPrefs().signOut();
        startActivity(new Intent(getContext(), WelcomeActivity.class));
        getActivity().finish();
    }

}
