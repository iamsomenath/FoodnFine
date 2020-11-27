package snd.orgn.foodnfine.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.custom_layout_toolbar.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.OfferAdapter
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.model.OfferPojo
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.util.NetworkChangeReceiver

class OffersActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var loadingDialog: LoadingDialog

    lateinit var networkChangeReceiver: NetworkChangeReceiver
    internal var network: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver.isNetworkAvailable

        loadingDialog = LoadingDialog(this)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager

        recyclerview.setLayoutManager(linearLayoutManager)
        recyclerview.setHasFixedSize(true)

        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)

        iv_back.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.right_in, R.anim.push_left_out)
            finish()
        }
    }

    private fun getCoupons() {

        //loadingDialog.showDialog();
        swipe_refresh_layout.setRefreshing(true)
        var offerList = ArrayList<OfferPojo>()
        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(ApiInterface::class.java)
        val call = api.all_coupon()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    val jsonObject = JSONObject(response.body()!!.string())

                    recyclerview.visibility = View.GONE
                    empty_image.visibility = View.VISIBLE

                    /*if (jsonObject.getString("status") == "1") {
                        val jsonArray = jsonObject.getJSONArray("coupons")
                        val gson = GsonBuilder().create()
                        val groupListType = object : TypeToken<ArrayList<OfferPojo>>() {
                        }.type
                        offerList = gson.fromJson(jsonArray.toString(), groupListType) as ArrayList<OfferPojo>
                        if (offerList.size != 0) {
                            val couponAdapter = OfferAdapter(offerList, this@OffersActivity)
                            recyclerview.adapter = couponAdapter
                        } else {
                            recyclerview.visibility = View.GONE
                            empty_image.visibility = View.VISIBLE
                        }
                    }*/
                }
                //loadingDialog.hideDialog();
                swipe_refresh_layout.setRefreshing(false)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //Log.d(TAG, "onFailure: " + t.getMessage());
                //loadingDialog.hideDialog()
                /*val snackbar = Snackbar.make(recyclerview,
                        "\u058D Something Wrong! Please try Again......", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.getView()
                snackbarView.setBackgroundColor(Color.RED)
                textView.setTextColor(Color.WHITE)
                snackbar.show()*/
                swipe_refresh_layout.setRefreshing(false)
            }
        })
    }

    public override fun onResume() {
        super.onResume()
        if (network!!)
            getCoupons()
        else {
            //startActivity(Intent(this@Offers, NetworkConnection::class.java))
        }
    }

    override fun onRefresh() {
        if (network!!)
            getCoupons()
        else {
            //startActivity(Intent(this@Offers, NetworkConnection::class.java))
        }
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        finish()
    }


    companion object {
        private val TAG = "Offers"
    }
}