package snd.orgn.foodnfine.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.SearchItemAdapter
import snd.orgn.foodnfine.application.DeliveryEverything
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.bottomSheetFragment.BottomSheetSelectItemFragment
import snd.orgn.foodnfine.callbacks.CallbackDeleteCartResponse
import snd.orgn.foodnfine.callbacks.CallbackSearchActivity
import snd.orgn.foodnfine.constant.AppConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.model.SearchResponseListPojo
import snd.orgn.foodnfine.rest.request.UserRequest
import snd.orgn.foodnfine.rest.response.RestResponseCart
import snd.orgn.foodnfine.view_model.SearchViewModel
import java.io.Serializable
import java.util.*

class SearchActivity : BaseActivity(), CallbackSearchActivity, CallbackDeleteCartResponse {

    lateinit var searchEditText: EditText
    lateinit var viewModel: SearchViewModel

    private lateinit var sessionManager: SessionManager
    private var type = "restaurant"
    private var bottomSheetSelectItemFragment: BottomSheetSelectItemFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        ButterKnife.bind(this)
        hideStatusBarcolor()
        initFields()
        initViewModel()
        setupOnClick()
        initBottomSheets()
    }

    override fun initFields() {
        setupUI(root_view, this)
        setupSearchView()
        sessionManager = SessionManager(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.setCallback(this)
    }

    override fun setupOnClick() {

        sv_productSearch_productSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                /*setFetchingDataLayout();
                viewModel.setSearchQuery(sv_productSearch_productSearch.getQuery().toString());
                return false;*/
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length == 0) {
                    setNoDataLayout()
                } else if (newText.length >= 3) {
                    setFetchingDataLayout()
                    viewModel.setSearchQuery(newText.toLowerCase())
                    //Log.d("SearchableActivity", "onQueryTextChange: " + newText.toLowerCase());
                }
                return true
                /*setFetchingDataLayout();
                viewModel.setSearchQuery(sv_productSearch_productSearch.getQuery().toString());
                return true;*/
            }
        })

        iv_back!!.setOnClickListener { v ->
            if (DeliveryEverything.getAppSharedPreference().itemQuantity.isEmpty()) {
                super.onBackPressed()
                overridePendingTransition(R.anim.right_in, R.anim.push_left_out)
                finish()
            } else {
                showBottomSheet();
            }
        }

        cart.setOnClickListener {
            val userRequest = UserRequest()
            userRequest.userId = DeliveryEverything.getAppSharedPreference().userId
            when {
                sessionManager.keyOrderType == "3" -> type = "restaurant"
                sessionManager.keyOrderType == "2" -> type = "grocery"
                sessionManager.keyOrderType == "5" -> type = "Swadesi Product"
                sessionManager.keyOrderType == "6" -> type = "Medicine Product"
                sessionManager.keyOrderType == "7" -> type = "Electronic Product"
            }
            userRequest.orderType = type
            viewModel.cartDetails(userRequest)
        }
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv_productSearch_productSearch!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchEditText = sv_productSearch_productSearch!!.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.text_colo_black))
        searchEditText.setHintTextColor(resources.getColor(R.color.hintTextColor))
        searchEditText.hint = resources.getString(R.string.search_hint_productSearch)
        searchEditText.textSize = 14f
        searchEditText.compoundDrawablePadding = 5
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setNoDataLayout() {
        rv_productSearch_productSearch!!.visibility = View.GONE
        layout_productSearch_noDataFound!!.visibility = View.VISIBLE
        layout_search_fetchingData!!.visibility = View.GONE
    }

    fun setFetchingDataLayout() {
        rv_productSearch_productSearch!!.visibility = View.GONE
        layout_productSearch_noDataFound!!.visibility = View.GONE
        layout_search_fetchingData!!.visibility = View.VISIBLE
    }

    fun setDataAvailableLayout() {
        rv_productSearch_productSearch!!.visibility = View.VISIBLE
        layout_productSearch_noDataFound!!.visibility = View.GONE
        layout_search_fetchingData!!.visibility = View.GONE
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    override fun onSuccess(searchList: ArrayList<SearchResponseListPojo>) {
        setDataAvailableLayout()
        //Log.d("TEST", "" + searchList.size)
        val adapter = SearchItemAdapter(this, searchList)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_productSearch_productSearch!!.layoutManager = layoutManager
        rv_productSearch_productSearch!!.adapter = adapter
    }

    override fun onError(message: String) {
        setNoDataLayout()
        Toast.makeText(this, "No product found.........", Toast.LENGTH_SHORT).show()
    }

    override fun onNetworkError(message: String) {
        setNoDataLayout()
        Toast.makeText(this, "Unable to search!!!", Toast.LENGTH_SHORT).show()
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

    override fun onCartDetailsSuccess(restResponse: RestResponseCart) {

        val cartDetails = restResponse
        DeliveryEverything.getAppSharedPreference().itemQuantity = cartDetails.sumcartCount!!.toString()
        DeliveryEverything.getAppSharedPreference().itemPrice = cartDetails.sumPrice!!.toString()
        when {
            sessionManager.keyOrderType == "3" -> type = "restaurant"
            sessionManager.keyOrderType == "2" -> type = "grocery"
            sessionManager.keyOrderType == "5" -> type = "Swadesi Product"
            sessionManager.keyOrderType == "6" -> type = "Medicine Product"
            sessionManager.keyOrderType == "7" -> type = "Electronic Product"
        }
        val intent = Intent(this@SearchActivity, ConfirmOrderActivity::class.java)
        intent.putExtra(AppConstants.INTENT_STRING_ORDER_TYPE, type)
        intent.putExtra(AppConstants.INTENT_STRING_CART_DETAIL, cartDetails as Serializable)
        startActivity(intent)
    }

    override fun onCartDetailsError(message: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), message!!, Snackbar.LENGTH_LONG).show()
    }

    override fun onCartDetailsNetworkError(message: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), message!!, Snackbar.LENGTH_LONG).show()
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
}