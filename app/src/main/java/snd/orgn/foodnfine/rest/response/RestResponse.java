package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestResponse implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("stat")
    @Expose
    private String stat;

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("charges")
    @Expose
    private String charges;

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
