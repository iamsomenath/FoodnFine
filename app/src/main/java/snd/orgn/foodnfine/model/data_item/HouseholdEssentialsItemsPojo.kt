package snd.orgn.foodnfine.model.data_item

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HouseholdEssentialsItemsPojo {

    @SerializedName("product_id")
    @Expose
    var product_id: String? = null
    @SerializedName("cat_id")
    @Expose
    var cat_id: String? = null
    @SerializedName("company_name")
    @Expose
    var company_name: String? = null
    @SerializedName("offer_price")
    @Expose
    var offer_price: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("product_image")
    @Expose
    var product_image: String? = null
    @SerializedName("product_name")
    @Expose
    var product_name: String? = null
    @SerializedName("product_sub_category_id")
    @Expose
    var product_sub_category_id: String? = null
    @SerializedName("unit")
    @Expose
    var unit: String? = null
    @SerializedName("weight")
    @Expose
    var weight: String? = null
}
