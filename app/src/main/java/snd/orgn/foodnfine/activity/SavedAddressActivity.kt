package snd.orgn.foodnfine.activity

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.adapter.activityAdapter.SaveAddressAdapter
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackGetAddress
import snd.orgn.foodnfine.callbacks.CallbackSaveAddressList
import snd.orgn.foodnfine.data.room.entity.AddressDetails
import snd.orgn.foodnfine.model.user_data.UserDataAddAddress
import snd.orgn.foodnfine.view_model.ActivityViewModel.AccountDetailsViewModel
import snd.orgn.foodnfine.view_model.ActivityViewModel.SavedAddressViewModel

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_saved_address.*

import java.util.ArrayList

import snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ADDRESS_ID

class SavedAddressActivity : BaseActivity(), CallbackSaveAddressList, CallbackGetAddress {

    lateinit var addressAdapter: SaveAddressAdapter
    lateinit var viewModel: SavedAddressViewModel
    lateinit var viewModel1: AccountDetailsViewModel
    private var addressDetails: List<AddressDetails>? = null

    private val addressList: UserDataAddAddress
        get() {
            val datagetAddress = UserDataAddAddress()
            datagetAddress.userId = FoodnFine.appSharedPreference!!.userId
            return datagetAddress
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_address)
        addressDetails = ArrayList()
        hideStatusBarcolor()
        initViewModel()
        initViewModelgetAddressList()
        initFields()
        setupOnClick()
    }

    override fun initFields() {
        initRecyclerSaveAddressView()
    }

    override fun setupOnClick() {
        iv_savedAddress_AddAddress!!.setOnClickListener { v -> gotoAddAddressView() }

        iv_saveAddress_back!!.setOnClickListener { v ->
            super.onBackPressed()
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SavedAddressViewModel::class.java)
        viewModel.setCallback(this)
    }


    private fun initViewModelgetAddressList() {
        viewModel1 = ViewModelProviders.of(this).get(AccountDetailsViewModel::class.java)
        viewModel1.setCallback(this)
    }

    private fun gotoAddAddressView() {
        val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra(INTENT_STRING_ADDRESS_ID, "")
        startActivity(intent)
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    private fun hideAddressLayout() {
        layout_saveAddress_fetchingData!!.visibility = View.GONE
        layout_noAddressFound!!.visibility = View.VISIBLE
        layout_saveAddressList!!.visibility = View.GONE
    }

    private fun showAddressLayout() {
        layout_saveAddress_fetchingData!!.visibility = View.GONE
        layout_noAddressFound!!.visibility = View.GONE
        layout_saveAddressList!!.visibility = View.VISIBLE
    }


    private fun initRecyclerSaveAddressView() {

        addressAdapter = SaveAddressAdapter(this, viewModel)
        //  myPostAdapter.setCallbackAddTocart(this);
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_savedAddressList!!.layoutManager = layoutManager
        rv_savedAddressList!!.adapter = addressAdapter
    }

    override fun onSuccessGetAddress(addressDetailsList: List<AddressDetails>) {
        addressAdapter.clearList()
        this.addressDetails = addressDetailsList
        if (addressDetails!!.isEmpty()) {
            hideAddressLayout()
        } else {
            showAddressLayout()
            addressAdapter.clearList()
            addressAdapter.addAddressDeatilsList(addressDetails)
            addressAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        setFetchingDataLayout()
        viewModel1.getaddressList(addressList)
    }

    override fun onSuccessDeleteAddress() {
        val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Address deleted successfully!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onErrorGetAddress(message: String) {
        val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun setFetchingDataLayout() {
        layout_saveAddressList.visibility = View.GONE
        layout_saveAddress_fetchingData!!.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
