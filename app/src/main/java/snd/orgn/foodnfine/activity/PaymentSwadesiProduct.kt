package snd.orgn.foodnfine.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment_app.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.constant.ErrorMessageConstant
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.other.FontChanger
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.rest.request.PlaceOrderRequest
import snd.orgn.foodnfine.rest.response.RestResponsePlaceOrder
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.util.NetworkChangeReceiver
import java.io.IOException

// currently not required
class PaymentSwadesiProduct : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var networkChangeReceiver: NetworkChangeReceiver
    internal var network: Boolean? = false

    internal var payment = "null"
    private var rg_payment_option: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_app)
        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver.isNetworkAvailable
        rg_payment_option = findViewById(R.id.rg_payment)

        if (network!!) {

            sessionManager = SessionManager(applicationContext)

            val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbar)
            supportActionBar!!.title = ""
            val upArrow = resources.getDrawable(R.drawable.ic_left_back)
            upArrow.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            supportActionBar!!.setHomeAsUpIndicator(upArrow)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar.setTitleTextColor(Color.WHITE)
            val textView = toolbar.findViewById<TextView>(R.id.checkout_txt)
            textView.typeface = Typeface.createFromAsset(assets, "ProximaNovaLight.otf")

            val fontChanger = FontChanger(assets, "ProximaNovaLight.otf")
            fontChanger.replaceFonts(this.findViewById<View>(android.R.id.content) as ViewGroup)

        } else {

        }

        btn_submit_payment_home.setOnClickListener {
            val selectedId_meal = rg_payment_option!!.checkedRadioButtonId
            payment = when (selectedId_meal) {

                R.id.rb_payment_option_wallet -> "wallet"

                R.id.rb_payment_option_op -> "op"

                R.id.rb_payment_option_cod -> "cod"

                else -> "null"
            }

            if (payment.equals("wallet", ignoreCase = true)) {
                sessionManager.successCall("payment_wallet")
                sessionManager.setPayType("Wallet")

            } else if (payment == "cod") {

                sessionManager.successCall("payment_cod")
                sessionManager.setPayType("COD")

                confirmOrder(1)

            } else if (payment == "op") {
                sessionManager.successCall("payment_online")

                confirmOrder(2)
            }
        }
    }

    private fun makeRequestPlaceOrder() {

        val reqArr = JSONArray()
        try {
            val reqObj2 = JSONObject()
            reqObj2.put("product_name", intent.getStringExtra("product_name"))
            reqObj2.put("product_id", intent.getStringExtra("product_id"))
            reqObj2.put("qty", intent.getStringExtra("qty"))
            //reqObj2.put("total_price", intent.getStringExtra("totalPrice"))
            reqObj2.put("total_price", (Integer.parseInt(intent.getStringExtra("qty")) *
                    java.lang.Double.parseDouble(intent.getStringExtra("price"))).toString())
            //reqObj2.put("price", intent.getStringExtra("price"))
            reqObj2.put("price", intent.getStringExtra("price"))
            reqObj2.put("product_desc", intent.getStringExtra("product_desc"))
            reqObj2.put("weight", intent.getStringExtra("weight"))
            reqObj2.put("unit", intent.getStringExtra("unit"))
            reqObj2.put("cat_id", intent.getStringExtra("cat_id"))
            reqObj2.put("pid", intent.getStringExtra("cat_id"))
            reqObj2.put("rest_grocery", "3")
            reqArr.put(reqObj2)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val orderDetails = reqArr
        val placeOrderRequest = PlaceOrderRequest()

        placeOrderRequest.userId = intent.getStringExtra("userId")
        placeOrderRequest.payMode = "COD"
        placeOrderRequest.payId = intent.getStringExtra("payId")
        placeOrderRequest.payStatus = intent.getStringExtra("payStatus")
        placeOrderRequest.totalPrice = intent.getStringExtra("totalPrice")
        placeOrderRequest.delivarAdd = intent.getStringExtra("delivarAdd")
        placeOrderRequest.remark = intent.getStringExtra("remark")
        placeOrderRequest.orderDetails = orderDetails
        when {
            sessionManager.keyOrderType == "5" -> {
                placeOrderRequest.orderType = "Swadesi Product"
                placeOrderRequest.restGrocery = "5"
            }
            sessionManager.keyOrderType == "6" -> {
                placeOrderRequest.orderType = "Medicine Product"
                placeOrderRequest.restGrocery = "6"
            }
            sessionManager.keyOrderType == "7" -> {
                placeOrderRequest.orderType = "Electronic Product"
                placeOrderRequest.restGrocery = "7"
            }
        }
        makeOrderPlaceRequest(placeOrderRequest)
    }

    private fun makeOrderPlaceRequest(orderRequest: PlaceOrderRequest) {

        val loadingDialog = LoadingDialog(this)
        loadingDialog.showDialog()
        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(ApiInterface::class.java)

        val call = api.placeOrderSwadesi(orderRequest.orderDetails, orderRequest.userId,
                orderRequest.remark, orderRequest.totalPrice, "COD", orderRequest.payId,
                orderRequest.payStatus, orderRequest.delivarAdd, orderRequest.orderType, sessionManager.shopId,
                "NA", "INR", "0", sessionManager.shopId)

        call.enqueue(object : Callback<RestResponsePlaceOrder> {
            override fun onResponse(call: Call<RestResponsePlaceOrder>, response: Response<RestResponsePlaceOrder>) {

                try {
                    if (response.body()!!.status == "1") {
                        Toast.makeText(this@PaymentSwadesiProduct, "Order submitted Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@PaymentSwadesiProduct, MyOrdersActivity::class.java))
                        finishAffinity()
                    } else {
                        Snackbar.make(findViewById<View>(android.R.id.content), response.body()!!.msg, Snackbar.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    //e.printStackTrace()
                    Snackbar.make(findViewById<View>(android.R.id.content),
                            ErrorMessageConstant.ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
                } catch (e: IOException) {
                    //e.printStackTrace()
                    Snackbar.make(findViewById<View>(android.R.id.content),
                            ErrorMessageConstant.ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
                }
                loadingDialog.hideDialog()
            }

            override fun onFailure(call: Call<RestResponsePlaceOrder>, t: Throwable) {
                //Log.d("TAG", "onFailure: " + t.message)
                loadingDialog.hideDialog()
                Snackbar.make(findViewById<View>(android.R.id.content),
                        ErrorMessageConstant.NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun confirmOrder(i: Int) {

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm")
        alertDialog.setMessage("Do you want to place order?")
        alertDialog.setIcon(R.drawable.confirm)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(Html.fromHtml("<font color='#009494'>Yes")) { dialog, which ->
            if (i == 1)
                makeRequestPlaceOrder()
            else {
                val myIntent = Intent(this@PaymentSwadesiProduct, OnlinePaymentActivitySwadesiProdct::class.java)
                myIntent.putExtra("PAY_AMOUNT", intent.getStringExtra("totalPrice"))
                myIntent.putExtra("product_name", intent.getStringExtra("product_name"))
                myIntent.putExtra("product_id", intent.getStringExtra("product_id"))
                myIntent.putExtra("qty", intent.getStringExtra("qty"))
                myIntent.putExtra("totalPrice", intent.getStringExtra("totalPrice"))
                myIntent.putExtra("price", intent.getStringExtra("price"))
                myIntent.putExtra("product_desc", intent.getStringExtra("product_desc"))
                myIntent.putExtra("weight", intent.getStringExtra("weight"))
                myIntent.putExtra("unit", intent.getStringExtra("unit"))
                myIntent.putExtra("cat_id", intent.getStringExtra("cat_id"))
                myIntent.putExtra("userId", intent.getStringExtra("userId"))
                myIntent.putExtra("payId", intent.getStringExtra("payId"))
                myIntent.putExtra("payStatus", intent.getStringExtra("payStatus"))
                myIntent.putExtra("delivarAdd", intent.getStringExtra("delivarAdd"))
                myIntent.putExtra("remark", intent.getStringExtra("remark"))
                myIntent.putExtra("orderType", intent.getStringExtra("orderType"))
                startActivity(myIntent)
            }
        }
        alertDialog.setNegativeButton(Html.fromHtml("<font color='#00585e'>No")) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
            super.onBackPressed()
        }
        return true
    }


}