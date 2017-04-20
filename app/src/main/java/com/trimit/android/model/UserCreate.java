
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreate {



    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("loyalty_points")
    @Expose
    private String loyaltyPoints;

    @SerializedName("barber_type_id")
    @Expose
    private String barberTypeId;

    @SerializedName("account")
    @Expose
    private AccountCreate account;


    public String getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(String loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBarberTypeId() {
        return barberTypeId;
    }

    public void setBarberTypeId(String barberTypeId) {
        this.barberTypeId = barberTypeId;
    }

    public void setAccount(AccountCreate account){
        this.account=account;
    }

    public AccountCreate getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "UserCreate{" +
                "gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", barberTypeId=" + barberTypeId +
                ", account=" + account +
                '}';
    }
}
