package com.trimit.android.ui.barber;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.trimit.android.R;
import com.trimit.android.model.barber.Barber;
import com.trimit.android.ui.OnFragmentInteractionListener;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BarberListFragment extends Fragment implements  BarberAdapter.DistanceProvider, BarberAdapter.ItemClickListener  {
    private static final String TAG = "HomeFragment";
    private OnFragmentInteractionListener mListener;

    private BarberAdapter mAdapter;

    private TextView mTextCounter;

    private RecyclerView mRecyclerView;

    private ProgressBar mProgress;
    CompositeDisposable mDisposables;


    public BarberListFragment() {
        mDisposables=new CompositeDisposable();
    }

    public static BarberListFragment newInstance() {
        BarberListFragment fragment = new BarberListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_barber_list, container, false);
        mProgress=(ProgressBar)view.findViewById(R.id.progress);
        mTextCounter=(TextView) view.findViewById(R.id.text_counter);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new BarberAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mProgress.setVisibility(View.VISIBLE);

        return view;
    }

    public void showData(List<Barber> list, String errorMsg) {
        mProgress.setVisibility(View.GONE);
        if(errorMsg==null) {
            Log.d(TAG, "showData: "+list);
            mAdapter.addItems(list);
            setCounter(list.size());
        }else {
            Log.e(TAG, "showData: "+errorMsg );
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCounter(int count){
        mTextCounter.setText(count+" barbers found");
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        mDisposables.add(mListener.getRetro().getBarbersObservable().subscribe(o -> {
            Log.d(TAG, "getData: success="+o.getSuccess());
            showData(o.getBarbers(), null);
        },throwable -> {
            showData(null, throwable.getMessage());
        }));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDisposables.dispose();
    }

    @Override
    public void itemClicked(Barber barber) {
        Toast.makeText(getContext(), barber.getAccount().get(0).getFirstName()+" clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public float getDistance(LatLng point){
        if(mListener.getPrefs().getUserLocation()!=null){
            LatLng userLatLng=mListener.getPrefs().getUserLocation();
            return distance(userLatLng.getLatitude(),userLatLng.getLongitude(),point.getLatitude(), point.getLongitude());
        }
        return 0.0f;
    }

    private float distance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }
}
