package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddToCartRestResponse implements Serializable {
    @SerializedName("responsesss")
    @Expose
    private Integer responsesss;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("carttotal")
    @Expose
    private String carttotal;
    @SerializedName("success_cart_count")
    @Expose
    private String successCartCount;
    @SerializedName("count_response")
    @Expose
    private Integer countResponse;

    public Integer getResponsesss() {
        return responsesss;
    }

    public void setResponsesss(Integer responsesss) {
        this.responsesss = responsesss;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCarttotal() {
        return carttotal;
    }

    public void setCarttotal(String carttotal) {
        this.carttotal = carttotal;
    }

    public String getSuccessCartCount() {
        return successCartCount;
    }

    public void setSuccessCartCount(String successCartCount) {
        this.successCartCount = successCartCount;
    }

    public Integer getCountResponse() {
        return countResponse;
    }

    public void setCountResponse(Integer countResponse) {
        this.countResponse = countResponse;
    }
}
