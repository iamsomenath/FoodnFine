package snd.orgn.foodnfine.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import snd.orgn.foodnfine.model.data_item.AllElectMedPojo

import java.io.Serializable

class ElecMedRestResponse : Serializable {

    @SerializedName("all_shops")
    @Expose
    var all_shops: List<AllElectMedPojo>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null
}