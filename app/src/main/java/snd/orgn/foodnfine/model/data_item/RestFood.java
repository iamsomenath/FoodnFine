package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestFood implements Serializable {

    @SerializedName("food_id")
    @Expose
    private String foodId;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("submenu_desc")
    @Expose
    private String submenuDesc;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("service_for")
    @Expose
    private String serviceFor;
    @SerializedName("base_price")
    @Expose
    private String basePrice;


    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getSubmenuDesc() {
        return submenuDesc;
    }

    public void setSubmenuDesc(String submenuDesc) {
        this.submenuDesc = submenuDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceFor() {
        return serviceFor;
    }

    public void setServiceFor(String serviceFor) {
        this.serviceFor = serviceFor;
    }
}
