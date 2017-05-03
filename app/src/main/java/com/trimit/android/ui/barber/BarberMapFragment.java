package com.trimit.android.ui.barber;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.squareup.picasso.Picasso;
import com.trimit.android.R;
import com.trimit.android.model.barber.Barber;
import com.trimit.android.model.barber.BarberResponce;
import com.trimit.android.ui.DataProvider;
import com.trimit.android.ui.OnFragmentInteractionListener;
import com.trimit.android.utils.ImageResUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class BarberMapFragment extends BarberBaseFragment {
    public static final String TAG = "BarberMapFragment";
    private static final String ARG_BARBER_ID = "ARG_BARBER_ID";

    private int mBarberId;

    private OnFragmentInteractionListener mListener;
    private DataProvider mDataProvider;

    private TextView textName, textAddress, textCode;
    private CircleImageView imgProfile;
    private ImageView imgStars;
    private Button btnBookNow;
    private MapView mMapView;
    private MapboxMap mMap;


    public BarberMapFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(int barberId) {
        BarberMapFragment fragment = new BarberMapFragment();
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
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_token));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber_map, container, false);
        setBackButton(view);
        textName=(TextView) view.findViewById(R.id.text_name);
        textAddress =(TextView) view.findViewById(R.id.text_address);
        textCode =(TextView) view.findViewById(R.id.text_code);
        imgStars=(ImageView)view.findViewById(R.id.img_stars);
        imgProfile=(CircleImageView)view.findViewById(R.id.img_profile);
        btnBookNow=(Button)view.findViewById(R.id.btn_book_now);

        btnBookNow.setOnClickListener(v -> {
            Log.w(TAG, "book now clicked");
            Toast.makeText(getContext(), getString(R.string.not_implemented), Toast.LENGTH_SHORT).show();
        });

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(mapboxMap -> {
            mMap=mapboxMap;
            getData();
        });
        return view;
    }

    private void getData() {
        Log.d(TAG, "getData: "+mBarberId);
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
    }

    private void initViews(BarberResponce responce) throws Exception{
        Log.d(TAG, "initViews responceSuccess="+responce.getSuccess());
        if(!responce.getSuccess() ||
                responce.getBarbers()==null ||
                responce.getBarbers().size()==0) {
            throw new NullPointerException("error empty responce");
        }
        Barber barber= responce.getBarbers().get(0);

        String name = barber.getAccount().get(0).getFirstName();
        String address = barber.getAddressLine1()+ " "+ barber.getAddressLine2();
        String code = barber.getPostcode();
        textName.setText(name);
        textAddress.setText(address);
        textCode.setText(code);

        String profilePicUrl = barber.getPhoto().getProfilePicture().getUrl();
        if(profilePicUrl!=null)
            Picasso.with(imgProfile.getContext()).load(profilePicUrl).into(imgProfile);
        imgStars.setImageResource(ImageResUtils.getStarsResId(barber.getOverallStars()));
        double lat = Double.parseDouble(barber.getBarbershop().get(0).getLocationLat());
        double lon = Double.parseDouble(barber.getBarbershop().get(0).getLocationLong());
        String barbershop=barber.getBarbershop().get(0).getBarbershopName()+"\n"+name;
        showOnMap(new LatLng(lat,lon), barbershop);
    }

    private void showOnMap(LatLng latLng, String barbershop) {
        Log.d(TAG, "showOnMap: "+latLng+" info: "+barbershop);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.setPosition(latLng);
        markerOptions.setTitle(barbershop);
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng),5000);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDataProvider=null;

    }

}
