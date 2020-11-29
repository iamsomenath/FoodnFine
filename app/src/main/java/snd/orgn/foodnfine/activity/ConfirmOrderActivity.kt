package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_confirm_order.*
import org.json.JSONException
import org.json.JSONObject
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.CreateOrderAdapter
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackApplyCouponActivity
import snd.orgn.foodnfine.callbacks.CallbackConfirmOrderActivity
import snd.orgn.foodnfine.callbacks.CallbackSelectedCartItemUpdate
import snd.orgn.foodnfine.constant.AppConstants
import snd.orgn.foodnfine.data.room.entity.AddressDetails
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.model.utility.UserData
import snd.orgn.foodnfine.rest.response.CartDatum
import snd.orgn.foodnfine.rest.response.RestResponseCart
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.view_model.ActivityViewModel.ConfirmOrderActivityViewModel
import java.util.*

class ConfirmOrderActivity : BaseActivity(), CallbackSelectedCartItemUpdate, CallbackConfirmOrderActivity, CallbackApplyCouponActivity {

    var scrollView: ScrollView? = null
    var restResponseCart: RestResponseCart? = null
    lateinit var cartDatumList: List<CartDatum>
    private var OrderType: String? = ""
    private var seletedOrderType: String? = ""
    private var seletedCartItemId = ""
    private var seletedCartItemQuantity = ""
    private var seletedRestGroceryId = ""
    private var copoun_code = ""
    private var coupon_category = ""
    private var coupon_id = ""
    private var copoun_type = ""
    private var total = ""
    private var quantity: Int? = null
    private var totalPrice: Int? = null
    private var itemprice: Int? = null
    var viewModel: ConfirmOrderActivityViewModel? = null
    var loadingDialog: LoadingDialog? = null
    var placeId1: String? = null
    var rootView: View? = null
    var fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
    var payment = "null"
    var sessionManager: SessionManager? = null

    var details_address: EditText? = null
    lateinit var adapter: CreateOrderAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        ButterKnife.bind(this)

