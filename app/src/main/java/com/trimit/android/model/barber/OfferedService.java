
package com.trimit.android.model.barber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedService {

    @SerializedName("offered_service_id")
    @Expose
    private Integer offeredServiceId;
    @SerializedName("offered_service_name")
    @Expose
    private String offeredServiceName;
    @SerializedName("offered_service_price")
    @Expose
    private String offeredServicePrice;

    public Integer getOfferedServiceId() {
        return offeredServiceId;
    }

    public void setOfferedServiceId(Integer offeredServiceId) {
        this.offeredServiceId = offeredServiceId;
    }

    public String getOfferedServiceName() {
        return offeredServiceName;
    }

    public void setOfferedServiceName(String offeredServiceName) {
        this.offeredServiceName = offeredServiceName;
    }

    public String getOfferedServicePrice() {
        return offeredServicePrice;
    }

    public void setOfferedServicePrice(String offeredServicePrice) {
        this.offeredServicePrice = offeredServicePrice;
    }

}
