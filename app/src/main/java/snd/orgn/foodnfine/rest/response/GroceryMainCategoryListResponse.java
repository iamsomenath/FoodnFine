package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllGrocerycategory;

public class GroceryMainCategoryListResponse implements Serializable {

    @SerializedName("all_grocerycategory")
    @Expose
    private List<AllGrocerycategory> allGrocerycategory;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("msg")
    @Expose
    private String msg;

    public List<AllGrocerycategory> getAllGrocerycategory() {
        return allGrocerycategory;
    }

    public void setAllGrocerycategory(List<AllGrocerycategory> allGrocerycategory) {
        this.allGrocerycategory = allGrocerycategory;
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
