package snd.orgn.foodnfine.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class AllGrocery : Serializable {
    @SerializedName("grocery_id")
    @Expose
    var groceryId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("time")
    @Expose
    var time: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
}
