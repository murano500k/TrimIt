
package com.trimit.android.model.createuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponceCreateUser {

    @SerializedName("user")
    @Expose
    private CreatedUser user;
    @SerializedName("success")
    @Expose
    private boolean success;

    public CreatedUser getUser() {
        return user;
    }

    public void setUser(CreatedUser user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponceCreateUser{" +
                "createdUser=" + user +
                ", success=" + success +
                '}';
    }
}
