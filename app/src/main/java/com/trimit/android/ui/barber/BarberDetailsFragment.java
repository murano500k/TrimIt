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

import com.squareup.picasso.Picasso;
import com.trimit.android.R;
import com.trimit.android.model.barber.Barber;
import com.trimit.android.model.barber.BarberResponce;
import com.trimit.android.model.barber.Gallery;
import com.trimit.android.model.barber.Photo;
import com.trimit.android.ui.DataProvider;
import com.trimit.android.ui.OnFragmentInteractionListener;
import com.trimit.android.ui.other.TosFragment;
import com.trimit.android.utils.ImageResUtils;
import com.trimit.android.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BarberDetailsFragment extends BarberBaseFragment {
    public static final String TAG = "BarberDetailsFragment";
    private static final String ARG_BARBER_ID = "ARG_BARBER_ID";

    private int mBarberId;

    private DataProvider mDataProvider;
    private OnFragmentInteractionListener mListener;

    private TextView textName, textTest, textReviews, textTos;
    private ImageView imgExample1, imgExample2, imgExample3, imgExample4, imgExample5;
    private ImageView imgMap;
    private ImageView imgBarbershop;
    private CircleImageView imgProfile;
    private ImageView imgStars, imgStarsReviews;
    private Button btnBookNow;


    public BarberDetailsFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(int barberId) {
        BarberDetailsFragment fragment = new BarberDetailsFragment();
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
        textName=(TextView) view.findViewById(R.id.text_name);
        textReviews=(TextView) view.findViewById(R.id.text_review);
        textTos=(TextView) view.findViewById(R.id.text_tos);
        textTest=(TextView) view.findViewById(R.id.text_address);
        imgBarbershop=(ImageView)view.findViewById(R.id.img_barbershop_photo);
        imgExample1=(ImageView)view.findViewById(R.id.img_example_1);
        imgExample2=(ImageView)view.findViewById(R.id.img_example_2);
        imgExample3=(ImageView)view.findViewById(R.id.img_example_3);
        imgExample4=(ImageView)view.findViewById(R.id.img_example_4);
        imgExample5=(ImageView)view.findViewById(R.id.img_example_5);
        imgMap=(ImageView)view.findViewById(R.id.img_map);
        imgStars=(ImageView)view.findViewById(R.id.img_stars);
        imgStarsReviews=(ImageView)view.findViewById(R.id.img_stars_review);
        imgProfile=(CircleImageView)view.findViewById(R.id.img_profile);
        btnBookNow=(Button)view.findViewById(R.id.btn_book_now);
        btnBookNow.setOnClickListener(v -> {
            Log.w(TAG, "book now clicked");
            Toast.makeText(getContext(), getString(R.string.not_implemented), Toast.LENGTH_SHORT).show();
        });
        imgMap.setOnClickListener(v -> {
            Log.w(TAG, "map clicked");
            mListener.onFragmentInteraction(UriUtils.getUri(BarberMapFragment.TAG,mBarberId));
        });
        textReviews.setOnClickListener(v -> {
            Log.w(TAG, "Reviews clicked");
            mListener.onFragmentInteraction(UriUtils.getUri(BarberReviewsFragment.TAG,mBarberId));
        });
        textTos.setOnClickListener(v -> {
            Log.w(TAG, "Tos clicked");
            mListener.onFragmentInteraction(UriUtils.getUri(TosFragment.TAG));
        });
        mDataProvider.getRetro()
                .getBarberObservable(String.valueOf(mBarberId))
                .subscribe(this::initViews,
                        throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        });
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

    }

    private void initViews(BarberResponce responce) throws Exception{
        Log.d(TAG, "initViews responceSuccess="+responce.getSuccess());
        if(!responce.getSuccess() ||
                responce.getBarbers()==null ||
                responce.getBarbers().size()==0) {
            throw new NullPointerException("error empty responce");
        }
        Barber barber= responce.getBarbers().get(0);
        Photo photo = barber.getPhoto();
        String name = barber.getAccount().get(0).getFirstName()+" "+barber.getAccount().get(0).getLastName();
        String test = barber.getPostcode();
        String profileImageUrl = photo.getProfilePicture().getUrl();
        String barbershopImageUrl = photo.getCoverPicture().getUrl();
        List <String > galleryImagesUrls= new ArrayList<>();
        for(Gallery gallery: photo.getGallery()){
            galleryImagesUrls.add(gallery.getUrl());
        }
        int starsResId=ImageResUtils.getStarsResId(barber.getOverallStars());


        setText(textName, name);
        setText(textTest, test);

        loadImage(imgProfile,profileImageUrl);
        loadImage(imgBarbershop,barbershopImageUrl);

        imgStars.setImageResource(starsResId);
        imgStarsReviews.setImageResource(starsResId);

        if(galleryImagesUrls.size()>0) loadImage(imgExample1, galleryImagesUrls.get(0));
        if(galleryImagesUrls.size()>1) loadImage(imgExample2, galleryImagesUrls.get(1));
        if(galleryImagesUrls.size()>2) loadImage(imgExample3, galleryImagesUrls.get(2));
        if(galleryImagesUrls.size()>3) loadImage(imgExample4, galleryImagesUrls.get(3));
        if(galleryImagesUrls.size()>4) loadImage(imgExample5, galleryImagesUrls.get(4));

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
