
package com.trimit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarberType implements Parcelable {

    @SerializedName("barber_type_id")
    @Expose
    private String barberTypeId;
    @SerializedName("barber_type_type")
    @Expose
    private String barberTypeType;

    protected BarberType(Parcel in) {
        barberTypeId = in.readString();
        barberTypeType = in.readString();
    }

    public static final Creator<BarberType> CREATOR = new Creator<BarberType>() {
        @Override
        public BarberType createFromParcel(Parcel in) {
            return new BarberType(in);
        }

        @Override
        public BarberType[] newArray(int size) {
            return new BarberType[size];
        }
    };

    public String getBarberTypeId() {
        return barberTypeId;
    }

    public void setBarberTypeId(String barberTypeId) {
        this.barberTypeId = barberTypeId;
    }

    public String getBarberTypeType() {
        return barberTypeType;
    }

    public void setBarberTypeType(String barberTypeType) {
        this.barberTypeType = barberTypeType;
    }

    @Override
    public String toString() {
        return "BarberType{" +
                "barberTypeId='" + barberTypeId + '\'' +
                ", barberTypeType='" + barberTypeType + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barberTypeId);
        dest.writeString(barberTypeType);
    }
}
