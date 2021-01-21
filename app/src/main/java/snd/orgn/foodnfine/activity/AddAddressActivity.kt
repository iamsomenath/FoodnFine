package snd.orgn.foodnfine.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackAddAddress;
import snd.orgn.foodnfine.callbacks.CallbackAddressDetailsFromId;
import snd.orgn.foodnfine.callbacks.CallbackGetAddress;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.model.user_data.UserDataAddAddress;
import snd.orgn.foodnfine.view_model.ActivityViewModel.AddAdressViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ADDRESS_ID;
import static snd.orgn.foodnfine.constant.AppConstants.MAP_API;

public class AddAddressActivity extends BaseActivity implements CallbackAddAddress, CallbackGetAddress, CallbackAddressDetailsFromId {

    @BindView(R.id.containerLayout_addAddress)
    LinearLayout containerLayout_addAddress;
    @BindView(R.id.layout_currentLocation)
    LinearLayout layout_currentLocation;
    @BindView(R.id.layout_saveAs)
    LinearLayout layout_saveAs;
    @BindView(R.id.iv_addAddress_back)
    ImageView iv_addAddress_back;
    @BindView(R.id.cv_search_location)
    CardView cv_search_location;
    @BindView(R.id.et_addAddress_apartmentName)
    TextInputEditText et_addAddress_apartmentName;
    @BindView(R.id.et_addAddress_otherLocation)
    TextInputEditText et_addAddress_otherLocation;
    @BindView(R.id.et_addAddress_contactPerson)
    TextInputEditText et_addAddress_contactPerson;
    @BindView(R.id.et_addAddress_intructionToReachLocation)
    TextInputEditText et_addAddress_intructionToReachLocation;
    @BindView(R.id.et_addAddress_landMark)
    TextInputEditText et_addAddress_landMark;
    @BindView(R.id.et_addAddress_house_flat_no)
    TextInputEditText et_addAddress_house_flat_no;
    @BindView(R.id.et_addAddress_location)
    TextInputEditText et_addAddress_location;
    @BindView(R.id.et_addAddress_contactNumber)
    TextInputEditText et_addAddress_contactNumber;
    @BindView(R.id.text_contact_number)
    TextInputLayout text_contact_number;
    @BindView(R.id.text_input_location)
    TextInputLayout text_input_location;
    @BindView(R.id.text_input_house_flat_no)
    TextInputLayout text_input_house_flat_no;
    @BindView(R.id.text_input_buliding_or_apatment_name)
    TextInputLayout text_input_buliding_or_apatment_name;
    @BindView(R.id.text_input_landmark)
    TextInputLayout text_input_landmark;
    @BindView(R.id.text_input_instruction_to_reach_location)
    TextInputLayout text_input_instruction_to_reach_location;
    @BindView(R.id.text_contact_person)
    TextInputLayout text_contact_person;
    @BindView(R.id.text_input_otherLocation)
    TextInputLayout text_input_otherLocation;
    @BindView(R.id.tv_addAddress_home)
    TextView tv_addAddress_home;
    @BindView(R.id.tv_addAddress_office)
    TextView tv_addAddress_office;
    @BindView(R.id.tv_addAddress_other)
    TextView tv_addAddress_other;
    @BindView(R.id.tv_saveAs)
    TextView tv_saveAs;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.tvBtn_text)
    TextView tvBtn_text;
    @BindView(R.id.cv_addAddressBtn)
    CardView cv_addAddressBtn;
    @BindView(R.id.layout_location)
    LinearLayout layout_location;
    @BindView(R.id.layout_saveAddress)
    LinearLayout layout_saveAddress;
    boolean ischeckHome = false;
    boolean ischeckOffice = false;
    boolean ischeckOther = false;
    boolean isupdateAddress = false;
    String wholeAddress;
    String seletedLocationType;
    AddAdressViewModel viewModel;
    private String userAddressId;
    private String category4;
    private AddressDetails addressDetails;
    String currentLatitude;
    String currentLongitude;
    String placeId1;
    View rootView;
    LatLng queriedLocation;
    ArrayList<String> location_list;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        textcheckLisner();
        if (!Places.isInitialized()) {
            Places.initialize(this, MAP_API);
        }
        addressDetails = new AddressDetails();

        Intent intent = getIntent();
        userAddressId = intent.getStringExtra(INTENT_STRING_ADDRESS_ID);
        category4 = intent.getStringExtra("category4");
        if (userAddressId.equals("")) {
            tv_toolbar_title.setText("Add Address");
            tvBtn_text.setText("Save this Address");
            isupdateAddress = false;
        } else {
            isupdateAddress = true;
            tv_toolbar_title.setText("Update Address");
            tvBtn_text.setText("Update this Address");
        }

        layout_location.setVisibility(View.VISIBLE);
        layout_saveAddress.setVisibility(View.VISIBLE);
        initFields();

        setupOnClick();
        hideStatusBarcolor();
        initToolbar();
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, AddAddressActivity.this);
    }

    private void initToolbar() {
        iv_addAddress_back.setOnClickListener(v -> {
            finish();
        });
    }

    private void toolBarTitleChange() {
        tv_toolbar_title.setText("Update Address");
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddAdressViewModel.class);
        viewModel.setCallbackgetAddressDetails(this);
        viewModel.setCallbackAddAddress(this);
        viewModel.setCallbackGetAddress(this);
    }

    @Override
    public void initFields() {
        if (FoodnFine.getAppSharedPreference().getCurrentLocation().equals("") || FoodnFine.getAppSharedPreference().getCurrentLocation().length() == 0) {
            getCurrentLocation("1");
        } else {

            wholeAddress = FoodnFine.getAppSharedPreference().getCurrentLocation();
            et_addAddress_location.setText(wholeAddress);
            addressSeparator();
        }
        saveDataAsInitialSeleted();
        initViewModel();
        //viewModel.getAddressDetails(userAddressId);
    }

    @Override
    public void setupOnClick() {
        iv_addAddress_back.setOnClickListener(v -> {
            if (category4.equals("")) {
                Intent intent = new Intent(this, SavedAddressActivity.class);
                startActivity(intent);
            }
        });
        cv_search_location.setOnClickListener(v -> {
            findPlace();
        });
        tv_addAddress_home.setOnClickListener(v -> {
            ischeckHome = true;
            seletedLocationType = "Home";
            tv_addAddress_home.setBackgroundResource(R.drawable.textview_rounded_seleleted);
            tv_addAddress_home.setTextColor(getResources().getColor(R.color.white_background));
            tv_addAddress_office.setBackgroundResource(R.drawable.textview_rounded_unselected);
            tv_addAddress_office.setTextColor(getResources().getColor(R.color.text_colo_black));
            tv_addAddress_home.setPadding(0, 6, 0, 6);
            tv_addAddress_office.setPadding(0, 6, 0, 6);
        });
        tv_addAddress_office.setOnClickListener(v -> {
            ischeckOffice = true;
            seletedLocationType = "Work";
            tv_addAddress_home.setBackgroundResource(R.drawable.textview_rounded_unselected);
            tv_addAddress_office.setBackgroundResource(R.drawable.textview_rounded_seleleted);
            tv_addAddress_home.setTextColor(getResources().getColor(R.color.text_colo_black));
            tv_addAddress_office.setTextColor(getResources().getColor(R.color.white_background));
            tv_addAddress_home.setPadding(0, 6, 0, 6);
            tv_addAddress_office.setPadding(0, 6, 0, 6);
        });
        tv_addAddress_other.setOnClickListener(v -> {
            ischeckOther = true;
            seletedLocationType = "Other";
            saveDataAsOtherSeleted();
        });
        cv_addAddressBtn.setOnClickListener(v -> {
            if (isupdateAddress) {
                if (checkValidation()) {
                    viewModel.updateAddress(getupdateAllDataResqust());
                }
            } else {
                if (checkValidation()) {
                    viewModel.saveAddress(getSaveAllDataResqust());
                }
            }
        });
        layout_currentLocation.setOnClickListener(v -> {
            getCurrentLocation("1");
        });
    }

    private UserDataAddAddress getSaveAllDataResqust() {
        UserDataAddAddress dataAddAddress = new UserDataAddAddress();
        dataAddAddress.setUserId(FoodnFine.getAppSharedPreference().getUserId());
        dataAddAddress.setLocation(String.valueOf(et_addAddress_location.getText()));
        dataAddAddress.setBuilding(String.valueOf(et_addAddress_apartmentName.getText()));
        dataAddAddress.setHouse(String.valueOf(et_addAddress_house_flat_no.getText()));
        dataAddAddress.setLandmark(String.valueOf(et_addAddress_landMark.getText()));
        dataAddAddress.setContactPerson(String.valueOf(et_addAddress_contactPerson.getText()));
        dataAddAddress.setContactNumber(String.valueOf(et_addAddress_contactNumber.getText()));
        dataAddAddress.setInstruction(String.valueOf(et_addAddress_intructionToReachLocation.getText()));
        dataAddAddress.setOtherDescription(String.valueOf(et_addAddress_otherLocation.getText()));
        dataAddAddress.setLocationType(seletedLocationType);

        return dataAddAddress;
    }


    private UserDataAddAddress getupdateAllDataResqust() {
        UserDataAddAddress dataAddAddress = new UserDataAddAddress();
        dataAddAddress.setUserAddressId(userAddressId);
        dataAddAddress.setUserId(FoodnFine.getAppSharedPreference().getUserId());
        dataAddAddress.setLocation(String.valueOf(et_addAddress_location.getText()));
        dataAddAddress.setBuilding(String.valueOf(et_addAddress_apartmentName.getText()));
        dataAddAddress.setHouse(String.valueOf(et_addAddress_house_flat_no.getText()));
        dataAddAddress.setLandmark(String.valueOf(et_addAddress_landMark.getText()));
        dataAddAddress.setContactPerson(String.valueOf(et_addAddress_contactPerson.getText()));
        dataAddAddress.setContactNumber(String.valueOf(et_addAddress_contactNumber.getText()));
        dataAddAddress.setInstruction(String.valueOf(et_addAddress_intructionToReachLocation.getText()));
        dataAddAddress.setOtherDescription(String.valueOf(et_addAddress_otherLocation.getText()));
        if (ischeckOther) {
            dataAddAddress.setLocationType(String.valueOf(et_addAddress_otherLocation.getText()));
        } else if (ischeckHome) {
            dataAddAddress.setLocationType("Home");
        } else if (ischeckOffice) {
            dataAddAddress.setLocationType("Work");
        }
        // dataAddAddress.setLocationType(seletedLocationType);

        return dataAddAddress;
    }

    private UserDataAddAddress getAddressList() {
        UserDataAddAddress datagetAddress = new UserDataAddAddress();
        datagetAddress.setUserId(FoodnFine.getAppSharedPreference().getUserId());
        return datagetAddress;
    }

    private void saveDataAsOtherSeleted() {
        layout_saveAs.setVisibility(View.GONE);
        tv_saveAs.setVisibility(View.GONE);
        text_input_otherLocation.setVisibility(View.VISIBLE);
    }

    private void saveDataAsInitialSeleted() {
        layout_saveAs.setVisibility(View.VISIBLE);
        tv_saveAs.setVisibility(View.VISIBLE);
        text_input_otherLocation.setVisibility(View.GONE);
    }

