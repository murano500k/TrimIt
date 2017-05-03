package com.trimit.android.ui.barber;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.trimit.android.R;
import com.trimit.android.model.barber.BarberResponce;
import com.trimit.android.ui.DataProvider;
import com.trimit.android.ui.OnFragmentInteractionListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class BarberReviewsFragment extends BarberBaseFragment {
    public static final String TAG = "BarberReviewsFragment";

    private static final String ARG_BARBER_ID = "ARG_BARBER_ID";

    private int mBarberId;

    private DataProvider mDataProvider;
    private OnFragmentInteractionListener mListener;


    public BarberReviewsFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(int barberId) {
        BarberReviewsFragment fragment = new BarberReviewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BARBER_ID, barberId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBarberId = getArguments().getInt(ARG_BARBER_ID);
        }else mBarberId=Integer.MAX_VALUE;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber_details, container, false);
        setBackButton(view);
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
                    + " must implement mDataProvider");
        }
        if(Integer.MAX_VALUE==mBarberId) {
            Toast.makeText(getContext(),"no barberId", Toast.LENGTH_SHORT).show();
            return;
        }
        /*mListener.getRetro().getBarberObject(mBarberId).subscribe(o -> {
            Log.d(TAG, "getBarberObject: "+o);
        }, Throwable::printStackTrace);*/
        mDataProvider.getRetro()
                .getBarberObservable(String.valueOf(mBarberId))
                .subscribe(o -> {
                    initViews(o);
                },
                        throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        });
    }

    private void initViews(BarberResponce responce) throws Exception{
        Log.d(TAG, "initViews responceSuccess="+responce.getSuccess());
    }
    private void setText(TextView textView, String text){
        if(text!=null) textView.setText(text);
        else Log.e(TAG, "setText: error");
    }
    private void loadImage(ImageView imageView, String url){
        if(url==null){
            Log.e(TAG, "loadImage: error ");
            return;
        }
        Picasso.with(getContext()).load(url).into(imageView);
    }
    private void loadImage(CircleImageView imageView, String url){
        if(url==null){
            Log.e(TAG, "loadImage: error ");
            return;
        }
        Picasso.with(getContext()).load(url).into(imageView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDataProvider = null;
    }

}
