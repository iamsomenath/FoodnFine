package snd.orgn.foodnfine.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.card.MaterialCardView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;

public class SelectLocationMapActivity extends BaseActivity  implements OnMapReadyCallback, LocationSource.OnLocationChangedListener{


    @BindView(R.id.searchBar_selectLocationMapActivity)
    MaterialSearchBar materialSearchBar;
    @BindView(R.id.cardBtn_selectLocationMapActivity_selectLocation)
    MaterialCardView cardBtn_selectLocation;

    @BindView(R.id.iv_selectLocation_back)
    ImageView iv_selectLocation_back;
    private GoogleMap mMap;
    private Geocoder mGeocoder;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private String lastLocationName;
    private LocationCallback locationCallback;
    private View mapView;
    private MarkerOptions markerOptions;
    LocationManager locationManager;
    String provider;
    private RippleBackground rippleBg;
    private final float DEFAULT_ZOOM = 18;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    private TypeFilter[] typeFilters = {TypeFilter.ADDRESS, TypeFilter.CITIES, TypeFilter.ESTABLISHMENT, TypeFilter.GEOCODE, TypeFilter.REGIONS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location_map);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        rippleBg = findViewById(R.id.ripple_background);
        initFields();
        setupOnClick();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            startSetupProcess();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }

    private void startSetupProcess() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView_selectLocationMapActivity);
        mapFragment.getMapAsync(SelectLocationMapActivity.this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SelectLocationMapActivity.this);

        Places.initialize(SelectLocationMapActivity.this, "AIzaSyBKPkTvihVE9YK9Jhx-T4vluWmWAJIKpsI");
        // Create a new Places client instance.
        placesClient = Places.createClient(SelectLocationMapActivity.this);
       final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {

                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }
            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
                    RectangularBounds bounds = RectangularBounds.newInstance(
                            new LatLng(22.203299, 88.249595),
                            new LatLng(22.990002, 87.026131));
                    FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()

                            // Call either setLocationBias() OR setLocationRestriction().
                            .setLocationBias(bounds)
                            //.setLocationRestriction(bounds)
                            .setCountry("in")
                            .setTypeFilter(TypeFilter.ADDRESS)
                            .setSessionToken(token)
                            .setQuery(s.toString())
                            .build();
                    FindAutocompletePredictionsRequest  predictionsRequest = FindAutocompletePredictionsRequest.builder()
                            .setCountry("IN")

                            .setTypeFilter(TypeFilter.ADDRESS)
                            .setTypeFilter(TypeFilter.CITIES)
                            .setTypeFilter(TypeFilter.ESTABLISHMENT)
                            .setTypeFilter(TypeFilter.GEOCODE)
                            .setTypeFilter(TypeFilter.REGIONS)
                            .setSessionToken(token)
                            .setQuery(s.toString())
                            .build();

                    placesClient.findAutocompletePredictions(request).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                            if(task.isSuccessful()){
                                FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                if(predictionsResponse != null){
                                    predictionList = predictionsResponse.getAutocompletePredictions();
                                    List<String> suggestionsList = new ArrayList<>();
                                    for(int i=0;i< predictionList.size();i++){
                                        AutocompletePrediction prediction = predictionList.get(i);
                                        suggestionsList.add(prediction.getFullText(null).toString());
                                    }
                                    materialSearchBar.updateLastSuggestions(suggestionsList);
                                    if(!materialSearchBar.isSuggestionsVisible()){
                                        materialSearchBar.showSuggestionsList();
                                    }
                                }
                            } else {
                                Log.i("SelectLocationActivity", "prediction fetching task unsuccessful");
                            }
                        }
                    });
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if(position >= predictionList.size()){
                    return;
                }
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(imm != null)
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                final String placeId = selectedPrediction.getPlaceId();
                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();
                        if(latLngOfPlace != null){
                            Location sLocation = new Location("");
                            sLocation.setLatitude(latLngOfPlace.latitude);
                            sLocation.setLongitude(latLngOfPlace.longitude);

                            lastLocationName=suggestion;
                            mLastKnownLocation = sLocation;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof ApiException){
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });
    }


    @Override
    public void initFields() {
        markerOptions = new MarkerOptions();
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    }

    @Override
    public void setupOnClick() {
        cardBtn_selectLocation.setOnClickListener(v -> {
//            Intent intent = new Intent();
//            intent.putExtra("selectedLatitude", String.valueOf(mLastKnownLocation.getLatitude()));
//            intent.putExtra("selectedLongitude", String.valueOf(mLastKnownLocation.getLongitude()));
//            intent.putExtra("locationName", lastLocationName);
//            try {
//                intent.putExtra("addressDetail", getAddressOfLocation());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            setResult(RESULT_OK, intent);
//            finish();


            LatLng currentMarkerLocation = mMap.getCameraPosition().target;
            String latitide = String.valueOf(currentMarkerLocation.latitude);
            String longitude = String.valueOf(currentMarkerLocation.longitude);
            rippleBg.startRippleAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rippleBg.stopRippleAnimation();

                    Intent intent = new Intent(SelectLocationMapActivity.this, DasboardActivity.class);
                    intent.putExtra("selectedLatitude", latitide);
                    intent.putExtra("selectedLongitude", longitude);


                    //  intent.putExtra("otp", String.valueOf(otpSend));
                    startActivity(intent);

                  //  startActivity(new Intent(SelectLocationMapActivity.this, DasboardActivity.class));
                    finish();
                }
            }, 3000);
        });


        iv_selectLocation_back.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }


