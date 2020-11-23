package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodCategory implements Serializable {
    @SerializedName("food_cat_menu_id")
    @Expose
    private String foodCatMenuId;
    @SerializedName("menu_category_name")
    @Expose
    private String menuCategoryName;


    public String getFoodCatMenuId() {
        return foodCatMenuId;
    }

    public void setFoodCatMenuId(String foodCatMenuId) {
        this.foodCatMenuId = foodCatMenuId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }
}
