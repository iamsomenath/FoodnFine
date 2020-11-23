package snd.orgn.foodnfine.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.ImageSliderAdaper;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.callbacks.CallbackgetAllPackageList;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.view_model.FragmentViewModel.PackageListViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.MAP_API;

public class DasboardActivity extends BaseActivity implements CallbackgetAllPackageList, CallbackGetChargesInKM {

    private TextView mTextMessage;
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.iv_dashboard_location)
    ImageView iv_dashboard_location;
    @BindView(R.id.iv_dashboard_userProfile)
    ImageView iv_dashboard_userProfile;
    @BindView(R.id.tv_dashboard_address)
    TextView tv_dashboard_address;
    private boolean doubleBackToExitPressedOnce;
    @BindView(R.id.bottomNavigation_dashboard)
    BottomNavigationViewEx navigation;
    @BindView(R.id.iv_dashboard_otherRestaurant)
    LinearLayout iv_dashboard_otherRestaurant;
    @BindView(R.id.iv_dashboard_otherStore)
    LinearLayout iv_dashboard_otherStore;
    //@BindView(R.id.iv_dashboard_send_package)
    LinearLayout iv_dashboard_send_package;
    @BindView(R.id.layout_btnOfficeBoyMaidService)
    LinearLayout layout_btnOfficeBoyMaidService;
    @BindView(R.id.iv_dashboard_grocery)
    LinearLayout iv_dashboard_grocery;
    @BindView(R.id.iv_dashboard_swadesi)
    LinearLayout iv_dashboard_swadesi;
    //@BindView(R.id.iv_dashboard_electronics)
    LinearLayout iv_dashboard_electronics;
    @BindView(R.id.iv_dashboard_medicine)
    LinearLayout iv_dashboard_medicine;
    String currentLatitude = "28.7041";
    String currentLongitude = "77.1025";
    PackageListViewModel viewModel;
    String placeId1;
    LatLng queriedLocation;
    ArrayList<String> location_list;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
    View rootView;
    SessionManager sessionManager;

    public static int chargesInDoubleInOneKm = 0;
    public static int chargesInDoubleInFiveKm = 0;
    public static int chargesInDoubleInTenKm = 0;
    public static int chargesInDoubleInGrater10Km = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, DasboardActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sessionManager = new SessionManager(this);
        //Toast.makeText(this, DeliveryEverything.getAppSharedPreference().getDevKey(), Toast.LENGTH_SHORT).show();
        //Log.d("NEW_TOKEN", DeliveryEverything.getAppSharedPreference().getDevKey());
        //multiDexEnabled true

        if (!Places.isInitialized()) {
            Places.initialize(this, MAP_API);
        }

        initViewModel();
        initFields();
        if (DeliveryEverything.getAppSharedPreference().getCurrentLocation().equals("") || DeliveryEverything.getAppSharedPreference().getCurrentLocation().length() == 0) {
            //featchCurrentLocation();
            location();
        } else {
            location();
            tv_dashboard_address.setText(DeliveryEverything.getAppSharedPreference().getCurrentLocation());
        }

        setupOnClick();
    }

    @Override
    public void initFields() {
        setupContentView();
        initImageSlider();
        viewModel.getPackageList();
        viewModel.getKMCharges();
    }

    @Override
    public void onResume(){
        super.onResume();
        setupContentView();
    }

    @Override
    public void setupOnClick() {
        iv_dashboard_userProfile.setOnClickListener(v -> {
            goToMyProfle();
        });
        iv_dashboard_location.setOnClickListener(v -> {
            // goToLocation();
            findPlace();
        });
        tv_dashboard_address.setOnClickListener(v -> {
            findPlace();
        });
        iv_dashboard_send_package.setOnClickListener(v -> {
            gotoSendPackage();
        });
        iv_dashboard_otherStore.setOnClickListener(v -> {
            gotoOtherStore();
        });

        iv_dashboard_otherRestaurant.setOnClickListener(v -> {
            goToRestaruent();
        });
        layout_btnOfficeBoyMaidService.setOnClickListener(v -> {
            goToOfficeBoyOrMaidService();
        });
        iv_dashboard_grocery.setOnClickListener(v->{
            gotoGrocery();
        });
        iv_dashboard_swadesi.setOnClickListener(v->{
            //Intent intent = new Intent(DasboardActivity.this, SwadesiProductActivity.class);
            // for Swadesi product
            DeliveryEverything.getAppSharedPreference().saveDeliveryCost("0");

            Intent intent = new Intent(DasboardActivity.this, NewSwadesiProductActivity.class);
            intent.putExtra("type", "SWADESI");
            new SessionManager(this).setShopId("0");
            sessionManager.setKeyOrderType("5");//SWADESI
            startActivity(intent);
        });
        iv_dashboard_electronics.setOnClickListener(v -> {
            Intent intent = new Intent(DasboardActivity.this, ElecMedListActivity.class);
            intent.putExtra("TYPE", "Electronic Store");
            sessionManager.setKeyOrderType("7");//ELECTRONIC
            startActivity(intent);
        });
        iv_dashboard_medicine.setOnClickListener(v -> {
            Intent intent = new Intent(DasboardActivity.this, ElecMedListActivity.class);
            intent.putExtra("TYPE", "Medicine Store");
            sessionManager.setKeyOrderType("6");//MEDICINE
            startActivity(intent);
        });
    }

    private void setupContentView() {

        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setCurrentItem(0);
        //navigation.setTextSize(10);
        navigation.setTextVisibility(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.navigation_home:
                break;
            case R.id.navigation_search:
                goToSearch();
                break;
            case R.id.navigation_order:
                goToOrder();
                break;
            case R.id.navigation_wallet:
                goToWallet();
                break;
        }
        return true;
    };

    private void initImageSlider() {
        imageSlider.setSliderAdapter(new ImageSliderAdaper(this));
        //imageSlider.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using 	  SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setScrollTimeInSec(5); //set scroll delay in seconds :
        imageSlider.startAutoCycle();
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            exitApp();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> {
            doubleBackToExitPressedOnce = false;
        }, 2000);
    }

    private void exitApp() {
        Intent intent = new Intent(this, LoginMobileNoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    private void goToMyProfle() {
        Intent intent = new Intent(this, AccountDetailsActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
    }

    private void goToLocation() {
        Intent intent = new Intent(this, SelectLocationActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
    }

    private void goToOrder() {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
        finish();
    }

    private void goToWallet() {
        Intent intent = new Intent(this, WalletActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
    }

    private void goToSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
    }


    public void findPlace() {

        /*Location startPoint = new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(currentLatitude));
        startPoint.setLongitude(Double.parseDouble(currentLongitude));
        LatLng latlon1 = getLocation(startPoint.getLongitude(), startPoint.getLatitude(), 50000);
        Location endPoint = new Location("locationA");
        endPoint.setLatitude(latlon1.latitude);
        endPoint.setLongitude(latlon1.longitude);

        // Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(endPoint.getLatitude(), endPoint.getLongitude()),
                new LatLng(startPoint.getLatitude(), startPoint.getLongitude()));*/

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                //.setLocationBias(bounds)
                //.setTypeFilter(TypeFilter.ADDRESS)
                .setCountry("IN")
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
                //String lat = Double.toString(place.getLatLng().latitude);
                //String lon = Double.toString(place.getLatLng().longitude);
                //Log.d("LATLON", lat + " " + lon);
                //getAddress(lat, lon);
                tv_dashboard_address.setText(place.getAddress());
                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(place.getAddress());
                DeliveryEverything.getAppSharedPreference().saveLatitude(Double.toString(place.getLatLng().latitude));
                DeliveryEverything.getAppSharedPreference().saveLongitude(Double.toString(place.getLatLng().longitude));

                /*Location startPoint = new Location("locationA");
                startPoint.setLatitude(place.getLatLng().latitude);
                startPoint.setLongitude(place.getLatLng().longitude);

                Location endPoint = new Location("locationA");
                endPoint.setLatitude(22.631041);
                endPoint.setLongitude(88.378356);

                double distance = startPoint.distanceTo(endPoint);
                Log.d("LATLON-Mts", " " + distance);*/
                //https://developer.android.com/reference/android/location/Location#distanceTo(android.location.Location)
                //https://stackoverflow.com/questions/6981916/how-to-calculate-distance-between-two-locations-using-their-longitude-and-latitu
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i("TAG", "Request Cancelled.......");
            }
        }
    }

    //Here getting distance in kilometers (km)
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void gotoSendPackage() {
        Intent intent = new Intent(DasboardActivity.this, SendPackageActivity.class);
        sessionManager.setKeyOrderType("1");//PACKAGE
        startActivity(intent);
    }

    private void gotoOtherStore() {
        Intent intent = new Intent(DasboardActivity.this, OtherStoreActivity.class);
        //sessionManager.setKeyOrderType("");
        startActivity(intent);
    }

    private void gotoGrocery() {
        Intent intent = new Intent(DasboardActivity.this, GroceryListActivity.class);
        sessionManager.setKeyOrderType("2");//GROCERY
        startActivity(intent);
    }

    private void goToRestaruent() {
        Intent intent = new Intent(DasboardActivity.this, RestrurentListActivity.class);
        sessionManager.setKeyOrderType("3");//RESTAURANT
        startActivity(intent);
    }

    private void goToOfficeBoyOrMaidService() {
        Intent intent = new Intent(DasboardActivity.this, SelectServiceActivity.class);
        //sessionManager.setKeyOrderType("");
        startActivity(intent);
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(PackageListViewModel.class);
        viewModel.setCallback(this);
        viewModel.setCallback2(this);
    }

    @Override
    public void onSuccess() {
        // Toast.makeText(this,"Data fetch",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {

    }

   /* @Override
    public void onSuccessGetCharges(String oneKmCharges, String lessThan5KmCharges, String greaterFiveToTenKmPrice, String greaterTenKmPrice) {
        chargesInDoubleInOneKm = Integer.parseInt(oneKmCharges);
        chargesInDoubleInFiveKm = Integer.parseInt(lessThan5KmCharges);
        chargesInDoubleInTenKm = Integer.parseInt(greaterFiveToTenKmPrice);
        chargesInDoubleInGrater10Km = Integer.parseInt(greaterTenKmPrice);
        //Log.d("TEST!!!", chargesInDoubleInGrater10Km + "");
        DeliveryEverything.getAppSharedPreference().saveCost1(String.valueOf(chargesInDoubleInOneKm));
        DeliveryEverything.getAppSharedPreference().saveCost2(String.valueOf(chargesInDoubleInFiveKm));
        DeliveryEverything.getAppSharedPreference().saveCost3(String.valueOf(chargesInDoubleInTenKm));
        DeliveryEverything.getAppSharedPreference().saveCost4(String.valueOf(chargesInDoubleInGrater10Km));
    }*/

    @Override
    public void onSuccessGetCharges(String fixed_cost, String per_kilometer) {
        DeliveryEverything.getAppSharedPreference().savePerKm(per_kilometer);
        DeliveryEverything.getAppSharedPreference().saveFixedCost(fixed_cost);
        //Log.d("TEST!!!", fixed_cost + " " + per_kilometer);
    }

    @Override
    public void onfailure() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    private void featchCurrentLocation() {
        Dexter.withActivity(DasboardActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        location();
//                            startActivity(new Intent(LocationOnActivity.this, DasboardActivity.class));
//                            finish();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DasboardActivity.this);
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(DasboardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }


    private void location() {
        String provider = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            //Toast.makeText(this, "Please enable GPS...", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(this, "Please wait while fetching address!", Toast.LENGTH_SHORT).show();
            getCurrentLocation("1");
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(final String type) {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                String lat = Double.toString(location.getLatitude());
                String lon = Double.toString(location.getLongitude());
                currentLatitude = lat;
                currentLongitude = lon;
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


    public void GetAddress(String lat, String lon) {
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
                tv_dashboard_address.setText(ret);
                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(ret);

                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(ret);
                DeliveryEverything.getAppSharedPreference().saveLatitude(String.valueOf(returnedAddress.getLatitude()));
                DeliveryEverything.getAppSharedPreference().saveLongitude(String.valueOf(returnedAddress.getLongitude()));
//                searchAddressLocation = true;
//                goTodashboard();
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
    }

    @SuppressLint("DefaultLocale")
    public static LatLng getLocation(double lon, double lat, int radius) {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(lat);

        double foundLongitude = new_x + lon;
        double foundLatitude = y + lat;
        System.out.println("Longitude: " + foundLongitude + "  Latitude: " + foundLatitude);

        return new LatLng(Double.parseDouble(String.format("%.6f", foundLatitude)),
                Double.parseDouble(String.format("%.6f", foundLongitude)));

    }
}


