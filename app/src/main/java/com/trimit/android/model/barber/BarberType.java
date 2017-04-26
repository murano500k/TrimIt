
package com.trimit.android.model.barber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarberType {

    @SerializedName("barber_type_id")
    @Expose
    private Integer barberTypeId;
    @SerializedName("barber_type_type")
    @Expose
    private String barberTypeType;

    public Integer getBarberTypeId() {
        return barberTypeId;
    }

    public void setBarberTypeId(Integer barberTypeId) {
        this.barberTypeId = barberTypeId;
    }

    public String getBarberTypeType() {
        return barberTypeType;
    }

    public void setBarberTypeType(String barberTypeType) {
        this.barberTypeType = barberTypeType;
    }

}
