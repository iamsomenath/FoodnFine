package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GroceryItemList : Serializable {

    @SerializedName("grocery_menu_list")
    @Expose
    var grocery_menu_list: List<GroceryItemPojo>? = null
    @SerializedName("product_category_id")
    @Expose
    var product_category_id: String? = null
    @SerializedName("category_name")
    @Expose
    var category_name: String? = null
    @SerializedName("category_type")
    @Expose
    var category_type: String? = null
    @SerializedName("grocery_id")
    @Expose
    var grocery_id: String? = null
}