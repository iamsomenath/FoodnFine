package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.GroceryListAdapter;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackAllGroceryList;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.rest.response.AllGrocery;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.GroceryListViewModel;

public class GroceryListActivity extends BaseActivity implements CallbackAllGroceryList, CallbackGetChargesInKM {

    @BindView(R.id.rv_recyclerViewGrocery)
    RecyclerView recyclerView;
    @BindView(R.id.iv_restaurant_back)
    ImageView iv_restaurant_back;
    GroceryListAdapter groceryListAdapter;
    GroceryListViewModel viewModel;
    @BindView(R.id.layout_grocery_fetchingData)
    RelativeLayout layout_fetchingData;
    @BindView(R.id.layout_dataAvailable)
    LinearLayout layout_dataAvailable;
    @BindView(R.id.layout_restaurent_notFound)
    ConstraintLayout layout_dataNotAvailable;
    @BindView(R.id.tv_numberOfStore)
    TextView tv_numberOfStore;
    LoadingDialog loadingDialog;

    private List<AllGrocery> allListForNearMe;
    private List<AllGrocery> NearMe1Km = new ArrayList<>();
    private List<AllGrocery> NearMeUpto5 = new ArrayList<>();
    private List<AllGrocery> NearMeUpto10 = new ArrayList<>();
    private List<AllGrocery> NearMeMorethan10 = new ArrayList<>();
    private List<AllGrocery> allList;
    private boolean locationIsSet = false;

    public static int chargesInDoubleInOneKm = 0;
    public static int chargesInDoubleInFiveKm = 0;
    public static int chargesInDoubleInTenKm = 0;
    public static int chargesInDoubleInGrater10Km = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        ButterKnife.bind(this);
        setFetchingDataLayout();
        hideStatusBarcolor();
        initFields();
        setupOnClick();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initFields() {
        initViewModel();
        loadingDialog = new LoadingDialog(this);
        //loadingDialog.showDialog();
        viewModel.getRestrurentList();
        //viewModel.getKMCharges();
        initRecyclerRestaurantList();

        ((Switch) (findViewById(R.id.switch1))).setOnCheckedChangeListener((buttonView, isChecked) -> {
            //Toast.makeText(this, isChecked + "", Toast.LENGTH_SHORT).show();
            //loadingDialog.showDialog();
            new Handler().postDelayed(() -> {
                //loadingDialog.hideDialog();
                if (isChecked) {
                    if (allListForNearMe.size() != 0) {
                        setDataAvailableLayout();
                        tv_numberOfStore.setText("(" + allListForNearMe.size() + " Stores)");
                        groceryListAdapter.clearGroceryList();
                        groceryListAdapter.addGroceryList(allListForNearMe);
                        groceryListAdapter.notifyDataSetChanged();
                    } else {
                        setNoDataLayout();
                        tv_numberOfStore.setText("(" + allListForNearMe.size() + " Stores)");
                        if (locationIsSet) {
                            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                                    "Oh sorry! No Grocery store found!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Oh sorry! No Grocery store found! Your location was not set properly. " +
                                    "Please select your current location to find `Near Me` stores.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                            builder.create();
                        }
                    }
                } else {
                    setDataAvailableLayout();
                    tv_numberOfStore.setText("(" + allList.size() + " Stores)");
                    groceryListAdapter.clearGroceryList();
                    groceryListAdapter.addGroceryList(allList);
                    groceryListAdapter.notifyDataSetChanged();
                }
            },1000);
        });
    }