        //deliveryCharge = (int) Math.floor(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getDeliveryCost()));
        //Log.d("DELIVERY", deliveryCharge+"");
        //Toast.makeText(this, "DELIVERY : " + deliveryCharge, Toast.LENGTH_SHORT).show();
        loadingDialog = LoadingDialog(this)
        initViewModel()
        hideStatusBarcolor()
        sessionManager = SessionManager(this)
        initFields()
        setupOnClick()
        dataPopulate()
        rootView = findViewById<View>(android.R.id.content).rootView
        setupUI(rootView, this@ConfirmOrderActivity)
    }

    override fun initFields() {
        restResponseCart = RestResponseCart()
        restResponseCart = intent.getSerializableExtra(AppConstants.INTENT_STRING_CART_DETAIL) as RestResponseCart?
        OrderType = intent.getStringExtra(AppConstants.INTENT_STRING_ORDER_TYPE)
        FoodnFine.appSharedPreference!!.saveDeliveryAdd(tv_confirmOrder_delivery_address!!.text.toString())
        seletedRestGroceryId = restResponseCart!!.restGrocery

        FoodnFine.appSharedPreference!!.setSeletedRestGroceryId(seletedRestGroceryId)
        seletedOrderType = if (OrderType.equals(AppConstants.ORDER_TYPE_GROCERY, ignoreCase = true)) {
            AppConstants.TYPE_GROCERY
        } else if (OrderType.equals(AppConstants.ORDER_TYPE_RESTAURANT, ignoreCase = true)) {
            AppConstants.TYPE_RESTAURANT
        } else OrderType
        cartDatumList = ArrayList()
        (cartDatumList as ArrayList<CartDatum>).addAll(restResponseCart!!.cartData)
        FoodnFine.appSharedPreference!!.saveArrayList(restResponseCart!!.cartData)

        initRecyclerOrderList()

        adapter.clearcartDetails()
        adapter.addAllcartDeatils(cartDatumList)
        adapter.notifyDataSetChanged()
    }

    override fun setupOnClick() {
        iv_confirmOrder_back!!.setOnClickListener { v: View? ->
            super.onBackPressed()
            finish()
        }
        ivBtn_confirmOrderChangeAddress!!.setOnClickListener { v: View? -> findPlace() }
        tvBtn_ConfirmOrder_continue!!.setOnClickListener { v: View? ->

            FoodnFine.appSharedPreference!!.saveRemark(et_ConfirmOrder_remark!!.text.toString())
            goToSelectPaymentMethods()
        }
        getAddress!!.setOnClickListener { v: View? ->
            val intent = Intent(this, GetFromSavedAddressActivity::class.java)
            intent.putExtra("VAL", "3")
            startActivityForResult(intent, 3)
        }
        offers!!.setOnClickListener { view: View? ->
            val intent = Intent(applicationContext, OffersActivity::class.java)
            startActivity(intent)
        }
        cancelCode!!.setOnClickListener { view: View? ->
            discountAmount = 0
            applyCode!!.visibility = View.VISIBLE
            cancelCode!!.visibility = View.GONE
            couponcode!!.setText("")
            val price2 = restResponseCart!!.sumPrice.toString()
            tv_confirmOrder_item_price!!.text = "₹ $price2.00"
            copoun_code = ""
            coupon_category = ""
            coupon_id = ""
            copoun_type = ""
        }
        applyCode!!.setOnClickListener { view: View? ->
            if (couponcode!!.text.toString().isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Please enter coupon code", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            coupon_category = if (OrderType.equals(AppConstants.ORDER_TYPE_GROCERY, ignoreCase = true)) {
                "2"
            } else if (OrderType.equals(AppConstants.ORDER_TYPE_RESTAURANT, ignoreCase = true)) {
                "1"
            } else "0"
            loadingDialog!!.showDialog()
            viewModel!!.applyCoupon(couponcode!!.text.toString(), FoodnFine.appSharedPreference!!.userId,
                    coupon_category, sessionManager!!.shopId)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ConfirmOrderActivityViewModel::class.java)
        viewModel!!.setCallback(this)
        viewModel!!.setCallback2(this)
    }

    private fun initRecyclerOrderList() {
        adapter = CreateOrderAdapter(this)
        adapter.setCallback(this)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_createOrder!!.layoutManager = layoutManager
        rv_createOrder!!.adapter = adapter
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dataPopulate() {
        tv_confirmOrder_type!!.text = OrderType
        tv_confirmOrder_delivery_address!!.setText(FoodnFine.appSharedPreference!!.currentLocation)
        tv_confirmOrder_totalPrice!!.text = AppConstants.RUPREES_SYMBOL + restResponseCart!!.sumPrice
        val quant = restResponseCart!!.sumcartCount.toString()
        val price = (restResponseCart!!.sumPrice + deliveryCharge + cancellationCharge + fixedCharge - discountAmount).toString()
        if (quant == "1") {
            tv_confirmOrder_item_count!!.text = "$quant item"
        } else {
            tv_confirmOrder_item_count!!.text = "$quant items"
        }
        tv_confirmOrder_item_price!!.text = AppConstants.RUPREES_SYMBOL + price + ".00"
        //Log.d("TESTING", deliveryCharge + "");
    }

    private fun findPlace() {
        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
        startActivityForResult(intent, 1)
    }

    @SuppressLint("SetTextI16n", "SetTextI18n")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                placeId1 = place.id
                /*queriedLocation = place.getLatLng();
                String lat = Double.toString(Objects.requireNonNull(place.getLatLng()).latitude);
                String lon = Double.toString(place.getLatLng().longitude);*/
                val startPoint = Location("locationA")
                startPoint.latitude = place.latLng!!.latitude
                startPoint.longitude = place.latLng!!.longitude
                val endPoint = Location("locationB")
                endPoint.latitude = FoodnFine.appSharedPreference!!.latitude.toDouble()
                endPoint.longitude = FoodnFine.appSharedPreference!!.longitude.toDouble()
                val distance = startPoint.distanceTo(endPoint) / 1000.toDouble()
                //Toast.makeText(activity, distance + "", Toast.LENGTH_SHORT).show();
                if (distance <= 1) //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInOneKm));
                    FoodnFine.appSharedPreference!!.saveDeliveryCost(FoodnFine.appSharedPreference!!.cost1) else if (distance < 5) //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInFiveKm));
                    FoodnFine.appSharedPreference!!.saveDeliveryCost(FoodnFine.appSharedPreference!!.cost2) else if (distance < 10) //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInTenKm));
                    FoodnFine.appSharedPreference!!.saveDeliveryCost(FoodnFine.appSharedPreference!!.cost3) else  //if(distance>10)
                //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInGrater10Km));
                    FoodnFine.appSharedPreference!!.saveDeliveryCost(FoodnFine.appSharedPreference!!.cost4)
                tv_confirmOrder_delivery_address!!.setText(place.address)
                FoodnFine.appSharedPreference!!.saveDeliveryAdd(tv_confirmOrder_delivery_address!!.text.toString())
                FoodnFine.appSharedPreference!!.saveCurrentLocation(place.address)
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 3) { // to fetch pickup saved address
            try {
                if (data!!.hasExtra("MESSAGE")) {
                    //Log.d("TESTS", data.getSerializableExtra("MESSAGE").toString());
                    val addressDetails = data.getSerializableExtra("MESSAGE") as AddressDetails?
                    details_address!!.setText(addressDetails!!.building + ", " + addressDetails.house + ", " +
                            addressDetails.landmark + ", " + addressDetails.location)
                }
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onEditedCartItem(cartId: String, qty: String, price: String) {
        loadingDialog!!.showDialog()
        seletedCartItemQuantity = qty
        seletedCartItemId = cartId
        quantity = qty.toInt()
        itemprice = price.toDouble().toInt()
        totalPrice = quantity!! * itemprice!!
        viewModel!!.updateCartItem(getUserdataForUpdateCartCart())
    }

    override fun onDeletedCartItem(cartId: String) {
        loadingDialog!!.hideDialog()
        val userData = UserData()
        userData.user_id = FoodnFine.appSharedPreference!!.userId
        userData.cartId = cartId
        viewModel!!.deleteCartItem(userData)
        Toast.makeText(this, "Cart items deleted successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessUpadteCartItem() {
        viewModel!!.getCartDetails(getuserdataForCartDetails())
    }

    override fun onSuccessDeleteCartItem() {
        viewModel!!.getCartDetails(getuserdataForCartDetails())
    }

    override fun onSuccessCartDetails(cartDetails: RestResponseCart) {
        loadingDialog!!.hideDialog()
        restResponseCart = cartDetails
        cartDatumList = ArrayList()
        (cartDatumList as ArrayList<CartDatum>).addAll(restResponseCart!!.cartData)
        FoodnFine.appSharedPreference!!.saveArrayList(restResponseCart!!.cartData)
        seletedRestGroceryId = restResponseCart!!.restGrocery
        FoodnFine.appSharedPreference!!.setSeletedRestGroceryId(seletedRestGroceryId)
        initRecyclerOrderList()
        adapter.clearcartDetails()
        adapter.addAllcartDeatils(cartDatumList)
        adapter.notifyDataSetChanged()
        dataPopulate()
    }

    override fun onError(message: String) {
        loadingDialog!!.hideDialog()
        loadingDialog!!.hideDialog()
    }

    override fun onNetworkError(message: String) {
        loadingDialog!!.hideDialog()
        loadingDialog!!.hideDialog()
    }

    private fun getUserdataForUpdateCartCart(): UserData {
        val userData = UserData()
        userData.user_id = FoodnFine.appSharedPreference!!.userId
        userData.cartId = seletedCartItemId
        userData.price = itemprice
        userData.quantity = seletedCartItemQuantity
        return userData
    }

    private fun getuserdataForCartDetails(): UserData {
        val data = UserData()
        data.user_id = FoodnFine.appSharedPreference!!.userId
        when (OrderType) {
            AppConstants.ORDER_TYPE_GROCERY, "grocery" -> {
                data.orderType = AppConstants.TYPE_GROCERY
                seletedOrderType = AppConstants.TYPE_GROCERY
            }
            AppConstants.ORDER_TYPE_RESTAURANT, "restaurant" -> {
                data.orderType = AppConstants.TYPE_RESTAURANT
                seletedOrderType = AppConstants.TYPE_RESTAURANT
            }
            else -> {
            }
        }
        return data
    }

    private fun goToSelectPaymentMethods() {
        val selectedId = rg_payment_home.checkedRadioButtonId
        payment = when (selectedId) {
            R.id.rb_payment_option_op -> "op"
            R.id.rb_payment_option_cod -> "cod"
            else -> ""
        }
        try {
            if (payment.isEmpty()) {
                scrollView!!.scrollTo(0, scrollView!!.bottom)
                Snackbar.make(findViewById(android.R.id.content),
                        "Please select payment option", Snackbar.LENGTH_SHORT).show()
                return
            }
        } catch (e: Exception) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Please select payment option", Snackbar.LENGTH_SHORT).show()
            return
        }

        val selectedId2 = radioGroup!!.checkedRadioButtonId
        val type2: RadioButton
        try {
            type2 = findViewById<View>(selectedId2) as RadioButton
            if (selectedId2 == -1) {
                //Toast.makeText(ConfirmOrderActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content),
                        "Please select Contact OR Non-contact delivery", Snackbar.LENGTH_SHORT).show()
                return
            }
        } catch (e: Exception) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Please select Contact OR Non-contact delivery", Snackbar.LENGTH_SHORT).show()
            return
        }

        total = (restResponseCart!!.sumPrice + deliveryCharge + cancellationCharge + fixedCharge - discountAmount).toString()
        if (payment == "cod") {
            sessionManager!!.successCall("payment_cod")
            sessionManager!!.setPayType("COD")
            FoodnFine.appSharedPreference!!.saveDeliveryAdd(tv_confirmOrder_delivery_address!!.text.toString())
            FoodnFine.appSharedPreference!!.orderType = seletedOrderType
            val sessionManager = SessionManager(this)
            sessionManager.redirect("NORMAL")
            val intent = Intent(applicationContext, SelectPaymentActivity::class.java)
            //intent.putExtra(INTENT_STRING_TOTAL_PRICE, String.valueOf(restResponseCart.getSumPrice()));
            intent.putExtra(AppConstants.INTENT_STRING_TOTAL_PRICE, total)
            intent.putExtra(AppConstants.INTENT_STRING_ORDER_TYPE, OrderType)
            // newly added
            intent.putExtra("delivery_charge", deliveryCharge.toString())
            intent.putExtra("cancellation_charge", cancellationCharge.toString())
            intent.putExtra("discount_amount", discountAmount.toString())
            intent.putExtra("copoun_type", copoun_type)
            intent.putExtra("copoun_code", copoun_code)
            intent.putExtra("coupon_id", coupon_id)
            intent.putExtra("coupon_category", coupon_category)
            intent.putExtra("total", total)
            intent.putExtra("order_actual_amount", restResponseCart!!.sumPrice.toString())
            intent.putExtra("remark_type", type2.text.toString())
            //Toast.makeText(this, deliveryCharge + " " + cancellationCharge + " " + discountAmount + " ", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, copoun_type + " " + copoun_code + " " + coupon_id + " " + coupon_category, Toast.LENGTH_SHORT).show();
            startActivity(intent)
        } else  /*if (payment.equals("op"))*/ {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Confirm")
            alertDialog.setMessage("Do you want to place order?")
            alertDialog.setIcon(R.drawable.confirm)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(Html.fromHtml("<font color='#009494'>Yes")
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                sessionManager!!.successCall("payment_online")
                sessionManager!!.setPayType("Online")
                val intent = Intent(this, OnlinePaymentActivity::class.java)
                //intent.putExtra("amount", String.valueOf(restResponseCart.getSumPrice()));
                intent.putExtra("amount", total)
                //intent.putExtra("pay_amount", String.valueOf(restResponseCart.getSumPrice()));
                intent.putExtra("pay_amount", total)
                FoodnFine.appSharedPreference!!.orderType = seletedOrderType
                intent.putExtra("ordertype", OrderType)
                // newly added
                intent.putExtra("delivery_charge", deliveryCharge.toString())
                intent.putExtra("cancellation_charge", cancellationCharge.toString())
                intent.putExtra("discount_amount", discountAmount.toString())
                intent.putExtra("copoun_type", copoun_type)
                intent.putExtra("copoun_code", copoun_code)
                intent.putExtra("coupon_id", coupon_id)
                intent.putExtra("coupon_category", coupon_category)
                intent.putExtra("total", total)
                intent.putExtra("order_actual_amount", restResponseCart!!.sumPrice.toString())
                intent.putExtra("remark_type", type2.text.toString())
                startActivity(intent)
            }
            alertDialog.setNegativeButton(Html.fromHtml("<font color='#00585e'>No")
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            alertDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessCoupon(response: JSONObject) {
        loadingDialog!!.hideDialog()
        val price = restResponseCart!!.sumPrice
        //Log.d("RESPONSE", response.toString() + " " + price);
        try {
            val minimum_order_amount = response.getString("minimum_order_amount").toDouble().toInt()
            var discount_amt = 0
            //Log.d("RESPONSE", minimum_order_amount + " " + discount_amt);
            if (price >= minimum_order_amount) {
                cancelCode!!.visibility = View.VISIBLE
                applyCode!!.visibility = View.GONE
                Toast.makeText(this, "Coupon code applied successfully", Toast.LENGTH_SHORT).show()
                discount_amt = if (response.getString("discount_type") == "flat") response.getString("discount_amt").toDouble().toInt() else (price * response.getString("discount_amt").toDouble()) as Int / 100
                discountAmount = discount_amt
                copoun_code = response.getString("code")
                coupon_category = response.getString("coupon_category")
                coupon_id = response.getString("coupon_id")
                copoun_type = response.getString("discount_type")
                val price2 = (restResponseCart!!.sumPrice + deliveryCharge + cancellationCharge - discountAmount).toString()
                tv_confirmOrder_item_price!!.text = "₹ $price2.00"
            } else {
                /*new MaterialDialog.Builder(this)
                        //.title(getResources().getString(R.string.dialogTitle_logout))
                        .content("Minimum order amount should be ₹" + minimum_order_amount + " to apply this code")
                        .positiveText("Cancel")
                        .positiveColor(ContextCompat.getColor(this, R.color.button_and_vespac_red_color))
                        .onPositive((dialog, which) -> {
                            dialog.dismiss();
                        }).show();*/
                val alertDialog2 = android.app.AlertDialog.Builder(this)
                //alertDialog2.setTitle("Unable");
                alertDialog2.setMessage("Minimum order amount should be ₹$minimum_order_amount to apply this code")
                alertDialog2.setPositiveButton("Ok"
                ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                alertDialog2.setCancelable(false)
                alertDialog2.show()
            }
        } catch (e: JSONException) {
            //Log.d("EXCEP", Objects.requireNonNull(e.message))
            e.printStackTrace()
        }
    }

    override fun onFailureCoupon(response: String) {
        loadingDialog!!.hideDialog()
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }

    override fun onNetworkErrorCoupon(message: String) {
        loadingDialog!!.hideDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        var discountAmount = 0
        var deliveryCharge = 0
        var cancellationCharge = 0
        var fixedCharge = 0
    }
}