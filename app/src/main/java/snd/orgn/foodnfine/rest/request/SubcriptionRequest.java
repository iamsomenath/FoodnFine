package snd.orgn.foodnfine.rest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubcriptionRequest implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("subscription_plan_id")
    @Expose
    private String subscriptionPlanId;
    @SerializedName("subscription_plan")
    @Expose
    private String subscriptionPlan;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("pay_id")
    @Expose
    private String payId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(String subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
