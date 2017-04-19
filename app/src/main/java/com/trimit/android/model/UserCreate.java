
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserCreate {



    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("loyalty_points")
    @Expose
    private Integer loyaltyPoints;

    @SerializedName("barber_type_id")
    @Expose
    private Integer barberTypeId;

    @SerializedName("accounts")
    @Expose
    private List<Account> accounts;

    public UserCreate() {
        this.accounts = new ArrayList<>();
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
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

    public Integer getBarberTypeId() {
        return barberTypeId;
    }

    public void setBarberTypeId(Integer barberTypeId) {
        this.barberTypeId = barberTypeId;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "UserCreate{" +
                "gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", barberTypeId=" + barberTypeId +
                ", accounts=" + accounts +
                '}';
    }
}
