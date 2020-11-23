package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllSubscriptionCharge;

public class SubscriptionResponse implements Serializable {

    @SerializedName("all_subscription_charges")
    @Expose
    private List<AllSubscriptionCharge> allSubscriptionCharges;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private String success;

    public List<AllSubscriptionCharge> getAllSubscriptionCharges() {
        return allSubscriptionCharges;
    }

    public void setAllSubscriptionCharges(List<AllSubscriptionCharge> allSubscriptionCharges) {
        this.allSubscriptionCharges = allSubscriptionCharges;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
