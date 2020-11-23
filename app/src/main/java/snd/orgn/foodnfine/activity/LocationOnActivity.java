package snd.orgn.foodnfine.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;

import static snd.orgn.foodnfine.constant.AppConstants.MAP_API;

public class LocationOnActivity extends BaseActivity {
    @BindView(R.id.cv_current_location)
    CardView cv_current_location;
    @BindView(R.id.cv_someOther_location)
    CardView cv_someOther_location;
    @BindView(R.id.layout_backIcon)
    LinearLayout layout_backIcon;
    View rootView;
    String placeId1;
    LatLng queriedLocation;
    boolean searchAddressLocation = false;
    public static final int DELAY_LENGTH = 800;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_on);
        ButterKnife.bind(this);
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, LocationOnActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hideStatusBarcolor();
        if (!Places.isInitialized()) {
            Places.initialize(this, MAP_API);
        }
        initFields();
        setupOnClick();
    }

    @Override
    public void initFields() {
        if (ContextCompat.checkSelfPermission(LocationOnActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(LocationOnActivity.this, DasboardActivity.class));
            finish();
            return;
        }
    }

    @Override
    public void setupOnClick() {
        cv_current_location.setOnClickListener(v -> {
            featchCurrentLocation();
        });

        layout_backIcon.setOnClickListener(v -> {
            super.onBackPressed();
            finish();
        });

        cv_someOther_location.setOnClickListener(v -> {
            Dexter.withActivity(LocationOnActivity.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            findPlace();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LocationOnActivity.this);
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
                                Toast.makeText(LocationOnActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .check();
        });


        if(searchAddressLocation){
            goTodashboard();
        }
    }


    private void startProcess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (searchAddressLocation) {
                    //  goTodashboard();
                } else {

                    // goToSignIn();
                }
            }
        }, DELAY_LENGTH);
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }


    private void goTodashboard() {
        Intent intent = new Intent(this, DasboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
//        startActivity(new Intent(LocationOnActivity.this, DasboardActivity.class));
//                           finish();
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
                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(place.getAddress());
                searchAddressLocation = true;
                goTodashboard();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private void location() {
        String provider = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            Toast.makeText(this, "Please enable GPS...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please wait while fetching address!", Toast.LENGTH_SHORT).show();

            getCurrentLocation("1");
        }
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
                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(ret);
                searchAddressLocation = true;
goTodashboard();
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



    private void featchCurrentLocation(){
        Dexter.withActivity(LocationOnActivity.this)
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(LocationOnActivity.this);
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
                            Toast.makeText(LocationOnActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();



    }
}
