package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by artem on 4/25/17.
 */

public class LocationData {

    @SerializedName("location_lat")
    @Expose
    private double lat;



    @SerializedName("location_long")
    @Expose
    private double lng;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LocationData(LatLng point) {
        this.lat = point.getLatitude();
        this.lng = point.getLongitude();
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
