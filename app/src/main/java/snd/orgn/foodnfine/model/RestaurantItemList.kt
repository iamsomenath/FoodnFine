package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RestaurantItemList : Serializable {

    @SerializedName("food_menu_list")
    @Expose
    var food_menu_list: List<RestaurantItemPojo>? = null
    @SerializedName("category_id")
    @Expose
    var category_id: String? = null
    @SerializedName("category_name")
    @Expose
    var category_name: String? = null
    @SerializedName("resturent_id")
    @Expose
    var resturent_id: String? = null
}