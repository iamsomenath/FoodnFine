package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestResponseCart implements Serializable {

    @SerializedName("sum_price")
    @Expose
    private Integer sumPrice;
    @SerializedName("sumcart_count")
    @Expose
    private Integer sumcartCount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("rest_grocery")
    @Expose
    private String restGrocery;
    @SerializedName("cart_data")
    @Expose
    private List<CartDatum> cartData;

    public String getRestGrocery() {
        return restGrocery;
    }

    public void setRestGrocery(String restGrocery) {
        this.restGrocery = restGrocery;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Integer getSumcartCount() {
        return sumcartCount;
    }

    public void setSumcartCount(Integer sumcartCount) {
        this.sumcartCount = sumcartCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CartDatum> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
    }
}
