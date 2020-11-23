package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_swadesi_details.*
import org.json.JSONObject
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.SliderAdapterExample
import snd.orgn.foodnfine.application.DeliveryEverything
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackSwadesiDetails
import snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.other.NetworkHelper
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.rest.request.AddToCartRequest
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.view_model.ActivityViewModel.SwadesiDetailsViewModel

class SwadesiDetailsActivity : BaseActivity(), CallbackSwadesiDetails {

    lateinit var loadingDialog: LoadingDialog
    lateinit var viewModel: SwadesiDetailsViewModel
    lateinit var imageList: ArrayList<String>
    lateinit var sessionManager : SessionManager

    override fun initFields() {
        initViewModel()
        loadingDialog = LoadingDialog(this)
        loadingDialog.showDialog()
        //Toast.makeText(this, intent.getStringExtra("product_id")!!, Toast.LENGTH_SHORT).show()
        viewModel.getSwadesiProductDetails(intent.getStringExtra("product_id")!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swadesi_details)
        sessionManager = SessionManager(applicationContext)
        hideStatusBarcolor()
        initFields()
    }

    override fun setupOnClick() {
        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SwadesiDetailsViewModel::class.java)
        viewModel.setCallback(this)
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(getdetails: String) {
        loadingDialog.hideDialog()
        //Log.d("RESPONSE", getdetails)
        val jsonObject = JSONObject(getdetails)
        tvProductname.text = jsonObject.getString("product_name")
        imageList = ArrayList()
        try {
            val jsonArray = jsonObject.getJSONArray("multipleimage")
            for (i in 0 until jsonArray.length())
                imageList.add(jsonArray.getJSONObject(i).getString("product_image"))
        } catch (e: Exception) {
            imageList.add(jsonObject.getString("product_image"))
        }
        val sliderView = findViewById<SliderView>(R.id.imageSlider)
        val adapter = SliderAdapterExample(this, imageList)
        sliderView.sliderAdapter = adapter

        sliderView.setIndicatorAnimation(IndicatorAnimations.SWAP)
        // set indicator animation by using
        // SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or
        // SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.scrollTimeInSec = 10 //set scroll delay in seconds :
        sliderView.startAutoCycle()

        brand.text = "Brand: " + jsonObject.getString("company_name")
        productname.text = jsonObject.getString("product_name")
        desc.text = jsonObject.getString("product_desc")
        price2.text = "₹" + jsonObject.getString("offer_price")
        price.text = "₹" + jsonObject.getString("price")
        price.paintFlags = price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        weight.text = jsonObject.getString("weight") + " " + jsonObject.getString("unit")

        // currently not required
        buy_now.setOnClickListener {

            val intent = Intent(this@SwadesiDetailsActivity, PaymentSwadesiProduct::class.java)
            intent.putExtra("userId", DeliveryEverything.getAppSharedPreference().userId)
            intent.putExtra("payMode", "ONLINE")
            intent.putExtra("payId", "")
            intent.putExtra("payStatus", "")
            intent.putExtra("totalPrice", jsonObject.getString("price"))
            intent.putExtra("delivarAdd", DeliveryEverything.getAppSharedPreference().currentLocation)
            intent.putExtra("remark", "")
            intent.putExtra("orderType", "Swadesi Product")
            intent.putExtra("restGrocery", "Swadesi")

            intent.putExtra("product_name", jsonObject.getString("product_name"))
            intent.putExtra("qty", "1")
            intent.putExtra("price", jsonObject.getString("price"))
            intent.putExtra("totalPrice", jsonObject.getString("offer_price"))
            intent.putExtra("product_desc", jsonObject.getString("product_desc"))
            intent.putExtra("weight", jsonObject.getString("weight"))
            intent.putExtra("unit", jsonObject.getString("unit"))
            intent.putExtra("cat_id", jsonObject.getString("product_id"))
            intent.putExtra("product_id", jsonObject.getString("product_id"))
            startActivity(intent)

            /*val intent = Intent(this@SwadesiDetailsActivity, ConfirmOrderActivity::class.java)
            intent.putExtra(INTENT_STRING_ORDER_TYPE, "SWADESI")
            intent.putExtra(INTENT_STRING_CART_DETAIL, responseCart as Serializable)
            startActivity(intent)*/
        }
        add_to_cart.setOnClickListener {
            //Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            val request = AddToCartRequest()
            request.userId = DeliveryEverything.getAppSharedPreference().userId
            request.pid = jsonObject.getString("product_id")
            request.qty = "1"
            request.devKey = DeliveryEverything.getAppSharedPreference().devKey
            //request.orderType = "Swadesi Product"
            request.price = jsonObject.getString("offer_price").toDouble().toInt()
            //request.restGrocery = "2"
            when {
                sessionManager.keyOrderType == "5" -> request.orderType = "Swadesi Product"
                sessionManager.keyOrderType == "6" -> request.orderType = "Medicine Product"
                sessionManager.keyOrderType == "7" -> request.orderType = "Electronic Product"
            }
            makeAddToCartRequest(request)

            // for swadesi product -> input - order_type :Swadesi Product, rest_grocery:5
        }
        iv_back.setOnClickListener {
            finish()
        }
    }

    fun getAPIInterface(): ApiInterface {
        var apiInterface: ApiInterface? = null
        if (apiInterface == null) {
            apiInterface = NetworkHelper.getClient().create(ApiInterface::class.java)
        }
        return apiInterface!!
    }

    @SuppressLint("CheckResult")
    private fun makeAddToCartRequest(request: AddToCartRequest) {

        val userResponseObservable = getAPIInterface().add_to_cart(request.userId,
                request.pid, request.qty, request.devKey, request.orderType, request.price, sessionManager.keyOrderType)
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restResponse ->
                    if (restResponse.responsesss == 1) {
                        DeliveryEverything.getAppSharedPreference().itemQuantity = "1"
                        Toast.makeText(this@SwadesiDetailsActivity, "Successfully added!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Snackbar.make(iv_back, restResponse.success, Snackbar.LENGTH_LONG).show()
                    }
                }, { e ->
                    Snackbar.make(iv_back, NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
                })
    }

    override fun onError(message: String) {
        loadingDialog.hideDialog()
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    override fun onNetworkError(message: String) {
        loadingDialog.hideDialog()
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}
