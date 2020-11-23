package snd.orgn.foodnfine.rest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddToCartRequest implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pid")
    @Expose
    private String pid;

    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("dev_key")
    @Expose
    private String devKey;

    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("rest_grocery")
    @Expose
    private String restGrocery;
    @SerializedName("price")
    @Expose
    private Integer price;


    public String getRestGrocery() {
        return restGrocery;
    }

    public void setRestGrocery(String restGrocery) {
        this.restGrocery = restGrocery;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
