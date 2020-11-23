package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllSubscriptionCharge implements Serializable {

    @SerializedName("subscription_plan_id")
    @Expose
    private String subscriptionPlanId;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("charges")
    @Expose
    private String charges;

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(String subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }
}
