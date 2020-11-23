package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SearchResponseListPojo : Serializable {

    @SerializedName("product_type")
    @Expose
    var productType: String? = null
    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("product_name")
    @Expose
    var productName: String? = null
    @SerializedName("product_desc")
    @Expose
    var productDesc: String? = null
    @SerializedName("store_id")
    @Expose
    var storeId: String? = null
    @SerializedName("product_price")
    @Expose
    var productPrice: String? = null
    @SerializedName("offer_price")
    @Expose
    var offerPrice: String? = null
    @SerializedName("store_name")
    @Expose
    var store_name: String? = null
}