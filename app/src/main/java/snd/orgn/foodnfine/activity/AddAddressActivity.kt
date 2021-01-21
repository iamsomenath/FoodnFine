package snd.orgn.foodnfine.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.modular_item_address_item_add_address.*
import kotlinx.android.synthetic.main.search_location_layout.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.application.FoodnFine.Companion.appSharedPreference
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackAddAddress
import snd.orgn.foodnfine.callbacks.CallbackAddressDetailsFromId
import snd.orgn.foodnfine.callbacks.CallbackGetAddress
import snd.orgn.foodnfine.constant.AppConstants
import snd.orgn.foodnfine.data.room.entity.AddressDetails
import snd.orgn.foodnfine.model.user_data.UserDataAddAddress
import snd.orgn.foodnfine.view_model.ActivityViewModel.AddAdressViewModel
import java.io.IOException
import java.util.*

class AddAddressActivity : BaseActivity(), CallbackAddAddress, CallbackGetAddress, CallbackAddressDetailsFromId {

    var ischeckHome = false
    var ischeckOffice = false
    var ischeckOther = false
    var isupdateAddress = false
    var wholeAddress: String? = null
    var seletedLocationType: String? = null
    var viewModel: AddAdressViewModel? = null
    private var userAddressId: String? = null
    private var category4: String? = null
    private var addressDetails: AddressDetails? = null

    var placeId1: String? = null
    var rootView: View? = null
    var queriedLocation: LatLng? = null