//    private SaveAddressDetails getAddressOfLocation() throws IOException {
//        Double latitude = mLastKnownLocation.getLatitude();
//        Double longitude = mLastKnownLocation.getLongitude();
//        SaveAddressDetails addressDetail = new SaveAddressDetails();
//        List<Address> addresses = mGeocoder.getFromLocation(latitude, longitude, 1);
//
//        if (addresses.get(0).getAddressLine(0) != null) {
//            String addressLine = addresses.get(0).getAddressLine(0);
//            String[] separated = addressLine.split(",");
//            String addressLine0 = separated[0];
//            String addressLine1 = separated[1];
//            String addressLine2 = separated[2];
//            String addressLine3 = separated[3];
//            String addressLine4 = separated[separated.length - 1];
//
//
//            addressDetail.setAddressLine1(addressLine0 + "," + addressLine1);
//            addressDetail.setAddressLine2(addressLine2);
//            addressDetail.setCountry(addressLine4);
//            addressDetail.setLandmark(addressLine2);
//        }
//
//        if (addresses.get(0).getAddressLine(1) != null) {
//            String addressLine2 = addresses.get(0).getAddressLine(1);
//            addressDetail.setAddressLine2(addressLine2);
//        }
//
//        if (addresses.get(0).getPostalCode() != null) {
//            String ZIP = addresses.get(0).getPostalCode();
//            addressDetail.setPincode(ZIP);
//            Log.d("ZIP CODE", ZIP);
//        }
//
//        if (addresses.get(0).getLocale() != null) {
//            String landmark = addresses.get(0).getLocality();
//            addressDetail.setLandmark(landmark);
//            Log.d("Landmark", landmark);
//        }
//
////        if (addresses.get(0).getLocality() != null) {
////            String area = addresses.get(0).getSubLocality();
////            addressDetail.setArea(area);
////            Log.d("Area",area);
////        }
//
//        if (addresses.get(0).getLocality() != null) {
//            String city = addresses.get(0).getLocality();
//            addressDetail.setCity(city);
//            Log.d("CITY", city);
//        }
//
//        if (addresses.get(0).getAdminArea() != null) {
//            String state = addresses.get(0).getAdminArea();
//            addressDetail.setState(state);
//            Log.d("STATE", state);
//        }
//
//        if (addresses.get(0).getAdminArea() != null) {
//            String area = addresses.get(0).getSubLocality();
//            addressDetail.setArea(area);
//        }
//
////        if (addresses.get(0).getCountryName() != null) {
////            String countryName = addresses.get(0).getCountryName();
////            addressDetail.setArea(countryName);
////        }
//
//        return addressDetail;
//    }

//    @SuppressLint("MissingPermission")
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        if(mapView != null && mapView.findViewById(Integer.parseInt("1")) != null){
//            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//            layoutParams.setMargins(0, 0, 40, 180);
//        }
//
//        //check if gps is enabled or not and then request user to enable it
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(5000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//
//        SettingsClient settingsClient = LocationServices.getSettingsClient(SelectLocationMapActivity.this);
//        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
//
//        task.addOnSuccessListener(SelectLocationMapActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                getDeviceLocation();
//            }
//        });
//
//        task.addOnFailureListener(SelectLocationMapActivity.this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if(e instanceof ResolvableApiException){
//                    ResolvableApiException resolvable = (ResolvableApiException) e;
//                    try {
//                        resolvable.startResolutionForResult(SelectLocationMapActivity.this, 51);
//                    } catch (IntentSender.SendIntentException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                if(materialSearchBar.isSuggestionsVisible())
//                    materialSearchBar.clearSuggestions();
//                if(materialSearchBar.isSearchEnabled())
//                    materialSearchBar.disableSearch();
//                return false;
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 51) {
//            if(resultCode == RESULT_OK) {
//                getDeviceLocation();
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private void getDeviceLocation(){
//        mFusedLocationProviderClient.getLastLocation()
//                .addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if(task.isSuccessful()){
//                            mLastKnownLocation = task.getResult();
//                            if(mLastKnownLocation != null){
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            } else {
//                                final LocationRequest locationRequest = LocationRequest.create();
//                                locationRequest.setInterval(10000);
//                                locationRequest.setFastestInterval(5000);
//                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                                locationCallback = new LocationCallback(){
//                                    @Override
//                                    public void onLocationResult(LocationResult locationResult) {
//                                        super.onLocationResult(locationResult);
//                                        if(locationResult == null) {
//                                            return;
//                                        }
//                                        mLastKnownLocation = locationResult.getLastLocation();
//                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
//                                    }
//                                };
//                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//
//                            }
//                        } else {
//                            Toast.makeText(SelectLocationMapActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder gc = new Geocoder(SelectLocationMapActivity.this);
                List<Address> list = null;

                try {

                    LatLng latLng = marker.getPosition();
                    list = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address address = list.get(0);
                    marker.setTitle(address.getLocality());
                    marker.showInfoWindow();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       // if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
//        if (mapView != null && mapView.findViewById(R.id.mapView_selectLocationMapActivity) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//            View locationButton = ((View) mapView.findViewById(R.id.mapView_selectLocationMapActivity).getParent()).findViewById(R.id.iv_selectLocationActivity_locationPin);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(SelectLocationMapActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(SelectLocationMapActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(SelectLocationMapActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(SelectLocationMapActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (materialSearchBar.isSuggestionsVisible()) {
                    materialSearchBar.clearSuggestions();
                }

                if (materialSearchBar.isSearchEnabled()) {
                    materialSearchBar.disableSearch();
                }

                return false;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();

                                        markerOptions.draggable(true);
                                        markerOptions.position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
                                        mMap.addMarker(markerOptions);

                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };

                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        } else {
                            Toast.makeText(SelectLocationMapActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onLocationChanged(Location location) {
        markerOptions.draggable(true);
        markerOptions.position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
        if (!markerOptions.isVisible()) {
            mMap.addMarker(markerOptions);
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //locationManager.removeUpdates(this.);
        }
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

}
