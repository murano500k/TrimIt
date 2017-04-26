
package com.trimit.android.model.barber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barbershop {

    @SerializedName("barbershop_id")
    @Expose
    private Integer barbershopId;
    @SerializedName("barbershop_town")
    @Expose
    private String barbershopTown;
    @SerializedName("barbershop_county")
    @Expose
    private String barbershopCounty;
    @SerializedName("barbershop_postcode")
    @Expose
    private String barbershopPostcode;
    @SerializedName("barbershop_name")
    @Expose
    private String barbershopName;
    @SerializedName("location_lat")
    @Expose
    private String locationLat;
    @SerializedName("location_long")
    @Expose
    private String locationLong;
    @SerializedName("barbershop_address_line_1")
    @Expose
    private String barbershopAddressLine1;
    @SerializedName("barbershop_address_line_2")
    @Expose
    private String barbershopAddressLine2;

    public Integer getBarbershopId() {
        return barbershopId;
    }

    public void setBarbershopId(Integer barbershopId) {
        this.barbershopId = barbershopId;
    }

    public String getBarbershopTown() {
        return barbershopTown;
    }

    public void setBarbershopTown(String barbershopTown) {
        this.barbershopTown = barbershopTown;
    }

    public String getBarbershopCounty() {
        return barbershopCounty;
    }

    public void setBarbershopCounty(String barbershopCounty) {
        this.barbershopCounty = barbershopCounty;
    }

    public String getBarbershopPostcode() {
        return barbershopPostcode;
    }

    public void setBarbershopPostcode(String barbershopPostcode) {
        this.barbershopPostcode = barbershopPostcode;
    }

    public String getBarbershopName() {
        return barbershopName;
    }

    public void setBarbershopName(String barbershopName) {
        this.barbershopName = barbershopName;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getBarbershopAddressLine1() {
        return barbershopAddressLine1;
    }

    public void setBarbershopAddressLine1(String barbershopAddressLine1) {
        this.barbershopAddressLine1 = barbershopAddressLine1;
    }

    public String getBarbershopAddressLine2() {
        return barbershopAddressLine2;
    }

    public void setBarbershopAddressLine2(String barbershopAddressLine2) {
        this.barbershopAddressLine2 = barbershopAddressLine2;
    }

    @Override
    public String toString() {
        return "Barbershop{" +
                "barbershopId=" + barbershopId +
                ", barbershopTown='" + barbershopTown + '\'' +
                ", barbershopCounty='" + barbershopCounty + '\'' +
                ", barbershopPostcode='" + barbershopPostcode + '\'' +
                ", barbershopName='" + barbershopName + '\'' +
                ", locationLat='" + locationLat + '\'' +
                ", locationLong='" + locationLong + '\'' +
                ", barbershopAddressLine1='" + barbershopAddressLine1 + '\'' +
                ", barbershopAddressLine2='" + barbershopAddressLine2 + '\'' +
                '}';
    }
}