    var fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        ButterKnife.bind(this)
        textcheckLisner()
        if (!Places.isInitialized()) {
            Places.initialize(this, AppConstants.MAP_API)
        }
        addressDetails = AddressDetails()
        val intent = intent
        userAddressId = intent.getStringExtra(AppConstants.INTENT_STRING_ADDRESS_ID)
        category4 = intent.getStringExtra("category4")
        if (userAddressId == "") {
            tv_toolbar_title!!.text = "Add Address"
            tvBtn_text!!.text = "Save this Address"
            isupdateAddress = false
        } else {
            isupdateAddress = true
            tv_toolbar_title!!.text = "Update Address"
            tvBtn_text!!.text = "Update this Address"
        }
        layout_location!!.visibility = View.VISIBLE
        layout_saveAddress!!.visibility = View.VISIBLE
        initFields()
        setupOnClick()
        hideStatusBarcolor()
        initToolbar()
        rootView = findViewById<View>(android.R.id.content).rootView
        setupUI(rootView, this@AddAddressActivity)
    }

    private fun initToolbar() {
        iv_addAddress_back!!.setOnClickListener { v: View? -> finish() }
    }

    private fun toolBarTitleChange() {
        tv_toolbar_title!!.text = "Update Address"
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddAdressViewModel::class.java)
        viewModel!!.setCallbackgetAddressDetails(this)
        viewModel!!.setCallbackAddAddress(this)
        viewModel!!.setCallbackGetAddress(this)
    }

    override fun initFields() {
        if (appSharedPreference!!.currentLocation == "" || appSharedPreference!!.currentLocation.isEmpty()) {
            getCurrentLocation("1")
        } else {
            wholeAddress = appSharedPreference!!.currentLocation
            et_addAddress_location!!.setText(wholeAddress)
            addressSeparator()
        }
        saveDataAsInitialSeleted()
        initViewModel()
        //viewModel.getAddressDetails(userAddressId);
    }

    override fun setupOnClick() {
        iv_addAddress_back!!.setOnClickListener { v: View? ->
            if (category4 == "") {
                val intent = Intent(this, SavedAddressActivity::class.java)
                startActivity(intent)
            }
        }
        cv_search_location!!.setOnClickListener { v: View? -> findPlace() }
        tv_addAddress_home!!.setOnClickListener { v: View? ->
            ischeckHome = true
            seletedLocationType = "Home"
            tv_addAddress_home!!.setBackgroundResource(R.drawable.textview_rounded_seleleted)
            tv_addAddress_home!!.setTextColor(resources.getColor(R.color.white_background))
            tv_addAddress_office!!.setBackgroundResource(R.drawable.textview_rounded_unselected)
            tv_addAddress_office!!.setTextColor(resources.getColor(R.color.text_colo_black))
            tv_addAddress_home!!.setPadding(0, 6, 0, 6)
            tv_addAddress_office!!.setPadding(0, 6, 0, 6)
        }
        tv_addAddress_office!!.setOnClickListener { v: View? ->
            ischeckOffice = true
            seletedLocationType = "Work"
            tv_addAddress_home!!.setBackgroundResource(R.drawable.textview_rounded_unselected)
            tv_addAddress_office!!.setBackgroundResource(R.drawable.textview_rounded_seleleted)
            tv_addAddress_home!!.setTextColor(resources.getColor(R.color.text_colo_black))
            tv_addAddress_office!!.setTextColor(resources.getColor(R.color.white_background))
            tv_addAddress_home!!.setPadding(0, 6, 0, 6)
            tv_addAddress_office!!.setPadding(0, 6, 0, 6)
        }
        tv_addAddress_other!!.setOnClickListener { v: View? ->
            ischeckOther = true
            seletedLocationType = "Other"
            saveDataAsOtherSeleted()
        }
        cv_addAddressBtn!!.setOnClickListener { v: View? ->
            if (isupdateAddress) {
                if (checkValidation()) {
                    viewModel!!.updateAddress(getupdateAllDataResqust())
                }
            } else {
                if (checkValidation()) {
                    viewModel!!.saveAddress(saveAllDataResqust)
                }
            }
        }
        layout_currentLocation!!.setOnClickListener { v: View? -> getCurrentLocation("1") }
    }

    private val saveAllDataResqust: UserDataAddAddress
        private get() {
            val dataAddAddress = UserDataAddAddress()
            dataAddAddress.userId = appSharedPreference!!.userId
            dataAddAddress.location = et_addAddress_location!!.text.toString()
            dataAddAddress.building = et_addAddress_apartmentName!!.text.toString()
            dataAddAddress.house = et_addAddress_house_flat_no!!.text.toString()
            dataAddAddress.landmark = et_addAddress_landMark!!.text.toString()
            dataAddAddress.contactPerson = et_addAddress_contactPerson!!.text.toString()
            dataAddAddress.contactNumber = et_addAddress_contactNumber!!.text.toString()
            dataAddAddress.instruction = et_addAddress_intructionToReachLocation!!.text.toString()
            dataAddAddress.otherDescription = et_addAddress_otherLocation!!.text.toString()
            dataAddAddress.locationType = seletedLocationType
            return dataAddAddress
        }

    private fun getupdateAllDataResqust(): UserDataAddAddress {
        val dataAddAddress = UserDataAddAddress()
        dataAddAddress.userAddressId = userAddressId
        dataAddAddress.userId = appSharedPreference!!.userId
        dataAddAddress.location = et_addAddress_location!!.text.toString()
        dataAddAddress.building = et_addAddress_apartmentName!!.text.toString()
        dataAddAddress.house = et_addAddress_house_flat_no!!.text.toString()
        dataAddAddress.landmark = et_addAddress_landMark!!.text.toString()
        dataAddAddress.contactPerson = et_addAddress_contactPerson!!.text.toString()
        dataAddAddress.contactNumber = et_addAddress_contactNumber!!.text.toString()
        dataAddAddress.instruction = et_addAddress_intructionToReachLocation!!.text.toString()
        dataAddAddress.otherDescription = et_addAddress_otherLocation!!.text.toString()
        if (ischeckOther) {
            dataAddAddress.locationType = et_addAddress_otherLocation!!.text.toString()
        } else if (ischeckHome) {
            dataAddAddress.locationType = "Home"
        } else if (ischeckOffice) {
            dataAddAddress.locationType = "Work"
        }
        // dataAddAddress.setLocationType(seletedLocationType);
        return dataAddAddress
    }

    private val addressList: UserDataAddAddress
        private get() {
            val datagetAddress = UserDataAddAddress()
            datagetAddress.userId = appSharedPreference!!.userId
            return datagetAddress
        }

    private fun saveDataAsOtherSeleted() {
        layout_saveAs!!.visibility = View.GONE
        tv_saveAs!!.visibility = View.GONE
        text_input_otherLocation!!.visibility = View.VISIBLE
    }

    private fun saveDataAsInitialSeleted() {
        layout_saveAs!!.visibility = View.VISIBLE
        tv_saveAs!!.visibility = View.VISIBLE
        text_input_otherLocation!!.visibility = View.GONE
    }

    //    private void loadInitialFragment() {
    //        Fragment_AddAdress fragment_addAdress = new Fragment_AddAdress(this);
    //        getSupportFragmentManager().beginTransaction()
    //                .add(R.id.containerLayout_addAddress, fragment_addAdress, "fragmentAddAddress")
    //                .commit();
    //    }
    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    //    @Override
    //    public void onAddressAdded() {
    //
    //    }
    private fun checkValidation(): Boolean {
        var validated = false
        var validationcount = 0
        if (et_addAddress_house_flat_no!!.text.toString().trim { it <= ' ' } == "" || et_addAddress_house_flat_no!!.text!!.length == 0) {
            et_addAddress_house_flat_no!!.requestFocus()
            validated = false
            text_input_house_flat_no!!.error = "Enter house or flat no"
        } else {
            validated = true
            validationcount++
        }
        if (et_addAddress_apartmentName!!.text.toString() == "" || et_addAddress_apartmentName!!.text!!.isEmpty()) {
            et_addAddress_apartmentName!!.requestFocus()
            validated = false
            text_input_buliding_or_apatment_name!!.error = "Enter apartment name or building name"
        } else {
            validated = true
            validationcount++
        }
        if (et_addAddress_landMark!!.text.toString() == "" || et_addAddress_landMark!!.text!!.isEmpty()) {
            et_addAddress_landMark!!.requestFocus()
            validated = false
            text_input_landmark!!.error = "Enter landmark"
        } else {
            validated = true
            validationcount++
        }
        if (et_addAddress_intructionToReachLocation!!.text.toString() == "" || et_addAddress_intructionToReachLocation!!.text!!.isEmpty()) {
            et_addAddress_intructionToReachLocation!!.requestFocus()
            validated = false
            text_input_instruction_to_reach_location!!.error = "Enter instruction to reach location"
        } else {
            validated = true
            validationcount++
        }

        /*if ((et_addAddress_contactPerson.getText().equals("")) || (et_addAddress_contactPerson.getText().length() == 0)) {
            et_addAddress_contactPerson.requestFocus();
            validated = false;
            text_contact_person.setError("Enter Contact person ");
        } else {
            validated = true;
            validationcount++;
        }

        if ((et_addAddress_contactNumber.getText().equals("")) || (et_addAddress_contactNumber.getText().length() == 0)) {
            et_addAddress_contactNumber.requestFocus();
            validated = false;
            text_contact_number.setError("Enter Contact Number ");
        } else {
            validated = true;
            validationcount++;
        }*/if (ischeckHome || ischeckOffice || ischeckOther) {
            if (ischeckOther) {
                if (et_addAddress_otherLocation!!.text.toString() == "" || et_addAddress_otherLocation!!.text!!.isEmpty()) {
                    validated = false
                    text_input_otherLocation!!.error = "Enter save as a"
                } else {
                    validated = true
                }
            }
            validated = true
            validationcount++
        } else {
            Toast.makeText(this, "Click save as a", Toast.LENGTH_SHORT).show()
        }
        return if (validationcount == 5) {
            validationcount = 0
            validated
        } else {
            false
        }
    }

    private fun textcheckLisner() {
        et_addAddress_house_flat_no!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_input_house_flat_no!!.error = null
            }
        })
        et_addAddress_apartmentName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_input_buliding_or_apatment_name!!.error = null
            }
        })
        et_addAddress_landMark!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_input_landmark!!.error = null
            }
        })
        et_addAddress_intructionToReachLocation!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_input_instruction_to_reach_location!!.error = null
            }
        })
        et_addAddress_contactPerson!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_contact_person!!.error = null
            }
        })
        et_addAddress_contactNumber!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_contact_number!!.error = null
            }
        })
        et_addAddress_otherLocation!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                text_input_otherLocation!!.error = null
            }
        })
    }

    private fun getCurrentLocation(type: String) {
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        // Define a listener that responds to location updates
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                val lat = java.lang.Double.toString(location.latitude)
                val lon = java.lang.Double.toString(location.longitude)
                //Log.d("LATLONG", lat + " " + lon + "");
                try {
                    if (type == "1") {
                        GetAddress(lat, lon)
                    } else {
                    }
                } catch (ignored: Exception) {
                    //new CustomToast().Show_Toast(getActivity(), myview, "Can't fetch proper location!");
                }
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
    }

    fun GetAddress(lat: String, lon: String): String? {
        val geocoder = Geocoder(this, Locale.ENGLISH)
        var ret: String
        try {
            val addresses = geocoder.getFromLocation(lat.toDouble(), lon.toDouble(), 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                /*StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }*/
                //ret = strReturnedAddress.toString();
                ret = returnedAddress.getAddressLine(0)
                appSharedPreference!!.saveCurrentLocation(ret)
                wholeAddress = ret
                et_addAddress_location!!.setText(wholeAddress)
                addressSeparator()


                // sessionManager.update_address_billing(ret);
            } else {
                ret = "No Address returned!"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ret = "Can't get Address!"
            val snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "Please turn on GPS", Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.WHITE)
            snackbar.show()
        }
        return ret
    }

    fun findPlace() {
        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
        startActivityForResult(intent, 1)
    }

    // A place has been received; use requestCode to track the request.
    @SuppressLint("SetTextI16n")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                placeId1 = place.id
                queriedLocation = place.latLng
                //                String lat = Double.toString(place.getLatLng().latitude);
//                String lon = Double.toString(place.getLatLng().longitude);
//
//                getAddress(lat, lon);
                wholeAddress = place.address
                et_addAddress_location!!.setText(wholeAddress)
                addressSeparator()
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private fun addressSeparator() {
        val separated = wholeAddress!!.split(",".toRegex()).toTypedArray()
        try {
            et_addAddress_house_flat_no!!.setText(separated[0])
            et_addAddress_apartmentName!!.setText(separated[1] + separated[2])
            et_addAddress_landMark!!.setText(separated[3])
            et_addAddress_intructionToReachLocation!!.setText(separated[4])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSuccess() {
        Toast.makeText(this, "Address added Successfully ", Toast.LENGTH_SHORT).show()
        finish()
        //viewModel.getaddressList(getAddressList());
    }

    override fun onError(message: String) {}
    override fun onNetworkError(message: String) {}
    override fun onSuccessGetAddress(addressDetailsList: List<AddressDetails>) {}
    override fun onErrorGetAddress(message: String) {}
    override fun onSuccessAddressDetails(addressDetails: AddressDetails) {}
}