package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllGroceryProduct;

public class GroceryAllProductListResponse implements Serializable {
    @SerializedName("all_product")
    @Expose
    private List<AllGroceryProduct> allProduct;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<AllGroceryProduct> getAllProduct() {
        return allProduct;
    }

    public void setAllProduct(List<AllGroceryProduct> allProduct) {
        this.allProduct = allProduct;
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
