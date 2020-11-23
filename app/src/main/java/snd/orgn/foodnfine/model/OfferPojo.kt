package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OfferPojo : Serializable {

    @SerializedName("coupon_id")
    @Expose
    var couponId: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("validity_start")
    @Expose
    var validityStart: String? = null
    @SerializedName("validity_end")
    @Expose
    var validityEnd: String? = null
    @SerializedName("coupon_status")
    @Expose
    var couponStatus: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("usage_limit")
    @Expose
    var usage_limit: String? = null
    @SerializedName("discount_amt")
    @Expose
    var discount_amt: String? = null
    @SerializedName("main_category_name")
    @Expose
    var main_category_name: String? = null
    @SerializedName("shop_name")
    @Expose
    var shop_name: String? = null
    @SerializedName("user_coupon_count")
    @Expose
    var user_coupon_count: String? = null
    @SerializedName("discount_type")
    @Expose
    var discount_type: String? = null
}