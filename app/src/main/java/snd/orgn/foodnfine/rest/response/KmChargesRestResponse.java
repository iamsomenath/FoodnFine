package snd.orgn.foodnfine.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KmChargesRestResponse implements Serializable {
    /*@SerializedName("1_km")
    @Expose
    private String _1Km;
    @SerializedName("greater_1km_less_5km")
    @Expose
    private String greater1kmLess5km;
    @SerializedName("greater_5km_less_10km")
    @Expose
    private String greater5kmLess10km;
    @SerializedName("greater_than_10")
    @Expose
    private String greater_than_10;
    @SerializedName("success")
    @Expose
    private String success;

    public String get_1Km() {
        return _1Km;
    }

    public String getGreater1kmLess5km() {
        return greater1kmLess5km;
    }

    public String getGreater5kmLess10km() {
        return greater5kmLess10km;
    }

    public String getGreater_than_10() {
        return greater_than_10;
    }

    public String getSuccess() {
        return success;
    }*/


    @SerializedName("fixed_cost")
    @Expose
    private String fixed_cost;
    @SerializedName("per_kilometer")
    @Expose
    private String per_kilometer;
    @SerializedName("success")
    @Expose
    private String success;

    public String getFixed_cost() {
        return fixed_cost;
    }

    public void setFixed_cost(String fixed_cost) {
        this.fixed_cost = fixed_cost;
    }

    public String getPer_kilometer() {
        return per_kilometer;
    }

    public void setPer_kilometer(String per_kilometer) {
        this.per_kilometer = per_kilometer;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
