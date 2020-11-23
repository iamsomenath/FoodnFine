package snd.orgn.foodnfine.adapter.activityAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.activity.OrderDetailsActivity
import snd.orgn.foodnfine.interfaces.CanceBtn
import snd.orgn.foodnfine.model.data_item.ItemDetailsResponse
import snd.orgn.foodnfine.model.data_item.Order

class OrderListAdapter(private val activity: Activity, private val context: Context, private val btnSelect: CanceBtn? = null) : RecyclerView.Adapter<OrderListAdapter.MyViewHolder>() {

    private val orderList: ArrayList<Order>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.mouder_order_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            holder.orderNo.text = orderList!![position].orderNumber
            //holder.orderType.setText(orderList.get(position).getOrderType().toUpperCase());
            holder.orderDate.text = orderList[position].orderDateTime
            holder.paymentStatus.text = orderList[position].paymentStatus
            if (orderList[position].paymentStatus.equals("paid", ignoreCase = true)) {
                holder.paymentStatus.setTextColor(Color.parseColor("#558b2f"))
            } else {
                holder.paymentStatus.setTextColor(Color.RED)
            }
            holder.deliveryStatus.text = orderList[position].deliveryStatus
            holder.payMode.text = orderList[position].payMode
            holder.totalPrice.text = "₹" + orderList[position].totalPrice
            holder.deliveryAddress.text = orderList[position].delivarAddress
            holder.restaurantName.text = orderList[position].name
            if(orderList[position].deliveryStatus.equals("canceled", false))
                holder.orderStatus.setTextColor(Color.RED)
             else if(orderList[position].deliveryStatus.equals("Delivered", false))
                holder.orderStatus.setTextColor(Color.BLUE)
            else
                holder.orderStatus.setTextColor(Color.GREEN)

            holder.orderStatus.text = orderList[position].deliveryStatus
            holder.restaurantAddress.text = orderList[position].address
            val orderType = orderList[position].orderType
            if (orderType != "sendPackage") {
                holder.restaurantAddress.visibility = View.VISIBLE
                holder.orderType.text = orderList[position].orderType.toUpperCase()
                holder.layout_item_description.visibility = View.VISIBLE
                holder.pickUpAddress.visibility = View.GONE
                holder.packageContainItems.visibility = View.GONE
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                holder.rv_itemList.layoutManager = layoutManager
                holder.rv_itemList.setHasFixedSize(true)
                val itemArrayList: List<ItemDetailsResponse> = ArrayList(orderList[position].orderDetails)
                val orderListItemAdapter = OrderListItemAdapter(activity, context)
                holder.rv_itemList.adapter = orderListItemAdapter
                orderListItemAdapter.clearOrderDetailsList()
                orderListItemAdapter.addOrderDetailsList(itemArrayList)
                orderListItemAdapter.notifyDataSetChanged()
                holder.layout_name.visibility = View.VISIBLE
                holder.layout_pickup_address.visibility = View.GONE
            } else {
                holder.restaurantAddress.visibility = View.GONE
                holder.orderType.text = "PACKAGE"
                holder.layout_pickup_address.visibility = View.VISIBLE
                holder.layout_item_description.visibility = View.GONE
                holder.layout_name.visibility = View.GONE
                holder.pickUpAddress.visibility = View.VISIBLE
                holder.packageContainItems.visibility = View.VISIBLE
                holder.pickUpAddress.text = orderList[position].pickupAdd
                holder.packageContainItems.text = "👉Package Contain: " + orderList[position]._package + " ," + "👉Approx Distance : " + orderList[position].distance
            }
            val remark = orderList[position].remarks
            if (remark != "") {
                holder.layout_remark.visibility = View.VISIBLE
                holder.remarks.text = orderList[position].remarks
            } else {
                holder.layout_remark.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (orderList!![position].deliveryStatus.equals("canceled", false) ||
                orderList[position].deliveryStatus.equals("Delivered", false) ||
                orderList[position].orderType.equals("sendPackage", false)) {
            holder.cancelOrder.visibility = View.GONE
        } else
            holder.cancelOrder.visibility = View.VISIBLE

        holder.cancelOrder.tag = position
        holder.cancelOrder.setOnClickListener {
            if (btnSelect != null) {
                btnSelect.onBtnSelectValue(holder.cancelOrder.tag as Int)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList!!.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var restaurantImage: RoundedImageView
        var restaurantName: TextView
        var restaurantTypeFood: TextView
        var restaurantAddress: TextView
        var orderNo: TextView
        var orderType: TextView
        var orderStatus: TextView
        var orderDate: TextView
        var totalPrice: TextView
        var deliveryStatus: TextView
        var paymentStatus: TextView
        var payMode: TextView
        var remarks: TextView
        var deliveryAddress: TextView
        var pickUpAddress: TextView
        var packageContainItems: TextView
        var rv_itemList: RecyclerView
        var layout_remark: LinearLayout
        var layout_name: LinearLayout
        var layout_item_description: LinearLayout
        var layout_pickup_address: LinearLayout
        var cancelOrder: Button

        init {
            cancelOrder = itemview.findViewById<View>(R.id.cancelOrder) as Button
            restaurantImage = itemview.findViewById<View>(R.id.rv_restaurantImage) as RoundedImageView
            restaurantName = itemview.findViewById<View>(R.id.tv_restaurantName) as TextView
            restaurantTypeFood = itemview.findViewById<View>(R.id.tv_restaurantFoodType) as TextView
            restaurantAddress = itemview.findViewById<View>(R.id.tv_restaurantAddress) as TextView
            orderNo = itemview.findViewById<View>(R.id.tv_orderNo) as TextView
            orderType = itemview.findViewById<View>(R.id.tv_orderType) as TextView
            orderDate = itemview.findViewById<View>(R.id.tv_orderlist_date) as TextView
            totalPrice = itemview.findViewById<View>(R.id.tv_totalAmount) as TextView
            deliveryStatus = itemview.findViewById<View>(R.id.tv_deliveryStatus) as TextView
            paymentStatus = itemview.findViewById<View>(R.id.tv_payment_status) as TextView
            remarks = itemview.findViewById<View>(R.id.tv_remarks) as TextView
            deliveryAddress = itemview.findViewById<View>(R.id.tv_orderList_deliveryAddress) as TextView
            pickUpAddress = itemview.findViewById<View>(R.id.tv_orderList_pickupAddress) as TextView
            orderStatus = itemview.findViewById<View>(R.id.tv_orderStatus) as TextView
            payMode = itemview.findViewById<View>(R.id.tv_pay_mode) as TextView
            packageContainItems = itemview.findViewById<View>(R.id.tv_package_content_item) as TextView
            rv_itemList = itemview.findViewById<View>(R.id.rv_itemList) as RecyclerView
            layout_remark = itemview.findViewById<View>(R.id.layout_remark) as LinearLayout
            layout_name = itemview.findViewById<View>(R.id.layout_name) as LinearLayout
            layout_item_description = itemview.findViewById<View>(R.id.layout_item_description) as LinearLayout
            layout_pickup_address = itemview.findViewById<View>(R.id.layout_pickup_address) as LinearLayout
            itemView.setOnClickListener { v: View? ->
                val intent = Intent(activity, OrderDetailsActivity::class.java)
                intent.putExtra("DETAILS", orderList!![adapterPosition])
                activity.startActivity(intent)
            }
        }
    }

    fun addOrderList(orderList: List<Order>) {
        this.orderList!!.addAll(orderList)
    }

    fun clearOrderList() {
        orderList?.clear()
    }
    

    init {
        orderList = ArrayList()
    }
}