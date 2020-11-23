package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestrurentRestResponse implements Serializable {
    @SerializedName("all_restaurant")
    @Expose
    private List<AllRestaurant> allRestaurant;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<AllRestaurant> getAllRestaurant() {
        return allRestaurant;
    }

    public void setAllRestaurant(List<AllRestaurant> allRestaurant) {
        this.allRestaurant = allRestaurant;
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
