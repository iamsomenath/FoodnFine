package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GroceryItemPojo : Serializable {

    @SerializedName("product_id")
    @Expose
    var product_id: String? = null
    @SerializedName("cat_id")
    @Expose
    var cat_id: String? = null
    @SerializedName("product_name")
    @Expose
    var product_name: String? = null
    @SerializedName("product_desc")
    @Expose
    var product_desc: String? = null
    @SerializedName("grocery_id")
    @Expose
    var grocery_id: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("weight")
    @Expose
    var weight: String? = null
    @SerializedName("unit")
    @Expose
    var unit: String? = null
    @SerializedName("product_image")
    @Expose
    var product_image: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("current_cart_qty")
    @Expose
    var current_cart_qty: String? = null
}