
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarberType {

    @SerializedName("barber_type_id")
    @Expose
    private String barberTypeId;
    @SerializedName("barber_type_type")
    @Expose
    private String barberTypeType;

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
}
