package snd.orgn.foodnfine.rest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PickupRequest implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pickup_add")
    @Expose
    private String pickupAdd;
    @SerializedName("delivar_add")
    @Expose
    private String delivarAdd;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("charges")
    @Expose
    private String charges;

    @SerializedName("pay_stat")
    @Expose
    private String payStat;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("pay_id")
    @Expose
    private String payId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("remark")
    @Expose
    private String remark;

    @SerializedName("estimate_value")
    @Expose
    private String estimate_value;


    public String getEstimate_value() {
        return estimate_value;
    }

    public void setEstimate_value(String estimate_value) {
        this.estimate_value = estimate_value;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayStat() {
        return payStat;
    }

    public void setPayStat(String payStat) {
        this.payStat = payStat;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickupAdd() {
        return pickupAdd;
    }

    public void setPickupAdd(String pickupAdd) {
        this.pickupAdd = pickupAdd;
    }

    public String getDelivarAdd() {
        return delivarAdd;
    }

    public void setDelivarAdd(String delivarAdd) {
        this.delivarAdd = delivarAdd;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }
}
