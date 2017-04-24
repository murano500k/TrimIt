
package com.trimit.android.model.getuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponceGetUser {

    @SerializedName("user")
    @Expose
    private List<User> user = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponceGetUser{" +
                "user=" + user +
                ", success=" + success +
                '}';
    }
}
