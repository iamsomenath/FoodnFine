package snd.orgn.foodnfine.rest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodRequest implements Serializable {

    @SerializedName("rest_id")
    @Expose
    private String restId;
    @SerializedName("food_catid")
    @Expose
    private String foodCatId;

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoodCatId() {
        return foodCatId;
    }

    public void setFoodCatId(String foodCatId) {
        this.foodCatId = foodCatId;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }
}
