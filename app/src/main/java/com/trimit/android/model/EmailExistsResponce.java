package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by artem on 4/19/17.
 */

public class EmailExistsResponce extends Responce {
    @SerializedName("email_exists")
    @Expose
    private Boolean emailExists;

    @Override
    public String toString() {
        return "EmailExistsResponce{" +
                "emailExists=" + emailExists +
                ", success=" + success +
                '}';
    }

    public Boolean getEmailExists() {
        return emailExists;
    }

    public void setEmailExists(Boolean emailExists) {
        this.emailExists = emailExists;
    }


}
