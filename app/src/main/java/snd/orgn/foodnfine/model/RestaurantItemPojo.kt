package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RestaurantItemPojo : Serializable {

    @SerializedName("food_submenu_id")
    @Expose
    var food_submenu_id: String? = null
    @SerializedName("menuid")
    @Expose
    var menuid: String? = null
    @SerializedName("submenu_name")
    @Expose
    var submenu_name: String? = null
    @SerializedName("submenu_desc")
    @Expose
    var submenu_desc: String? = null
    @SerializedName("restaurant_id")
    @Expose
    var restaurant_id: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("product_image")
    @Expose
    var product_image: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
}