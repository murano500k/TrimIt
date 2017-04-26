package com.trimit.android.ui.barber;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.squareup.picasso.Picasso;
import com.trimit.android.R;
import com.trimit.android.model.barber.Barber;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by artem on 4/25/17.
 */

public class BarberAdapter extends RecyclerView.Adapter {
    private List<Barber> mItems;
    private DistanceProvider mDistanceProvider;
    private ItemClickListener mItemClickListener;

    public BarberAdapter(DistanceProvider provider, ItemClickListener listener) {
        this.mItems = new ArrayList<>();
        this.mDistanceProvider=provider;
        this.mItemClickListener=listener;
    }

    public void addItems(List<Barber> list){
        this.mItems =list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barber, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Barber barber = mItems.get(position);
        String name = barber.getAccount().get(0).getFirstName();
        String address = barber.getAddressLine1()+ " "+ barber.getAddressLine2();
        String code = barber.getPostcode();
        vh.textName.setText(name);
        vh.textAddress.setText(address);
        vh.textCode.setText(code);

        String profilePicUrl = barber.getPhoto().getProfilePicture().getUrl();
        if(profilePicUrl!=null)
            Picasso.with(vh.imgProfile.getContext()).load(profilePicUrl).into(vh.imgProfile);
        double lat  = Double.valueOf(barber.getBarbershop().get(0).getLocationLat());
        double lon  = Double.valueOf(barber.getBarbershop().get(0).getLocationLong());
        String miles = "0.0";

        if(mDistanceProvider.getDistance(new LatLng(lat,lon))!=Float.MAX_VALUE){
            miles=String.valueOf(mDistanceProvider.getDistance(new LatLng(lat,lon)));
        }
        vh.textMiles.setText(miles);
        vh.imgStars.setImageResource(getStarsResId(barber.getOverallStars()));
    }

    private int getStarsResId(String starsString){
        int stars = (int) Double.parseDouble(starsString);
        switch (stars){
            case 0:
                return R.drawable.img_stars_0;
            case 1:
                return R.drawable.img_stars_1;
            case 2:
                return R.drawable.img_stars_2;
            case 3:
                return R.drawable.img_stars_3;
            case 4:
                return R.drawable.img_stars_4;
            case 5:
                return R.drawable.img_stars_5;
            default:
                return R.drawable.img_stars_0;
        }
    }
    /*onBindViewHolder: Barber{addressLine2='null', town='City of L', county='L', postcode='XX XX', accountId=188, overallStars='4.0', barberId=31, contactNumber='1111 22222', barbershopId=34, bio='First time android user! Yes
                                                                   it is.', accountNumber='12312399', sortcode='60-00-01', addressLine1='23 Throgmorton street', cashOnly=false, gender='Other', inactive=false, holding=true, account=[com.trimit.android.model.barber.Account@fe51357], barbershop=[Barbershop{barbershopId=34, barbershopTown='City', barbershopCounty='London', barbershopPostcode='SM5 6AN', barbershopName='John and I', locationLat='51.360007', locationLong='-0.166517', barbershopAddressLine1='23 Rastel', barbershopAddressLine2='Avenue'}], photo=Photo{profilePicture=com.trimit.android.model.barber.ProfilePicture@cb7ee44, coverPicture=com.trimit.android.model.barber.CoverPicture@1815b2d, gallery=[com.trimit.android.model.barber.Gallery@fa9b862, com.trimit.android.model.barber.Gallery@f3d8df3, com.trimit.android.model.barber.Gallery@b67aab0, com.trimit.android.model.barber.Gallery@5457d29, com.trimit.android.model.barber.Gallery@16ef8ae]}, barberType=[com.trimit.android.model.barber.BarberType@99164f], available=true, offeredService=[com.trimit.android.model.barber.OfferedService@614c1dc, com.trimit.android.model.barber.OfferedService@60fc2e5]}*/

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textAddress, textCode, textMiles;
        CircleImageView imgProfile;
        ImageView imgStars;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            textName=(TextView) itemView.findViewById(R.id.text_name);
            textAddress=(TextView) itemView.findViewById(R.id.text_address);
            textCode=(TextView) itemView.findViewById(R.id.text_code);
            textMiles=(TextView) itemView.findViewById(R.id.text_miles_value);
            imgProfile=(CircleImageView) itemView.findViewById(R.id.img_profile);
            imgStars=(ImageView) itemView.findViewById(R.id.img_stars);
        }
    }
    interface ItemClickListener{
        void itemClicked(Barber barber);
    }
    interface DistanceProvider{
        float getDistance(LatLng point);
    }
}
