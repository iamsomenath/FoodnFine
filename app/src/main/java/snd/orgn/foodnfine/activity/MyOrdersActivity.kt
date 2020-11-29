package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_myorder.*
import kotlinx.android.synthetic.main.layout_no_order_found.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.OrderListAdapter
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackCancelOrder
import snd.orgn.foodnfine.callbacks.CallbackOrderListActivity
import snd.orgn.foodnfine.interfaces.CanceBtn
import snd.orgn.foodnfine.model.data_item.Order
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.view_model.ActivityViewModel.OrderListActivityViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyOrdersActivity : BaseActivity(), CallbackOrderListActivity, CallbackCancelOrder {

    private var orderlists: List<Order> = ArrayList()
    var orderListAdpter: OrderListAdapter? = null
    var viewModel: OrderListActivityViewModel? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myorder)
        ButterKnife.bind(this)
        hideStatusBarcolor()
        setFetchingDataLayout()
        // loadingDialog = new LoadingDialog(this);
        initViewModel()
        //loadingDialog.showDialog();
        loadingDialog = LoadingDialog(this)
        loadingDialog.showDialog()
        viewModel!!.getOrderList()
        initFields()
        setupOnClick()
    }

    override fun initFields() {
        initRecyclerRestaurantList()
    }

    override fun setupOnClick() {
        iv_order_back!!.setOnClickListener { v: View? ->
            if (intent.hasExtra("FROM")) super.onBackPressed() else {
                startActivity(Intent(this, DasboardActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(OrderListActivityViewModel::class.java)
        viewModel!!.setCallback(this)
        viewModel!!.setCallback2(this)
    }

    private fun initRecyclerRestaurantList() {
        orderListAdpter = OrderListAdapter(this, this, object : CanceBtn {
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onBtnSelectValue(position: Int) {
                try {
                    //Toast.makeText(this@MyOrdersActivity, orderlists[position].orderDateTime.split(" ")[1], Toast.LENGTH_SHORT).show()
                    val sdf = SimpleDateFormat("HH:mm:ss")
                    val currentDateandTime: String = sdf.format(Date())
                    //Log.d("TIME", currentDateandTime)
                    val date1: Date = sdf.parse(orderlists[position].orderDateTime.split(" ")[1])
                    val date2: Date = sdf.parse(currentDateandTime)
                    val mills = (date1.time - date2.time) / 1000
                    if (mills > 60) {

                        /*new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.dialogTitle_success))
                .content(getResources().getString(R.string.dialogMessage_user_subcription))
                .positiveText(getResources().getString(R.string.dialogPositiveButtonText_subcription))
                .positiveColor(ContextCompat.getColor(this, R.color.colorTranslucentButton))
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    super.onBackPressed();
                }).show();*/

                        val alertDialog2 = AlertDialog.Builder(this@MyOrdersActivity)
                        alertDialog2.setTitle("Confirm to cancel")
                        alertDialog2.setMessage("Are you sure you want to cancel the order? It may incur cancellation charges.")
                        alertDialog2.setPositiveButton("Yes"
                        ) { dialog: DialogInterface?, which: Int ->
                            dialog!!.dismiss()
                            loadingDialog.showDialog()
                            viewModel!!.cancelOrder(FoodnFine.appSharedPreference!!.userId, orderlists[position].orderId)
                        }
                        alertDialog2.setNegativeButton("No"
                        ) { dialog: DialogInterface?, which: Int ->
                            dialog!!.dismiss()
                        }
                        alertDialog2.setCancelable(false)
                        alertDialog2.show()

                        /* MaterialDialog.Builder(this@MyOrdersActivity)
                                 .title("Confirm to cancel")
                                 .content("Are you sure you want to cancel the order? It may incur cancellation charges.")
                                 .positiveText("Yes")
                                 .positiveColor(ContextCompat.getColor(this@MyOrdersActivity, R.color.button_and_vespac_red_color))
                                 .negativeText("No")
                                 .negativeColor(ContextCompat.getColor(this@MyOrdersActivity, R.color.colorTranslucentButton))
                                 .onPositive { dialog: MaterialDialog?, which: DialogAction? ->
                                     //Toast.makeText(this@MyOrdersActivity, mills.toString(), Toast.LENGTH_SHORT).show()
                                     dialog!!.dismiss()
                                     loadingDialog.showDialog()
                                     viewModel!!.cancelOrder(DeliveryEverything.getAppSharedPreference().userId, orderlists[position].orderId)
                                 }
                                 .onNegative { dialog: MaterialDialog, which: DialogAction? -> dialog.dismiss() }.show()*/
                    } else {
                        /* MaterialDialog.Builder(this@MyOrdersActivity)
                                 .title("Confirm to cancel")
                                 .content("Are you sure you want to cancel the order?")
                                 .positiveText("Yes")
                                 .positiveColor(ContextCompat.getColor(this@MyOrdersActivity, R.color.button_and_vespac_red_color))
                                 .negativeText("No")
                                 .negativeColor(ContextCompat.getColor(this@MyOrdersActivity, R.color.colorTranslucentButton))
                                 .onPositive { dialog: MaterialDialog?, which: DialogAction? ->
                                     //Toast.makeText(this@MyOrdersActivity, mills.toString(), Toast.LENGTH_SHORT).show()
                                     dialog!!.dismiss()
                                     loadingDialog.showDialog()
                                     viewModel!!.cancelOrder(DeliveryEverything.getAppSharedPreference().userId, orderlists[position].orderId)
                                 }
                                 .onNegative { dialog: MaterialDialog, which: DialogAction? -> dialog.dismiss() }.show()*/

                        val alertDialog2 = AlertDialog.Builder(this@MyOrdersActivity)
                        alertDialog2.setTitle("Confirm to cancel")
                        alertDialog2.setMessage("Are you sure you want to cancel the order?")
                        alertDialog2.setPositiveButton("Yes"
                        ) { dialog: DialogInterface?, which: Int ->
                            dialog!!.dismiss()
                            loadingDialog.showDialog()
                            viewModel!!.cancelOrder(FoodnFine.appSharedPreference!!.userId, orderlists[position].orderId)
                        }
                        alertDialog2.setNegativeButton("No"
                        ) { dialog: DialogInterface?, which: Int ->
                            dialog!!.dismiss()
                        }
                        alertDialog2.setCancelable(false)
                        alertDialog2.show()
                    }
                } catch (e: Exception) {
                }
            }
        })
        //  myPostAdapter.setCallbackAddTocart(this);
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_orderlistRecyclerView!!.layoutManager = layoutManager
        rv_orderlistRecyclerView!!.adapter = orderListAdpter
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    override fun onSuccess(orderlists: List<Order>) {
        loadingDialog.hideDialog()
        this.orderlists = orderlists
        if (orderlists.isNotEmpty()) {
            setDataAvailableLayout()
            orderListAdpter!!.clearOrderList()
            orderListAdpter!!.addOrderList(orderlists)
            orderListAdpter!!.notifyDataSetChanged()
        } else {
            setNoDataLayout()
        }
    }

    override fun onError(message: String) {
        loadingDialog.hideDialog()
        setNoDataLayout()
        /*Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();*/
    }

    override fun onNetworkError(message: String) {
        loadingDialog.hideDialog()
        setNoDataLayout()
        Snackbar.make(findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG).show()
    }

    override fun onSuccessCancel(response: String?) {
        loadingDialog.hideDialog()
        /*MaterialDialog.Builder(this@MyOrdersActivity)
                .content(response!!)
                .positiveText("DONE")
                .positiveColor(ContextCompat.getColor(this@MyOrdersActivity, R.color.colorTranslucentButton))
                .onPositive { dialog: MaterialDialog?, which: DialogAction? ->
                    //Toast.makeText(this@MyOrdersActivity, mills.toString(), Toast.LENGTH_SHORT).show()
                    dialog!!.dismiss()
                    finish()
                }.show()*/
        val alertDialog2 = AlertDialog.Builder(this@MyOrdersActivity)
        alertDialog2.setMessage(response!!)
        alertDialog2.setPositiveButton("DONE"
        ) { dialog: DialogInterface?, which: Int ->
            dialog!!.dismiss()
            finish()
        }
        alertDialog2.show()
        alertDialog2.setCancelable(false)
    }

    override fun onErrorCancel(message: String?) {
        loadingDialog.hideDialog()
        Snackbar.make(findViewById(android.R.id.content), message!!, Snackbar.LENGTH_LONG).show()
    }

    override fun onNetworkErrorCancel(message: String?) {
        loadingDialog.hideDialog()
        Snackbar.make(findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG).show()
    }

    fun setNoDataLayout() {
        rv_orderlistRecyclerView!!.visibility = View.GONE
        layout_orderlist_notFound!!.visibility = View.VISIBLE
        layout_orderList_fetchingData!!.visibility = View.GONE
    }

    fun setFetchingDataLayout() {
        rv_orderlistRecyclerView!!.visibility = View.GONE
        layout_orderlist_notFound!!.visibility = View.GONE
        layout_orderList_fetchingData!!.visibility = View.VISIBLE
    }

    fun setDataAvailableLayout() {
        rv_orderlistRecyclerView!!.visibility = View.VISIBLE
        layout_orderlist_notFound!!.visibility = View.GONE
        layout_orderList_fetchingData!!.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (intent.hasExtra("FROM")) super.onBackPressed() else {
            startActivity(Intent(this, DasboardActivity::class.java))
            finishAffinity()
        }
    }
}