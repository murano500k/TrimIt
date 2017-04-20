package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by artem on 4/19/17.
 */

public class ResetPasswordResponce extends Responce {
    @SerializedName("reset_password")
    @Expose
    private Boolean reset_password;

    @Override
    public String toString() {
        return "EmailExistsResponce{" +
                "emailExists=" + reset_password +
                ", success=" + success +
                '}';
    }

    public Boolean getReset_password() {
        return reset_password;
    }

    public void setReset_password(Boolean reset_password) {
        this.reset_password = reset_password;
    }
}
