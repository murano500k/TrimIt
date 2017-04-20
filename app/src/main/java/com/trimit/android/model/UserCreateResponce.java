
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreateResponce {

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("account")
    @Expose
    private AccountCreateResponce account;
    @SerializedName("customer")
    @Expose
    private CustomerResponce customer;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AccountCreateResponce getAccount() {
        return account;
    }

    public void setAccount(AccountCreateResponce account) {
        this.account = account;
    }

    public CustomerResponce getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponce customer) {
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
        return "UserCreateResponce{" +
                "userId=" + userId +
                ", account=" + account +
                ", customer=" + customer +
                ", photo=" + photo +
                '}';
    }
}
