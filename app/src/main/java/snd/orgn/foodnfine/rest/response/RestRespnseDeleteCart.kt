package snd.orgn.foodnfine.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class RestRespnseDeleteCart : Serializable {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null
}
