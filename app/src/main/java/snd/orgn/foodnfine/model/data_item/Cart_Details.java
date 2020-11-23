package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart_Details {


    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("food_id")
    @Expose
    private String foodId;
    @SerializedName("qty")
    @Expose
    private String quantity;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("food_category")
    @Expose
    private String foodCategory;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ingridients")
    @Expose
    private String ingridients;
    @SerializedName("foodtype")
    @Expose
    private String foodtype;
    @SerializedName("main_img")
    @Expose
    private String mainImg;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngridients() {
        return ingridients;
    }

    public void setIngridients(String ingridients) {
        this.ingridients = ingridients;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
