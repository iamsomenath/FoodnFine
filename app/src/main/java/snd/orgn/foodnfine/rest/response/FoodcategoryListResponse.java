package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import snd.orgn.foodnfine.model.data_item.FoodCategory;
import snd.orgn.foodnfine.model.data_item.RestFood;

public class FoodcategoryListResponse implements Serializable {

    @SerializedName("food_category")
    @Expose
    private List<FoodCategory> foodCategory;
    @SerializedName("rest_food")
    @Expose
    private List<RestFood> restFood;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<RestFood> getRestFood() {
        return restFood;
    }

    public void setRestFood(List<RestFood> restFood) {
        this.restFood = restFood;
    }

    public List<FoodCategory> getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(List<FoodCategory> foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
