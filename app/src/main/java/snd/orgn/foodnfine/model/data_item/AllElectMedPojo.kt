package snd.orgn.foodnfine.model.data_item

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class AllElectMedPojo : Serializable {

    @SerializedName("shop_id")
    @Expose
    var shop_id: String? = null
    @SerializedName("shop_name")
    @Expose
    var shop_name: String? = null
    @SerializedName("shop_type")
    @Expose
    var shop_type: String? = null
    @SerializedName("delivery_time")
    @Expose
    var delivery_time: String? = null
    @SerializedName("shop_shop_loc")
    @Expose
    var shop_shop_loc: String? = null
    @SerializedName("shop_img")
    @Expose
    var shop_img: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
}
