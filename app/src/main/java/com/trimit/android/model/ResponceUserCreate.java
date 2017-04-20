
package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponceUserCreate {

    @SerializedName("user")
    @Expose
    private UserCreateResponce userCreateResponce;
    @SerializedName("success")
    @Expose
    private boolean success;

    public UserCreateResponce getUserCreateResponce() {
        return userCreateResponce;
    }

    public void setUserCreateResponce(UserCreateResponce userCreateResponce) {
        this.userCreateResponce = userCreateResponce;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponceUserCreate{" +
                "userCreateResponce=" + userCreateResponce +
                ", success=" + success +
                '}';
    }
}
