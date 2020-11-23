package snd.orgn.foodnfine.view_model.ActivityViewModel

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.base.BaseViewModel
import snd.orgn.foodnfine.callbacks.CallbackSwadesiDetails
import snd.orgn.foodnfine.constant.ErrorMessageConstant
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.constant.WebConstants
import java.io.IOException

class SwadesiDetailsViewModel : BaseViewModel() {

    internal var apiInterface: ApiInterface = getAPIInterface()
    internal var callback: CallbackSwadesiDetails? =  null

    fun setCallback(callback: CallbackSwadesiDetails) {
        this.callback = callback
    }

    fun getSwadesiProductDetails(productId: String) {
        makeRequest(productId)
    }

    private fun makeRequest(productId: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(ApiInterface::class.java)

        val call = api.swadeshi_pro_detail(productId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val jsonObject = JSONObject(response.body()!!.string())
                try {
                    //Log.d("RESPONSE", jsonObject.toString())
                    if (jsonObject.getString("result") == "1") {
                        callback!!.onSuccess(jsonObject.getJSONObject("product").toString())
                    }else{
                        callback!!.onError(ErrorMessageConstant.ERROR_FETCH_DATA)
                    }
                } catch (e: JSONException) {
                    //e.printStackTrace()
                    callback!!.onError(ErrorMessageConstant.ERROR_MESSAGE)
                } catch (e: IOException) {
                    //e.printStackTrace()
                    callback!!.onError(ErrorMessageConstant.ERROR_MESSAGE)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //Log.d(TAG, "onFailure: " + t.getMessage());
                callback!!.onNetworkError(ErrorMessageConstant.NETWORK_ERROR_MESSAGE)
            }
        })
    }
}