    @Override
    public void setupOnClick() {
        iv_restaurant_back.setOnClickListener(v -> {
            super.onBackPressed();
        });
        final ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(GroceryListActivity.this, filter);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_pan, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.header1:
                            getNewList(NearMe1Km);
                            break;
                        case R.id.header2:
                            getNewList(NearMeUpto5);
                            break;
                        case R.id.header3:
                            getNewList(NearMeUpto10);
                            break;
                        case R.id.header4:
                            getNewList(NearMeMorethan10);
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroceryListViewModel.class);
        viewModel.setCallback(this);
        viewModel.setCallback2(this);
    }

    private void initRecyclerRestaurantList() {

        groceryListAdapter = new GroceryListAdapter(this);
        //  myPostAdapter.setCallbackAddTocart(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryListAdapter);
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

   /* @Override
    public void onSuccessGetCharges(String oneKmCharges, String lessThan5KmCharges, String greaterFiveToTenKmPrice, String greaterTenKmPrice) {
        chargesInDoubleInOneKm = Integer.parseInt(oneKmCharges);
        chargesInDoubleInFiveKm = Integer.parseInt(lessThan5KmCharges);
        chargesInDoubleInTenKm = Integer.parseInt(greaterFiveToTenKmPrice);
        chargesInDoubleInGrater10Km = Integer.parseInt(greaterTenKmPrice);
        //Log.d("TEST!!!", chargesInDoubleInOneKm + "");
    }*/

    @SuppressLint("SetTextI18n")
    private void getNewList(List<AllGrocery> allList) {
        if (allList.size() != 0) {
            setDataAvailableLayout();
            tv_numberOfStore.setText("(" + allList.size() + " Stores)");
            groceryListAdapter.clearGroceryList();
            groceryListAdapter.addGroceryList(allList);
            groceryListAdapter.notifyDataSetChanged();
        } else {
            setNoDataLayout();
            tv_numberOfStore.setText("(" + allList.size() + " Stores)");
            if (locationIsSet) {
                Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Oh sorry! No Grocery store found!", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Oh sorry! No Grocery store found! Your location was not set properly. " +
                        "Please select your current location to find `Near Me` stores.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                builder.create();
            }
        }
    }

    @Override
    public void onSuccessGetCharges(String fixed_cost, String per_kilometer) {

    }

    @Override
    public void onfailure() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(List<AllGrocery> allGroceryList) {
        //loadingDialog.hideDialog();
        allList = new ArrayList<>();
        allList = allGroceryList;
        if (allGroceryList.size() != 0) {
            setDataAvailableLayout();
            tv_numberOfStore.setText("(" + allGroceryList.size() + " Stores)");
            groceryListAdapter.clearGroceryList();
            groceryListAdapter.addGroceryList(allGroceryList);
            groceryListAdapter.notifyDataSetChanged();

            allListForNearMe = new ArrayList<>();
            try {
                for (int i = 0; i < allGroceryList.size(); i++) {
                    Log.d("LAT-LON", allGroceryList.get(i).getLatitude() + " " + allGroceryList.get(i).getLongitude());
                    if (!allGroceryList.get(i).getLatitude().equals("0")) {
                        Location startPoint = new Location("locationA");
                        startPoint.setLatitude(Double.parseDouble(Objects.requireNonNull(allGroceryList.get(i).getLatitude())));
                        startPoint.setLongitude(Double.parseDouble(Objects.requireNonNull(allGroceryList.get(i).getLongitude())));

                        Location endPoint = new Location("locationA");
                        endPoint.setLatitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLatitude()));
                        endPoint.setLongitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLongitude()));

                        double distance = startPoint.distanceTo(endPoint);
                        double myDistance = Double.parseDouble(getString(R.string.distance));
                        if (distance <= myDistance)
                            allListForNearMe.add(allGroceryList.get(i));
                        if (distance < 1.0)
                            NearMe1Km.add(allGroceryList.get(i));
                        if (distance >= 1.0 && distance < 5.0)
                            NearMeUpto5.add(allGroceryList.get(i));
                        if (distance >= 5.0 && distance < 10.0)
                            NearMeUpto10.add(allGroceryList.get(i));
                        if (distance > 10.0)
                            NearMeMorethan10.add(allGroceryList.get(i));

                        //Log.d("DISTANCE", "" + distance / 1000);
                        //Log.d("DISTANCE", endPoint.getLatitude() + " " + endPoint.getLongitude());
                        //Log.d("DISTANCE", startPoint.getLatitude() + " " + startPoint.getLongitude());

                    } else {
                        allListForNearMe.add(allGroceryList.get(i));
                    }
                }
                locationIsSet = true;
            } catch (Exception e) {
                locationIsSet = false;
            }
        } else {
            setNoDataLayout();
            tv_numberOfStore.setText("(" + allGroceryList.size() + " Stores)");
        }
    }

    @Override
    public void onError(String message) {
        //loadingDialog.hideDialog();
        setNoDataLayout();
        tv_numberOfStore.setText("");
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError(String message) {
        //loadingDialog.hideDialog();
        setNoDataLayout();
        tv_numberOfStore.setText("");
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setNoDataLayout() {
        recyclerView.setVisibility(View.GONE);
        //layout_dataNotAvailable.setVisibility(View.VISIBLE);
        layout_fetchingData.setVisibility(View.GONE);
    }

    public void setFetchingDataLayout() {
        recyclerView.setVisibility(View.GONE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.VISIBLE);
    }

    public void setDataAvailableLayout() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.GONE);
    }
}
