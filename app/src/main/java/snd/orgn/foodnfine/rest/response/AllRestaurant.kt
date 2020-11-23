package snd.orgn.foodnfine.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class AllRestaurant : Serializable {
    @SerializedName("restaurant_id")
    @Expose
    var restaurantId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("restaurant_add")
    @Expose
    var restaurantAdd: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
}
