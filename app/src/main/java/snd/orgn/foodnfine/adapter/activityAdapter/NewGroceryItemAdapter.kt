package snd.orgn.foodnfine.adapter.activityAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snd.orgn.foodnfine.model.GroceryItemPojo
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.application.DeliveryEverything
import snd.orgn.foodnfine.constant.ErrorMessageConstant
import snd.orgn.foodnfine.constant.WebConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.rest.api.ApiInterface
import snd.orgn.foodnfine.rest.request.AddToCartRequest
import snd.orgn.foodnfine.util.LoadingDialog
import java.io.IOException
import java.util.*

class NewGroceryItemAdapter(private val context: Context, private val activity: Activity,
                            internal var itemArrayList: ArrayList<GroceryItemPojo>,
                            private val menu: Menu) : RecyclerView.Adapter<NewGroceryItemAdapter.MyViewHolder>() {

    private val sessionManager: SessionManager
    private val uid: String?
    internal var data = HashMap<String, String>()

    init {
        sessionManager = SessionManager(context)
        //Retriving Data
        //data = sessionManager.details
        uid = DeliveryEverything.getAppSharedPreference().userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.modular_item_layout_grocery, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = " " + itemArrayList[position].product_name!!
        holder.brand.text = " " + itemArrayList[position].product_desc!!
        holder.itemQuantity.text = " " + itemArrayList[position].weight!! + " " + itemArrayList[position].unit!!
        holder.price.text = "₹" + itemArrayList[position].price!!
        holder.price2.text = "₹" + itemArrayList[position].price!!
        holder.price.paintFlags = holder.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        Picasso.get().load(itemArrayList[position].product_image)
                .placeholder(R.drawable.twiclo_logo)
                //.transform(new CircleTransform())
                .into(holder.itemImage)

        holder.add_to_cart.setOnClickListener { v ->
            /*callback.onAddBttomPres(holder.quantity.text.toString(), itemArrayList[position].product_id,
                    itemArrayList[position].price)*/

            val request = AddToCartRequest()
            request.userId = DeliveryEverything.getAppSharedPreference().userId
            request.pid = itemArrayList[position].product_id
            request.qty = holder.quantity.text.toString()
            request.devKey = DeliveryEverything.getAppSharedPreference().devKey
            request.orderType = "grocery"
            request.price = itemArrayList[position].price!!.toInt()
            request.restGrocery = sessionManager.shopId
            makeAddToCartRequest(request, holder.add_to_cart)
        }

        holder.ivbtn_minus.setOnClickListener { v1 ->
            if (holder.quantity.text.toString() != "1") {
                holder.quantity.text = (Integer.parseInt(holder.quantity.text.toString()) - 1).toString()
                val price = java.lang.Double.parseDouble(itemArrayList[position].price!!)
                val qty = java.lang.Double.parseDouble(holder.quantity.text.toString())
                val totalPrice = price * qty
                // callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),String.valueOf(totalPrice),"");
            }
        }
        holder.ivbtn_plus.setOnClickListener { v2 ->
            holder.quantity.text = (Integer.parseInt(holder.quantity.text.toString()) + 1).toString()
            val price = java.lang.Double.parseDouble(itemArrayList[position].price!!)
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
        internal var brand: TextView
        internal var price: TextView
        internal var price2: TextView
        internal var add_to_cart: LinearLayout
        internal var ivbtn_plus: ImageView
        internal var ivbtn_minus: ImageView
        internal val quantity: TextView
        internal val itemQuantity: TextView

        init {
            itemImage = itemview.findViewById<View>(R.id.rv_groceryProductImage) as RoundedImageView
            name = itemview.findViewById<View>(R.id.tv_grocery_itemName) as TextView
            brand = itemview.findViewById<View>(R.id.tv_grocery_item_description) as TextView
            price = itemview.findViewById<View>(R.id.tv_grocery_itemPrice) as TextView
            price2 = itemview.findViewById<View>(R.id.price2) as TextView
            add_to_cart = itemview.findViewById<View>(R.id.layout_AddBtn) as LinearLayout
            ivbtn_plus = itemview.findViewById<View>(R.id.ivBtn_add) as ImageView
            ivbtn_minus = itemview.findViewById<View>(R.id.ivBtn_substract) as ImageView
            quantity = itemview.findViewById<View>(R.id.tv_quantity) as TextView
            itemQuantity = itemview.findViewById<View>(R.id.tv_grocery_quantity) as TextView
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("CheckResult")
    private fun makeAddToCartRequest(request: AddToCartRequest, view: View) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.showDialog()

        val retrofit = Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiInterface::class.java)
        val call = service.addToCart(request.getUserId(),
                request.getPid(), request.getQty(), request.getDevKey(), request.getOrderType(), request.getPrice(),
                request.getRestGrocery())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if (jsonObject.getInt("responsesss") == 1) {
                        //Toast.makeText(activity, "Items added Successfully", Toast.LENGTH_SHORT).show()
                        onSNACK(view)
                    } else {
                        Snackbar.make(activity.findViewById<View>(android.R.id.content), "Unable to add items", Snackbar.LENGTH_LONG).show()
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

    fun onSNACK(view: View) {
        /*val snackbar = Snackbar.make(view, "Items added Successfully",
                Snackbar.LENGTH_LONG).setAction("\uD83D\uDED2 VIEW") {
            val userRequest = UserRequest()
            userRequest.userId = DeliveryEverything.getAppSharedPreference().userId
            userRequest.orderType = "grocery"
            makeCartDetailsRequest(userRequest)
        }*/
        val snackbar = Snackbar.make(view, "\uD83D\uDED2 Items added Successfully", Snackbar.LENGTH_SHORT)
        /*snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        textView.textSize = 15f
        textView.typeface = Typeface.createFromAsset(activity.assets, "ProximaNovaLight.otf")*/
        snackbar.show()
    }
}

