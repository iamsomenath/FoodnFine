package snd.orgn.foodnfine.adapter.activityAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS
import snd.orgn.foodnfine.constant.ErrorMessageConstant
import snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.other.NetworkHelper
import snd.orgn.foodnfine.model.SearchResponseListPojo
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.rest.request.AddToCartRequest
import snd.orgn.foodnfine.util.LoadingDialog
import java.io.IOException
import java.util.*

class SearchItemAdapter(private val activity: Activity, private var itemArrayList: ArrayList<SearchResponseListPojo>) :
        RecyclerView.Adapter<SearchItemAdapter.MyViewHolder>() {

    private val sessionManager: SessionManager
    private val uid: String?
    internal var data = HashMap<String, String>()

    init {
        sessionManager = SessionManager(activity)
        //data = sessionManager.details
        uid = FoodnFine.appSharedPreference!!.userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item_layout, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.productType.text = itemArrayList[position].productType!!
        holder.storeName.text = " " + itemArrayList[position].store_name!!
        holder.name.text = " " + itemArrayList[position].productName!!
        holder.desc.text = itemArrayList[position].productDesc!!
        holder.itemQuantity.text = " "
        holder.price.text = "₹" + itemArrayList[position].productPrice!!
        holder.price2.text = "₹" + itemArrayList[position].offerPrice!!
        holder.price.paintFlags = holder.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        Picasso.get().load(itemArrayList[position].image)
                .placeholder(R.drawable.new_logo)
                //.transform(new CircleTransform())
                .into(holder.itemImage)

        holder.add_to_cart.setOnClickListener { v ->

            val request = AddToCartRequest()
            request.userId = FoodnFine.appSharedPreference!!.userId
            request.pid = itemArrayList[position].productId
            request.qty = holder.quantity.text.toString()
            request.devKey = FoodnFine.appSharedPreference!!.devKey
            when {
                itemArrayList[position].productType!! == "Restaurant Product" -> {
                    request.orderType = "restaurant"
                    sessionManager.keyOrderType = "3"
                }
                itemArrayList[position].productType!! == "Grocery Product" -> {
                    request.orderType = "grocery"
                    sessionManager.keyOrderType = "2"
                }
                itemArrayList[position].productType!! == "Medicine Product" -> {
                    request.orderType = "Medicine Product"
                    sessionManager.keyOrderType = "6"
                }
                itemArrayList[position].productType!! == "Electronic Product" -> {
                    request.orderType = "Electronic Product"
                    sessionManager.keyOrderType = "7"
                }
                else -> {
                    request.orderType = "Swadesi Product"
                    sessionManager.keyOrderType = "5"
                }
            }
            try {
                request.price = itemArrayList[position].offerPrice!!.toInt() * holder.quantity.text.toString().toInt()
            }catch(e : Exception){
                request.price = (itemArrayList[position].offerPrice!!.toFloat() * holder.quantity.text.toString().toInt()).toInt()
            }
            request.restGrocery = itemArrayList[position].storeId
            makeAddToCartRequest(request)
        }

        holder.ivbtn_minus.setOnClickListener {
            if (holder.quantity.text.toString() != "1") {
                holder.quantity.text = (Integer.parseInt(holder.quantity.text.toString()) - 1).toString()
                val price = java.lang.Double.parseDouble(itemArrayList[position].offerPrice!!)
                val qty = java.lang.Double.parseDouble(holder.quantity.text.toString())
                val totalPrice = price * qty
                // callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),String.valueOf(totalPrice),"");
            }
        }
        holder.ivbtn_plus.setOnClickListener {
            holder.quantity.text = (Integer.parseInt(holder.quantity.text.toString()) + 1).toString()
            val price = java.lang.Double.parseDouble(itemArrayList[position].offerPrice!!)
            val qty = java.lang.Double.parseDouble(holder.quantity.text.toString())
            val totalPrice = price * qty
            //callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),String.valueOf(totalPrice),"");
        }
    }

    override fun getItemCount(): Int {
        return itemArrayList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        internal var itemImage: RoundedImageView
        internal var name: TextView
        internal var storeName: TextView
        internal var productType: TextView
        internal var desc: TextView
        internal var price: TextView
        internal var price2: TextView
        internal var add_to_cart: LinearLayout
        internal var ivbtn_plus: ImageView
        internal var ivbtn_minus: ImageView
        internal val quantity: TextView
        internal val itemQuantity: TextView

        init {
            itemImage = itemview.findViewById<View>(R.id.rv_ProductImage) as RoundedImageView
            name = itemview.findViewById<View>(R.id.tv_itemName) as TextView
            storeName = itemview.findViewById<View>(R.id.storeName) as TextView
            productType = itemview.findViewById<View>(R.id.productType) as TextView
            desc = itemview.findViewById<View>(R.id.tv_description) as TextView
            price = itemview.findViewById<View>(R.id.tv_itemPrice) as TextView
            price2 = itemview.findViewById<View>(R.id.price2) as TextView
            itemQuantity = itemview.findViewById<View>(R.id.tv_item_quantity) as TextView

            add_to_cart = itemview.findViewById<View>(R.id.layout_AddBtn) as LinearLayout
            ivbtn_plus = itemview.findViewById<View>(R.id.ivBtn_add) as ImageView
            ivbtn_minus = itemview.findViewById<View>(R.id.ivBtn_substract) as ImageView
            quantity = itemview.findViewById<View>(R.id.tv_quantity) as TextView
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("CheckResult")
    private fun makeAddToCartRequest(request: AddToCartRequest) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.showDialog()

        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiInterface::class.java)
        val call = service.addToCart(request.userId,
                request.pid, request.qty, request.devKey, request.orderType, request.price, request.restGrocery)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    when {
                        jsonObject.getInt("responsesss") == 1 ->
                            //Toast.makeText(activity, "Items added Successfully", Toast.LENGTH_SHORT).show()
                            onSNACK()
                        jsonObject.getInt("responsesss") == 2 ->
                            showPopup(request)
                        else -> Snackbar.make(activity.findViewById<View>(android.R.id.content), "Unable to add items", Snackbar.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    //e.printStackTrace()
                    Snackbar.make(activity.findViewById<View>(android.R.id.content),
                            ErrorMessageConstant.ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
                } catch (e: IOException) {
                    //e.printStackTrace()
                    Snackbar.make(activity.findViewById<View>(android.R.id.content),
                            ErrorMessageConstant.ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
                }
                loadingDialog.hideDialog()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //Log.d("TAG", "onFailure: " + t.message)
                loadingDialog.hideDialog()
                Snackbar.make(activity.findViewById<View>(android.R.id.content),
                        ErrorMessageConstant.NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    fun onSNACK() {
        /*val snackbar = Snackbar.make(view, "Items added Successfully",
                Snackbar.LENGTH_LONG).setAction("\uD83D\uDED2 VIEW") {
            val userRequest = UserRequest()
            userRequest.userId = DeliveryEverything.getAppSharedPreference().userId
            userRequest.orderType = "grocery"
            makeCartDetailsRequest(userRequest)
        }*/
        val snackbar = Snackbar.make(activity.findViewById<View>(android.R.id.content), "\uD83D\uDED2 Items added Successfully", Snackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        textView.textSize = 15f
        textView.typeface = Typeface.createFromAsset(activity.assets, "ProximaNovaLight.otf")
        snackbar.show()
    }

    private fun showPopup(request: AddToCartRequest) {
        try {
            val li = LayoutInflater.from(activity)
            val prompt = li.inflate(R.layout.popup_cart_new, null)
            val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setView(prompt)
            val dlg = alertDialogBuilder.show()
            alertDialogBuilder.setCancelable(false)
            dlg.setCancelable(false)
            (prompt.findViewById(R.id.yes) as TextView).setOnClickListener {
                delete_all_from_cart(uid!!, request)
                dlg.dismiss()
            }
            (prompt.findViewById(R.id.no) as TextView).setOnClickListener {
                dlg.dismiss()
            }
        } catch (ignore: Exception) {
            //e.printStackTrace();
        }
    }

    private fun getAPIInterface(): ApiInterface? {
        var apiInterface: ApiInterface? = null
        if (apiInterface == null) {
            apiInterface = NetworkHelper.getClient().create(ApiInterface::class.java)
        }
        return apiInterface
    }

    @SuppressLint("CheckResult")
    private fun delete_all_from_cart(userId: String, request: AddToCartRequest) {
        val userResponseObservable = getAPIInterface()!!.deleteAllCartDetails(FoodnFine.appSharedPreference!!.userId)
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restResponse ->
                    if (restResponse.status == WEB_SUCCESS) {
                        makeAddToCartRequest(request)
                    } else {
                        Toast.makeText(activity, "Unable to delete data!!!", Toast.LENGTH_SHORT).show()
                    }
                }, { e ->
                    Toast.makeText(activity, NETWORK_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                })
    }
}