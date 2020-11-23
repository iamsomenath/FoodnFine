package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GroceryRestResponse implements Serializable {
    
    @SerializedName("all_grocery")
    @Expose
    private List<AllGrocery> allGrocery;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<AllGrocery> getAllGrocery() {
        return allGrocery;
    }

    public void setAllGrocery(List<AllGrocery> allGrocery) {
        this.allGrocery = allGrocery;
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
