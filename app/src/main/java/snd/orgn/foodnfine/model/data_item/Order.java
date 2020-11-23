package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_date_time")
    @Expose
    private String orderDateTime;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("pay_id")
    @Expose
    private String payId;
    @SerializedName("delivar_address")
    @Expose
    private String delivarAddress;
    @SerializedName("delivary_date")
    @Expose
    private String delivaryDate;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("estimate_value")
    @Expose
    private String estimateValue;
    @SerializedName("pickup_add")
    @Expose
    private String pickupAdd;

    @SerializedName("order_details")
    @Expose
    private List<ItemDetailsResponse> orderDetails;
    @SerializedName("pay_mode")
    @Expose
    private String payMode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("package")
    @Expose
    private String _package;

    @SerializedName("delivery_charge")
    @Expose
    private String delivery_charge;
    @SerializedName("cancellation_charge")
    @Expose
    private String cancellation_charge;
    @SerializedName("discount_amount")
    @Expose
    private String discount_amount;
    @SerializedName("order_actual_amount")
    @Expose
    private String order_actual_amount;
    @SerializedName("copoun_code")
    @Expose
    private String copoun_code;
    @SerializedName("delivery_fixed_amt")
    @Expose
    private String fixed_cost;
    @SerializedName("total_product_price")
    @Expose
    private String total_product_price;

    public String getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getPayId() {
        return payId;
    }

    public String getDelivarAddress() {
        return delivarAddress;
    }

    public String getDelivaryDate() {
        return delivaryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDistance() {
        return distance;
    }

    public String getEstimateValue() {
        return estimateValue;
    }

    public String getPickupAdd() {
        return pickupAdd;
    }

    public List<ItemDetailsResponse> getOrderDetails() {
        return orderDetails;
    }

    public String getPayMode() {
        return payMode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String get_package() {
        return _package;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public String getCancellation_charge() {
        return cancellation_charge;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public String getOrder_actual_amount() {
        return order_actual_amount;
    }

    public String getCopoun_code() {
        return copoun_code;
    }

    public String getFixed_cost() {
        return fixed_cost;
    }

    public String getTotal_product_price() {
        return total_product_price;
    }

    public void setTotal_product_price(String total_product_price) {
        this.total_product_price = total_product_price;
    }
}
