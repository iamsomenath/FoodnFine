package snd.orgn.foodnfine.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class SwadesiRestResponse : Serializable {

    @SerializedName("result")
    @Expose
    val result: String? = null

    @SerializedName("message")
    @Expose
    val message: String? = null

    @SerializedName("product")
    @Expose
    val product: String? = null

}
