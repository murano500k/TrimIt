
package com.trimit.android.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("loyalty_points")
    @Expose
    private Integer loyaltyPoints;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("barber_type_id")
    @Expose
    private Integer barberTypeId;
    @SerializedName("account")
    @Expose
    private List<Account> account = null;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
