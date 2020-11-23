package snd.orgn.foodnfine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

internal class SearchResponse : Serializable {

    @SerializedName("product_list")
    @Expose
    val product_list: ArrayList<SearchResponseListPojo>? = null
    @SerializedName("status")
    @Expose
    val status: Boolean? = null
    @SerializedName("msg")
    @Expose
    val msg: String? = null
}


