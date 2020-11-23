package snd.orgn.foodnfine.model.data_item

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class HouseholdEssentialsItemsList : Serializable {

    @SerializedName("productlist")
    @Expose
    var productlist: List<HouseholdEssentialsItemsPojo>? = null
    @SerializedName("category_id")
    @Expose
    var category_id: String? = null
    @SerializedName("product_sub_category_id")
    @Expose
    var product_sub_category_id: String? = null
    @SerializedName("subcategory_name")
    @Expose
    var subcategory_name: String? = null
}
