package snd.orgn.foodnfine.activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.util.NetworkChangeReceiver
import java.io.IOException

class AboutActivity : AppCompatActivity() {

    var networkChangeReceiver: NetworkChangeReceiver? = null
    var network: Boolean? = false
    var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        loadingDialog = LoadingDialog(this)
        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver!!.isNetworkAvailable

        if (network!!)
            getData()
        else {
            val snackbar = Snackbar.make(this.findViewById<View>(android.R.id.content),
                    "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        iv_back.setOnClickListener { V -> super.onBackPressed() }
    }

    private fun getData() {

        loadingDialog!!.showDialog()
        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(ApiInterface::class.java)

        val callCheckSubs = api.about_page("aboutus")
        callCheckSubs.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog!!.hideDialog()
                Log.d("onFailure:", "Error")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    loadingDialog!!.hideDialog()
                    try {
                        assert(response.body() != null)
                        val tmp =  response.body()!!.string()
                        Log.d("!!onResponse_Contactus:", tmp)
                        try {
                            //Toast.makeText(ContactUs.this, temp, Toast.LENGTH_SHORT).show();
                            val jsonObject = JSONObject(tmp)
                            if (jsonObject.getString("result") == "1") {
                                aboutText.text = Html.fromHtml(jsonObject.getJSONObject("cms_detail").getString("description"))
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}
