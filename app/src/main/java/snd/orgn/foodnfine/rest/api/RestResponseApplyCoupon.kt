package snd.orgn.foodnfine.rest.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.io.Serializable

class RestResponseApplyCoupon : Serializable {
    @SerializedName("result")
    @Expose
    var result: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("coupon_detail")
    @Expose
    var coupon_detail: JSONObject? = null

   /* @SerializedName("used_coupon_detail")
    @Expose
    var used_coupon_detail: String? = null*/
}