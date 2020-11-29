package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import snd.orgn.foodnfine.adapter.activityAdapter.RestrurentListAdapter;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackAllRestaurantList;
import snd.orgn.foodnfine.rest.response.AllRestaurant;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.RestrurentListViewModel;

public class RestrurentListActivity extends BaseActivity implements CallbackAllRestaurantList {

    @BindView(R.id.rv_recyclerViewRestaurant)
    RecyclerView recyclerView;
    @BindView(R.id.iv_restaurant_back)
    ImageView iv_restaurant_back;
    RestrurentListAdapter restrurentListAdapter;
    RestrurentListViewModel viewModel;
    @BindView(R.id.layout_restaurent_fetchingData)
    RelativeLayout layout_fetchingData;
    @BindView(R.id.layout_dataAvailable)
    LinearLayout layout_dataAvailable;
    @BindView(R.id.layout_restaurent_notFound)
    ConstraintLayout layout_dataNotAvailable;
    LoadingDialog loadingDialog;
    @BindView(R.id.tv_numberOfStore)
    TextView tv_numberOfStore;

    private List<AllRestaurant> allListForNearMe;
    private List<AllRestaurant> allLists;
    private List<AllRestaurant> NearMe1Km = new ArrayList<>();
    private List<AllRestaurant> NearMeUpto5 = new ArrayList<>();
    private List<AllRestaurant> NearMeUpto10 = new ArrayList<>();
    private List<AllRestaurant> NearMeMorethan10 = new ArrayList<>();
    private boolean locationIsSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrurent_list);
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
        loadingDialog.showDialog();
        viewModel.getRestrurentList();
        initRecyclerRestaurantList();

        ((Switch) (findViewById(R.id.switch1))).setOnCheckedChangeListener((buttonView, isChecked) -> {
            //Toast.makeText(this, isChecked + "", Toast.LENGTH_SHORT).show();
            loadingDialog.showDialog();
            new Handler().postDelayed(() -> {
                loadingDialog.hideDialog();
                if (isChecked) {
                    if (allListForNearMe.size() != 0) {
                        setDataAvailableLayout();
                        tv_numberOfStore.setText("(" + allListForNearMe.size() + " Stores)");
                        restrurentListAdapter.clearRestrurentList();
                        restrurentListAdapter.addRestrurentList(allListForNearMe);
                        restrurentListAdapter.notifyDataSetChanged();
                    } else {
                        tv_numberOfStore.setText("(" + allListForNearMe.size() + " Stores)");
                        setNoDataLayout();
                        if (locationIsSet) {
                            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                                    "Oh sorry! No Restaurant found!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Oh sorry! No Restaurant store found! Your location was not set properly. " +
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
                    tv_numberOfStore.setText("(" + allLists.size() + " Stores)");
                    setDataAvailableLayout();
                    restrurentListAdapter.clearRestrurentList();
                    restrurentListAdapter.addRestrurentList(allLists);
                    restrurentListAdapter.notifyDataSetChanged();
                }
            }, 1000);
        });
    }

    @Override
    public void setupOnClick() {
        iv_restaurant_back.setOnClickListener(v -> {
            super.onBackPressed();
        });
        final ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(RestrurentListActivity.this, filter);
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
        viewModel = ViewModelProviders.of(this).get(RestrurentListViewModel.class);
        viewModel.setCallback(this);
    }

    private void initRecyclerRestaurantList() {

        restrurentListAdapter = new RestrurentListAdapter(this);
        //  myPostAdapter.setCallbackAddTocart(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(restrurentListAdapter);
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @SuppressLint("SetTextI18n")
    private void getNewList(List<AllRestaurant> allList) {
        if (allList.size() != 0) {
            setDataAvailableLayout();
            tv_numberOfStore.setText("(" + allList.size() + " Stores)");
            restrurentListAdapter.clearRestrurentList();
            restrurentListAdapter.addRestrurentList(allList);
            restrurentListAdapter.notifyDataSetChanged();
        } else {
            tv_numberOfStore.setText("(" + allList.size() + " Stores)");
            setNoDataLayout();
            if (locationIsSet) {
                Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Oh sorry! No Restaurant found!", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Oh sorry! No Restaurant store found! Your location was not set properly. " +
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
    public void onSuccess(List<AllRestaurant> allRestaurant) {

        allLists = new ArrayList<>();
        allLists = allRestaurant;
        loadingDialog.hideDialog();

        if (allRestaurant.size() != 0) {
            tv_numberOfStore.setText("(" + allRestaurant.size() + " Stores)");
            setDataAvailableLayout();
            restrurentListAdapter.clearRestrurentList();
            restrurentListAdapter.addRestrurentList(allRestaurant);
            restrurentListAdapter.notifyDataSetChanged();

            allListForNearMe = new ArrayList<>();
            try {
                for (int i = 0; i < allRestaurant.size(); i++) {
                    if (!allRestaurant.get(i).getLatitude().equals("0")) {
                        Location startPoint = new Location("locationA");
                        startPoint.setLatitude(Double.parseDouble(Objects.requireNonNull(allRestaurant.get(i).getLatitude())));
                        startPoint.setLongitude(Double.parseDouble(Objects.requireNonNull(allRestaurant.get(i).getLongitude())));

                        Location endPoint = new Location("locationA");
                        endPoint.setLatitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLatitude()));
                        endPoint.setLongitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLongitude()));

                        double distance = startPoint.distanceTo(endPoint);
                        double myDistance = Double.parseDouble(getString(R.string.distance));
                        if (distance <= myDistance)
                            allListForNearMe.add(allRestaurant.get(i));
                        if (distance < 1.0)
                            NearMe1Km.add(allRestaurant.get(i));
                        if (distance >= 1.0 && distance < 5.0)
                            NearMeUpto5.add(allRestaurant.get(i));
                        if (distance >= 5.0 && distance < 10.0)
                            NearMeUpto10.add(allRestaurant.get(i));
                        if (distance > 10.0)
                            NearMeMorethan10.add(allRestaurant.get(i));
                        //Log.d("DISTANCE", "" + distance / 1000);
                        //Log.d("DISTANCE", endPoint.getLatitude() + " " + endPoint.getLongitude());
                        //Log.d("DISTANCE", startPoint.getLatitude() + " " + startPoint.getLongitude());

                    } else {
                        allListForNearMe.add(allRestaurant.get(i));
                    }
                }
                locationIsSet = true;
            } catch (Exception e) {
                locationIsSet = false;
            }
        }else {
            tv_numberOfStore.setText("(" + allRestaurant.size() + " Stores)");
            setNoDataLayout();
        }
    }

    @Override
    public void onError(String message) {
        loadingDialog.hideDialog();
        setNoDataLayout();
        tv_numberOfStore.setText("");
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialog.hideDialog();
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
        layout_dataNotAvailable.setVisibility(View.VISIBLE);
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
