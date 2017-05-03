package com.trimit.android.ui.barber;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.trimit.android.R;
import com.trimit.android.model.barber.Barber;
import com.trimit.android.ui.DataProvider;
import com.trimit.android.ui.OnFragmentInteractionListener;
import com.trimit.android.utils.UriUtils;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BarberListFragment extends BarberBaseFragment implements  BarberAdapter.DistanceProvider, BarberAdapter.ItemClickListener  {

    public static final String TAG = "BarberListFragment";
    private OnFragmentInteractionListener mListener;
    private DataProvider mDataProvider;


    private BarberAdapter mAdapter;

    private TextView mTextCounter;

    private RecyclerView mRecyclerView;

    CompositeDisposable mDisposables;

    private SwipeRefreshLayout mSwipeRefreshLayout;


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
        setRetainInstance(true);
        View view=inflater.inflate(R.layout.fragment_barber_list, container, false);
        setBackButton(view);
        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> {
                    Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                    getData();
                }
        );

        mTextCounter=(TextView) view.findViewById(R.id.text_counter);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new BarberAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        setCounter(0);
        return view;
    }
    public void getData(){
        Log.d(TAG, "getData");
        mDisposables.add(mDataProvider.getRetro().getBarbersObservable().subscribe(o -> {
            Log.d(TAG, "getData: success="+o.getSuccess());
            showData(o.getBarbers(), null);
        },throwable -> {
            throwable.printStackTrace();
            showData(null, throwable.getMessage());
        }));
    }

    public void showData(List<Barber> list, String errorMsg) {
        mSwipeRefreshLayout.setRefreshing(false);
        if(errorMsg==null) {
            mAdapter.addItems(list);
            mAdapter.notifyDataSetChanged();
            setCounter(list.size());
        }else {
            Log.e(TAG, "showData: "+errorMsg );
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCounter(int count){
        mTextCounter.setVisibility(View.VISIBLE);
        String text =count+" barbers found";
        if(count==0) text+=". Swipe to refresh";
        mTextCounter.setText(text);
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
        getData();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDataProvider=null;
        mDisposables.dispose();
    }

    @Override
    public void itemClicked(Barber barber) {
        Toast.makeText(getContext(), barber.getBarberId()+" clicked", Toast.LENGTH_SHORT).show();
        mListener.onFragmentInteraction(UriUtils.getUri(BarberDetailsFragment.TAG, barber.getBarberId()));
    }

    @Override
    public float getDistance(LatLng point){
        if(mDataProvider.getPrefs().getUserLocation()!=null){
            LatLng userLatLng=mDataProvider.getPrefs().getUserLocation();
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
