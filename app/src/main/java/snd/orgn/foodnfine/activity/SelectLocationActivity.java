package snd.orgn.foodnfine.activity;



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;

import static snd.orgn.foodnfine.constant.AppConstants.MAP_API;

public class SelectLocationActivity extends BaseActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    AutocompleteSupportFragment place1;
    TextView txtInfo;
    String placeId1;
    LatLng queriedLocation, queriedLocation2;
    ArrayList<String> location_list;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        if (!Places.isInitialized()) {
            Places.initialize(this, MAP_API);
        }
        PlacesClient placesClient = Places.createClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        place1 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place1);

        place1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        place1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeId1 = place.getId();
                //Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                //Log.d("Tag", "Place: " + place.getAddress());
                queriedLocation = place.getLatLng();

                //Log.i(snd.orgn.deliver_everything.activity.TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
               // Log.i(TAG, "An error occurred: " + status);
            }
        });

    }


    @Override
    public void initFields() {

    }

    @Override
    public void setupOnClick() {

    }

    public void AddPlace(Place place, int pno) {
        try {
            if (mMap == null) {
                Toast.makeText(SelectLocationActivity.this, "Please check your API key for Google Places SDK and your internet conneciton", Toast.LENGTH_LONG).show();
            } else {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 4));

                mMap.addMarker(new MarkerOptions().position(place.getLatLng())
                        .title("Name:" + place.getName() + ". Address:" + place.getAddress()));

                txtInfo.setText("Name:" + place.getName() + ". Address:" + place.getAddress());

            }
        } catch (Exception ex) {

            if (ex != null) {
                Toast.makeText(SelectLocationActivity.this, "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(mMap!=null)
        {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    txtInfo.setText(marker.getTitle().toString() + " Lat:" + marker.getPosition().latitude + " Long:" + marker.getPosition().longitude);
                    return false;
                }
            });
        }
    }

    private class TAG {
    }
}

