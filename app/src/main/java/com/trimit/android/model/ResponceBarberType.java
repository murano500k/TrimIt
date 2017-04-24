
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponceBarberType {

    @SerializedName("barber_type")
    @Expose
    private List<BarberType> barberType = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<BarberType> getBarberType() {
        return barberType;
    }

    public void setBarberType(List<BarberType> barberType) {
        this.barberType = barberType;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponceBarberType{" +
                "barberType=" + barberType +
                ", success=" + success +
                '}';
    }
}
