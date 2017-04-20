package com.trimit.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by artem on 4/20/17.
 */

public class Responce {
    @SerializedName("success")
    @Expose
    protected String success;

    public Responce() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Responce{" +
                "success='" + success + '\'' +
                '}';
    }
}
