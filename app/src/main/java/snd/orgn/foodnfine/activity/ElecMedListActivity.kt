package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_elictronic_medicine_list.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.ElecMedListAdapter
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackElecMedList
import snd.orgn.foodnfine.model.data_item.AllElectMedPojo
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.view_model.ActivityViewModel.ElecMedListModelView
import java.util.*

class ElecMedListActivity : BaseActivity(), CallbackElecMedList {

    lateinit var loadingDialog: LoadingDialog

    lateinit var listAdapter: ElecMedListAdapter
    lateinit var viewModel: ElecMedListModelView

    private var allListForNearMe: MutableList<AllElectMedPojo>? = null
    private var allList: List<AllElectMedPojo>? = null
    private val NearMe1Km: MutableList<AllElectMedPojo> = ArrayList()
    private val NearMeUpto5: MutableList<AllElectMedPojo> = ArrayList()
    private val NearMeUpto10: MutableList<AllElectMedPojo> = ArrayList()
    private val NearMeMorethan10: MutableList<AllElectMedPojo> = ArrayList()
    private var locationIsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elictronic_medicine_list)
        //ButterKnife.bind(this)
        setFetchingDataLayout()
        hideStatusBarcolor()
        initFields()
        setupOnClick()
    }

    @SuppressLint("SetTextI18n")
    override fun initFields() {
        initViewModel()
        loadingDialog = LoadingDialog(this)
        loadingDialog.showDialog()
        viewModel.getLists(intent.getStringExtra("TYPE"))
        initRecyclerRestaurantList()

        (findViewById<View>(R.id.switch1) as Switch).setOnCheckedChangeListener { buttonView, isChecked ->
            //Toast.makeText(this, isChecked + "", Toast.LENGTH_SHORT).show();
            loadingDialog.showDialog()
            Handler().postDelayed({
                loadingDialog.hideDialog()
                if (isChecked) {
                    if (allListForNearMe!!.size != 0) {
                        setDataAvailableLayout()
                        tv_numberOfStore!!.text = "(" + allListForNearMe!!.size + " Stores)"
                        listAdapter.clearList()
                        listAdapter.addList(allListForNearMe)
                        listAdapter.notifyDataSetChanged()
                    } else {
                        tv_numberOfStore!!.text = "(" + allListForNearMe!!.size + " Stores)"
                        setNoDataLayout()
                        if (locationIsSet) {
                            val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                                    "Oh sorry! No stores found!", Snackbar.LENGTH_LONG)
                            snackbar.show()
                        } else {
                            val builder = AlertDialog.Builder(this)
                            builder.setMessage("Oh sorry! No store found! Your location was not set properly. "
                                    + "Please select your current location to find `Near Me` stores.")
                            builder.setPositiveButton("OK") { dialog, id -> dialog.dismiss() }
                            builder.setCancelable(false)
                            builder.show()
                            builder.create()
                        }
                    }
                } else {
                    setDataAvailableLayout()
                    tv_numberOfStore!!.text = "(" + allList!!.size + " Stores)"
                    listAdapter.clearList()
                    listAdapter.addList(allList)
                    listAdapter.notifyDataSetChanged()
                }
            }, 1000)
        }

        if (intent.getStringExtra("TYPE").equals("Medicine Store", ignoreCase = true)) {
            tv_name!!.text = "Medicine Stores"
        } else {
            tv_name!!.text = "Electronic Stores"
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ElecMedListModelView::class.java)
        viewModel.setCallback(this)
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    private fun initRecyclerRestaurantList() {

        listAdapter = ElecMedListAdapter(this)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_recyclerViewElect!!.layoutManager = layoutManager
        rv_recyclerViewElect!!.adapter = listAdapter
    }

    override fun setupOnClick() {
        iv_back!!.setOnClickListener { v ->
            super.onBackPressed()
            finish()
        }
        val filter = findViewById<ImageView>(R.id.filter)
        filter.setOnClickListener { v: View? ->
            val popupMenu = PopupMenu(this@ElecMedListActivity, filter)
            popupMenu.menuInflater.inflate(R.menu.popup_menu_pan, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.header1 -> getNewList(NearMe1Km)
                    R.id.header2 -> getNewList(NearMeUpto5)
                    R.id.header3 -> getNewList(NearMeUpto10)
                    R.id.header4 -> getNewList(NearMeMorethan10)
                }
                return@setOnMenuItemClickListener false
            }
            popupMenu.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getNewList(allList: List<AllElectMedPojo>) {
        if (allList.isNotEmpty()) {
            setDataAvailableLayout()
            tv_numberOfStore!!.text = "(" + allList.size + " Stores)"
            listAdapter.clearList()
            listAdapter.addList(allList)
            listAdapter.notifyDataSetChanged()
        } else {
            tv_numberOfStore!!.text = "(" + allList.size + " Stores)"
            setNoDataLayout()
            if (locationIsSet) {
                val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Oh sorry! No stores found!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Oh sorry! No store found! Your location was not set properly. "
                        + "Please select your current location to find `Near Me` stores.")
                builder.setPositiveButton("OK") { dialog, id -> dialog.dismiss() }
                builder.setCancelable(false)
                builder.show()
                builder.create()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(lists: List<AllElectMedPojo>) {
        loadingDialog.hideDialog()
        allList = ArrayList()
        allList = lists
        if (lists.isNotEmpty()) {
            setDataAvailableLayout()
            tv_numberOfStore!!.text = "(" + lists.size + " Stores)"
            listAdapter.clearList()
            listAdapter.addList(lists)
            listAdapter.notifyDataSetChanged()

            allListForNearMe = ArrayList()
            try {
                for (i in lists.indices) {
                    if (lists[i].latitude != "0") {
                        val startPoint = Location("locationA")
                        startPoint.latitude = java.lang.Double.parseDouble(Objects.requireNonNull<String>(lists[i].latitude))
                        startPoint.longitude = java.lang.Double.parseDouble(Objects.requireNonNull<String>(lists[i].longitude))

                        val endPoint = Location("locationA")
                        endPoint.latitude = java.lang.Double.parseDouble(FoodnFine.appSharedPreference!!.latitude)
                        endPoint.longitude = java.lang.Double.parseDouble(FoodnFine.appSharedPreference!!.longitude)

                        val distance = startPoint.distanceTo(endPoint).toDouble()
                        val myDistance = java.lang.Double.parseDouble(getString(R.string.distance))

                        if (distance <= myDistance) allListForNearMe!!.add(lists[i])
                        if (distance < 1.0) NearMe1Km.add(lists.get(i))
                        if (distance >= 1.0 && distance < 5.0) NearMeUpto5.add(lists.get(i))
                        if (distance >= 5.0 && distance < 10.0) NearMeUpto10.add(lists.get(i))
                        if (distance > 10.0) NearMeMorethan10.add(lists.get(i))

                        //Log.d("DISTANCE", "" + distance / 1000);
                        //Log.d("DISTANCE", endPoint.getLatitude() + " " + endPoint.getLongitude());
                        //Log.d("DISTANCE", startPoint.getLatitude() + " " + startPoint.getLongitude());
                    } else {
                        allListForNearMe!!.add(lists[i])
                    }
                }
                locationIsSet = true
            } catch (e: Exception) {
                locationIsSet = false
            }
        } else {
            setNoDataLayout()
            tv_numberOfStore!!.text = "(" + lists.size + " Stores)"
        }
    }

    override fun onError(message: String) {
        loadingDialog.hideDialog()
        setNoDataLayout()
        tv_numberOfStore!!.text = ""
        val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onNetworkError(message: String) {
        loadingDialog.hideDialog()
        setNoDataLayout()
        tv_numberOfStore!!.text = ""
        val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun setNoDataLayout() {
        rv_recyclerViewElect!!.visibility = View.GONE
        if (intent.getStringExtra("TYPE").equals("Medicine Store", ignoreCase = true)) {
            med_no_list!!.visibility = View.VISIBLE
        } else {
            elect_no_list!!.visibility = View.VISIBLE
        }
        layout_fetchingData!!.visibility = View.GONE
    }

    private fun setFetchingDataLayout() {
        rv_recyclerViewElect!!.visibility = View.GONE
        //layout_notFound.setVisibility(View.GONE);
        if (intent.getStringExtra("TYPE").equals("Medicine Store", ignoreCase = true)) {
            med_no_list!!.visibility = View.GONE
        } else {
            elect_no_list!!.visibility = View.GONE
        }
        layout_fetchingData!!.visibility = View.VISIBLE
    }

    private fun setDataAvailableLayout() {
        rv_recyclerViewElect!!.visibility = View.VISIBLE
        //layout_notFound.setVisibility(View.GONE);
        if (intent.getStringExtra("TYPE").equals("Medicine Store", ignoreCase = true)) {
            med_no_list!!.visibility = View.GONE
        } else {
            elect_no_list!!.visibility = View.GONE
        }
        layout_fetchingData!!.visibility = View.GONE
    }


    override fun onBackPressed(){
        super.onBackPressed()
        finish()
    }
}
