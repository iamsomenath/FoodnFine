package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemDetailsResponse implements Serializable {

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("rest_grocery")
    @Expose
    private String restGrocery;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getRestGrocery() {
        return restGrocery;
    }

    public void setRestGrocery(String restGrocery) {
        this.restGrocery = restGrocery;
    }
}
