package snd.orgn.foodnfine.rest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;

public class PlaceOrderRequest implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("order_details")
    @Expose
    private JSONArray orderDetails;
    @SerializedName("pay_mode")
    @Expose
    private String payMode;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("pay_status")
    @Expose
    private String payStatus;
    @SerializedName("pay_id")
    @Expose
    private String payId;
    @SerializedName("delivar_add")
    @Expose
    private String delivarAdd;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("rest_grocery")
    @Expose
    private String restGrocery;
    @SerializedName("rest_grocery_id")
    @Expose
    private String restGroceryid;

    @SerializedName("delivery_charge")
    @Expose
    private String delivery_charge;
    @SerializedName("cancellation_charge")
    @Expose
    private String cancellation_charge;
    @SerializedName("discount_amount")
    @Expose
    private String discount_amount;
    @SerializedName("copoun_type")
    @Expose
    private String copoun_type;
    @SerializedName("copoun_code")
    @Expose
    private String copoun_code;
    @SerializedName("coupon_category")
    @Expose
    private String coupon_category;
    @SerializedName("coupon_id")
    @Expose
    private String coupon_id;
    @SerializedName("order_actual_amount")
    @Expose
    private String order_actual_amount;
    @SerializedName("remark_type")
    @Expose
    private String remark_type;
    @SerializedName("total_product_price")
    @Expose
    private String total_product_price;

    public String getRestGrocery() {
        return restGrocery;
    }

    public void setRestGrocery(String restGrocery) {
        this.restGrocery = restGrocery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSONArray getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(JSONArray orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getDelivarAdd() {
        return delivarAdd;
    }

    public void setDelivarAdd(String delivarAdd) {
        this.delivarAdd = delivarAdd;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRestGroceryid() {
        return restGroceryid;
    }

    public void setRestGroceryid(String restGroceryid) {
        this.restGroceryid = restGroceryid;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getCancellation_charge() {
        return cancellation_charge;
    }

    public void setCancellation_charge(String cancellation_charge) {
        this.cancellation_charge = cancellation_charge;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getCopoun_type() {
        return copoun_type;
    }

    public void setCopoun_type(String copoun_type) {
        this.copoun_type = copoun_type;
    }

    public String getCopoun_code() {
        return copoun_code;
    }

    public void setCopoun_code(String copoun_code) {
        this.copoun_code = copoun_code;
    }

    public String getCoupon_category() {
        return coupon_category;
    }

    public void setCoupon_category(String coupon_category) {
        this.coupon_category = coupon_category;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getOrder_actual_amount() {
        return order_actual_amount;
    }

    public void setOrder_actual_amount(String order_actual_amount) {
        this.order_actual_amount = order_actual_amount;
    }

    public String getRemark_type() {
        return remark_type;
    }

    public void setRemark_type(String remark_type) {
        this.remark_type = remark_type;
    }

    public String getTotal_product_price() {
        return total_product_price;
    }

    public void setTotal_product_price(String total_product_price) {
        this.total_product_price = total_product_price;
    }
}