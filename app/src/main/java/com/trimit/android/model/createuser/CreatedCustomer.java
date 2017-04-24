
package com.trimit.android.model.createuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedCustomer {

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
