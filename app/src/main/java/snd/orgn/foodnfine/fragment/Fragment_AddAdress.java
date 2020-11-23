package snd.orgn.foodnfine.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseFragment;
import snd.orgn.foodnfine.callbacks.CallbackAddAddress;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 */
public class Fragment_AddAdress extends BaseFragment implements OnMapReadyCallback {



    View rootView;
    GoogleMap googleMap;
    private View mapView;
    private Double selectedLocationLatitude = 0.0d;
    private Double selectedLocationLongitude = 0.0d;
    private SupportMapFragment mMapView;
    private static int REQUEST_CODE_GET_LOCATION = 121;
    private CallbackAddAddress callback;

    public Fragment_AddAdress() {
        // Required empty public constructor
    }

  public Fragment_AddAdress(CallbackAddAddress callback){
        this.callback = callback;
  }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_add_adress, container, false);
        ButterKnife.bind(this, rootView);
        MapsInitializer.initialize(getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();


        initFields();

      //  setupViewModel();
        return rootView;

    }

   private void initFields(){

   }

   private void setupOnClick(){

   }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