//    private void loadInitialFragment() {
//        Fragment_AddAdress fragment_addAdress = new Fragment_AddAdress(this);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.containerLayout_addAddress, fragment_addAdress, "fragmentAddAddress")
//                .commit();
//    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

//    @Override
//    public void onAddressAdded() {
//
//    }


    private boolean checkValidation() {
        boolean validated = false;
        int validationcount = 0;
        if ((et_addAddress_house_flat_no.getText().toString().trim().equals("")) || (et_addAddress_house_flat_no.getText().length() == 0)) {
            et_addAddress_house_flat_no.requestFocus();
            validated = false;
            text_input_house_flat_no.setError("Enter house or flat no");
        } else {
            validated = true;
            validationcount++;
        }
        if ((et_addAddress_apartmentName.getText().equals("")) || (et_addAddress_apartmentName.getText().length() == 0)) {
            et_addAddress_apartmentName.requestFocus();
            validated = false;
            text_input_buliding_or_apatment_name.setError("Enter apartment name or building name");
        } else {
            validated = true;
            validationcount++;
        }

        if ((et_addAddress_landMark.getText().equals("")) || (et_addAddress_landMark.getText().length() == 0)) {
            et_addAddress_landMark.requestFocus();
            validated = false;
            text_input_landmark.setError("Enter landmark");
        } else {
            validated = true;
            validationcount++;
        }
        if ((et_addAddress_intructionToReachLocation.getText().equals("")) || (et_addAddress_intructionToReachLocation.getText().length() == 0)) {
            et_addAddress_intructionToReachLocation.requestFocus();
            validated = false;
            text_input_instruction_to_reach_location.setError("Enter instruction to reach location");
        } else {
            validated = true;
            validationcount++;
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
        }*/

        if (ischeckHome || ischeckOffice || ischeckOther) {

            if (ischeckOther) {
                if ((et_addAddress_otherLocation.getText().equals("")) || (et_addAddress_otherLocation.getText().length() == 0)) {
                    validated = false;
                    text_input_otherLocation.setError("Enter save as a");
                } else {
                    validated = true;

                }
            }
            validated = true;
            validationcount++;

        } else {

            Toast.makeText(this, "Click save as a", Toast.LENGTH_SHORT).show();
        }

        if (validationcount == 5) {
            validationcount = 0;
            return validated;
        } else {
            return false;
        }
    }

    private void textcheckLisner() {
        et_addAddress_house_flat_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                text_input_house_flat_no.setError(null);


            }
        });


        et_addAddress_apartmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_input_buliding_or_apatment_name.setError(null);


            }
        });
        et_addAddress_landMark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_input_landmark.setError(null);


            }
        });
        et_addAddress_intructionToReachLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_input_instruction_to_reach_location.setError(null);


            }
        });
        et_addAddress_contactPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_contact_person.setError(null);


            }
        });

        et_addAddress_contactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_contact_number.setError(null);


            }
        });


        et_addAddress_otherLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text_input_otherLocation.setError(null);


            }
        });
    }


    private void getCurrentLocation(final String type) {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                String lat = Double.toString(location.getLatitude());
                String lon = Double.toString(location.getLongitude());
                //Log.d("LATLONG", lat + " " + lon + "");
                try {
                    if (type.equals("1")) {

                        GetAddress(lat, lon);


                    } else {

                    }
                } catch (Exception ignored) {
                    //new CustomToast().Show_Toast(getActivity(), myview, "Can't fetch proper location!");
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
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
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }


    public String GetAddress(String lat, String lon) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        String ret;
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat),
                    Double.parseDouble(lon), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                /*StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }*/
                //ret = strReturnedAddress.toString();
                ret = returnedAddress.getAddressLine(0);
                FoodnFine.getAppSharedPreference().saveCurrentLocation(ret);
                wholeAddress = ret;
                et_addAddress_location.setText(wholeAddress);
                addressSeparator();


                // sessionManager.update_address_billing(ret);
            } else {
                ret = "No Address returned!";
            }
        } catch (IOException e) {
            e.printStackTrace();
            ret = "Can't get Address!";

            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Please turn on GPS", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.WHITE);
            snackbar.show();
        }
        return ret;
    }


    public void findPlace() {

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, 1);
    }


    // A place has been received; use requestCode to track the request.
    @SuppressLint("SetTextI16n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                placeId1 = place.getId();
                queriedLocation = place.getLatLng();
//                String lat = Double.toString(place.getLatLng().latitude);
//                String lon = Double.toString(place.getLatLng().longitude);
//
//                getAddress(lat, lon);
                wholeAddress = place.getAddress();
                et_addAddress_location.setText(wholeAddress);
                addressSeparator();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private void addressSeparator() {

        String[] separated = wholeAddress.split(",");
        try {
            et_addAddress_house_flat_no.setText(separated[0]);
            et_addAddress_apartmentName.setText(separated[1] + separated[2]);
            et_addAddress_landMark.setText(separated[3]);
            et_addAddress_intructionToReachLocation.setText(separated[4]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Address added Successfully ", Toast.LENGTH_SHORT).show();
        finish();
        //viewModel.getaddressList(getAddressList());
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onNetworkError(String message) {

    }

    @Override
    public void onSuccessGetAddress(List<AddressDetails> addressDetailsList) {

    }

    @Override
    public void onErrorGetAddress(String message) {

    }

    @Override
    public void onSuccessAddressDetails(AddressDetails addressDetails) {

    }
}
