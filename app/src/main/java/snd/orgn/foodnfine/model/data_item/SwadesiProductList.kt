package snd.orgn.foodnfine.model.data_item

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class SwadesiProductList : Serializable {

    @SerializedName("product")
    @Expose
    val product: List<HouseholdEssentialsItemsList>? = null
    @SerializedName("result")
    @Expose
    val result: String? = null
    @SerializedName("message")
    @Expose
    val message: String? = null
}
