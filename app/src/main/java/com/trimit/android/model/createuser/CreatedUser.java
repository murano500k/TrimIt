
package com.trimit.android.model.createuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedUser {

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("account")
    @Expose
    private CreatedAccount account;
    @SerializedName("customer")
    @Expose
    private CreatedCustomer customer;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CreatedAccount getAccount() {
        return account;
    }

    public void setAccount(CreatedAccount account) {
        this.account = account;
    }

    public CreatedCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CreatedCustomer customer) {
        this.customer = customer;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "CreatedUser{" +
                "userId=" + userId +
                ", account=" + account +
                ", customer=" + customer +
                ", photo=" + photo +
                '}';
    }
}
