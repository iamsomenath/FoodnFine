package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_address_toolbar.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.SwadesiMainAdapter
import snd.orgn.foodnfine.application.DeliveryEverything
import snd.orgn.foodnfine.bottomSheetFragment.BottomSheetSelectItemFragment
import snd.orgn.foodnfine.callbacks.CallbackDeleteCartResponse
import snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_CART_DETAIL
import snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE
import snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.other.BadgeDrawable
import snd.orgn.foodnfine.helper.other.NetworkHelper
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsList
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsPojo
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.rest.request.UserRequest
import snd.orgn.foodnfine.util.NetworkChangeReceiver
import java.io.IOException
import java.io.Serializable
import java.util.*

class SwadesiProductActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, CallbackDeleteCartResponse {

    lateinit var arrayList: ArrayList<HouseholdEssentialsItemsList>
    internal var uid: String? = null

    lateinit var sessionManager: SessionManager
    internal var data = HashMap<String, String>()

    lateinit var networkChangeReceiver: NetworkChangeReceiver
    var network: Boolean = false

    lateinit var mainrecyleView: RecyclerView

    private var menu: Menu? = null
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var adapter: SwadesiMainAdapter

    private var bottomSheetSelectItemFragment: BottomSheetSelectItemFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swasdesiproduct)

        val toolbar = findViewById<View>(R.id.custom_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.left_arrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val toolbar_title = findViewById<View>(R.id.tv_custom_toolbar_title) as TextView
        if (intent.getStringExtra("type") == "SWADESI")
            toolbar_title.text = "Swadesi Products"
        else
            toolbar_title.text = intent.getStringExtra("shop_name")

        tv_address.text = DeliveryEverything.getAppSharedPreference().currentLocation

        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)

        sessionManager = SessionManager(applicationContext)
        //Retriving Data
        //data = sessionManager.details
        uid = DeliveryEverything.getAppSharedPreference().userId

        mainrecyleView = findViewById<RecyclerView>(R.id.rv_recyclerViewmainList)

        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver.isNetworkAvailable
        if (!network) {
            Snackbar.make(findViewById<View>(android.R.id.content),
                    NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
        } else {
            DisplayData()
        }

        //Toast.makeText(this@SwadesiProductActivity, "Test", Toast.LENGTH_SHORT).show()
        initBottomSheets()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Log.d("TEST!!!", "TEST2" + mycartdata.size());
        this.menu = menu
        menuInflater.inflate(R.menu.menu_two, menu)
        val item1 = menu.findItem(R.id.menu_main2_shopping_cart)
        //getCartCount(item1);
        val iconBitmap = item1.icon as BitmapDrawable
        val iconLayer = LayerDrawable(arrayOf<Drawable>(iconBitmap))
        setBadgeCount(this@SwadesiProductActivity, iconLayer, data[SessionManager.COUNT_SESSION])
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main2_shopping_cart -> {
                val userRequest = UserRequest()
                userRequest.userId = DeliveryEverything.getAppSharedPreference().userId
                when {
                    sessionManager.keyOrderType == "5" -> userRequest.orderType = "Swadesi Product"
                    sessionManager.keyOrderType == "6" -> userRequest.orderType = "Medicine Product"
                    sessionManager.keyOrderType == "7" -> userRequest.orderType = "Electronic Product"
                }
                makeCartDetailsRequest(userRequest)
                return true
            }
            android.R.id.home -> {
                if (DeliveryEverything.getAppSharedPreference().itemQuantity == "") {
                    super.onBackPressed()
                    overridePendingTransition(R.anim.right_in, R.anim.push_left_out)
                    finish()
                } else {
                    showBottomSheet()
                }
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("CheckResult")
    private fun DisplayData() {

        //final LoadingDialog loadingDialog = new LoadingDialog(this);
        swipeRefreshLayout.isRefreshing = true
        //loadingDialog.showDialog();
        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(ApiInterface::class.java)

        val call: Call<ResponseBody>
        call = if (intent.getStringExtra("type") == "SWADESI")
            api.household_essentials(DeliveryEverything.getAppSharedPreference().userId)
        else
            api.swadeshi_product(intent.getStringExtra("shop_id"), DeliveryEverything.getAppSharedPreference().userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                arrayList = ArrayList()
                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    //Log.d("RESPONSE", jsonObject.toString())
                    if (jsonObject.getString("result") == "1") {
                        val jsonArray = JSONArray(jsonObject.getString("product"))
                        for (i in 0 until jsonArray.length()) {
                            val newjsonObject = jsonArray.getJSONObject(i)
                            val obj = HouseholdEssentialsItemsList()
                            obj.category_id = newjsonObject.getString("category_id")

                            if (intent.getStringExtra("type") == "SWADESI") {
                                obj.product_sub_category_id = newjsonObject.getString("product_sub_category_id")
                                obj.subcategory_name = newjsonObject.getString("subcategory_name")
                            } else {
                                obj.product_sub_category_id = newjsonObject.getString("product_category_id")
                                obj.subcategory_name = newjsonObject.getString("category_name")
                            }

                            val jsonArray2 = newjsonObject.getJSONArray("productlist")

                            val arrayList2 = ArrayList<HouseholdEssentialsItemsPojo>()
                            for (j in 0 until jsonArray2.length()) {
                                val jsonObject2 = jsonArray2.getJSONObject(j)
                                val obj2 = HouseholdEssentialsItemsPojo()
                                obj2.product_id = jsonObject2.getString("product_id")
                                obj2.cat_id = jsonObject2.getString("cat_id")
                                obj2.product_sub_category_id = jsonObject2.getString("product_sub_category_id")
                                obj2.product_name = jsonObject2.getString("product_name")
                                obj2.product_image = jsonObject2.getString("product_image")
                                obj2.price = jsonObject2.getString("price")
                                obj2.offer_price = jsonObject2.getString("offer_price")
                                obj2.company_name = jsonObject2.getString("company_name")
                                obj2.weight = jsonObject2.getString("weight")
                                obj2.unit = jsonObject2.getString("unit")
                                arrayList2.add(obj2)
                            }
                            obj.productlist = arrayList2
                            arrayList.add(obj)
                        }
                        initRecyclerviewmaintList();
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //Log.d(TAG, "onFailure: " + t.getMessage());
                val snackbar = Snackbar.make(findViewById<View>(android.R.id.content),
                        "\u058D Something Wrong! Please try Again......", Snackbar.LENGTH_LONG)
                snackbar.show()
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    fun getAPIInterface(): ApiInterface {
        var apiInterface: ApiInterface? = null
        if (apiInterface == null) {
            apiInterface = NetworkHelper.getClient().create(ApiInterface::class.java)
        }
        return apiInterface!!
    }

    @SuppressLint("CheckResult")
    private fun makeCartDetailsRequest(request: UserRequest) {
        //val userResponseObservable = apiInterface!!.getCartDetails(request.userId, request.orderType)
        val userResponseObservable = getAPIInterface().getCartDetails(request.userId, request.orderType)
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restResponse ->
                    if (restResponse.status == 1) {

                        val cartDetails = restResponse
                        DeliveryEverything.getAppSharedPreference().itemQuantity = cartDetails.sumcartCount!!.toString()
                        DeliveryEverything.getAppSharedPreference().itemPrice = cartDetails.sumPrice!!.toString()

                        var type= ""
                        when {
                            sessionManager.keyOrderType == "5" -> type = "Swadesi Product"
                            sessionManager.keyOrderType == "6" -> type = "Medicine Product"
                            sessionManager.keyOrderType == "7" -> type = "Electronic Product"
                        }
                        val intent = Intent(this@SwadesiProductActivity, ConfirmOrderActivity::class.java)
                        intent.putExtra(INTENT_STRING_ORDER_TYPE, type)
                        intent.putExtra(INTENT_STRING_CART_DETAIL, cartDetails as Serializable)
                        startActivity(intent)

                    } else {
                        Snackbar.make(findViewById<View>(android.R.id.content), restResponse.msg, Snackbar.LENGTH_LONG).show()
                    }
                }, { e ->
                    Snackbar.make(findViewById<View>(android.R.id.content), "Your cart is empty!", Snackbar.LENGTH_LONG).show()
                })
    }

    private fun initRecyclerviewmaintList() {

        adapter = SwadesiMainAdapter(this, arrayList, menu!!)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainrecyleView.layoutManager = layoutManager
        mainrecyleView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        if (network)
            DisplayData()
        else {
            Snackbar.make(findViewById<View>(android.R.id.content), NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initBottomSheets() {
        bottomSheetSelectItemFragment = BottomSheetSelectItemFragment()
        bottomSheetSelectItemFragment!!.setCallback(this)
    }

    private fun showBottomSheet() {
        bottomSheetSelectItemFragment!!.show(supportFragmentManager, bottomSheetSelectItemFragment!!.tag)
    }

    override fun onSucessDataDelete() {
        super.onBackPressed()
        DeliveryEverything.getAppSharedPreference().itemQuantity = ""
        overridePendingTransition(R.anim.right_in, R.anim.push_left_out)
        finish()
    }

    override fun onBackPressed() {
        if (DeliveryEverything.getAppSharedPreference().itemQuantity == "") {
            super.onBackPressed()
            overridePendingTransition(R.anim.right_in, R.anim.push_left_out)
            finish()
        } else {
            showBottomSheet()
        }
    }

    companion object {

        fun setBadgeCount(context: SwadesiProductActivity, icon: LayerDrawable, count: String?) {

            val badge: BadgeDrawable

            // Reuse drawable if possible
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            badge = if (reuse != null && reuse is BadgeDrawable) {
                reuse
            } else {
                BadgeDrawable(context)
            }

            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
    }
}