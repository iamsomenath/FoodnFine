package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.bottomSheetFragment.BottomSheetSelectPackageFragment;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.callbacks.CallbackSeletedItem;
import snd.orgn.foodnfine.constant.AppConstants;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.SendPackageViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_TOTAL_PRICE;
import static snd.orgn.foodnfine.constant.AppConstants.MAP_API;

public class SendPackageActivity extends BaseActivity implements CallbackSeletedItem, CallbackGetChargesInKM {

    @BindView(R.id.iv_sendPackage_back)
    ImageView iv_sendPackage_back;
    @BindView(R.id.iv_right_tick)
    ImageView iv_right_tick;
    @BindView(R.id.iv_right_tick_delivery)
    ImageView iv_right_tick_delivery;
    @BindView(R.id.iv_right_tick_package_contents)
    ImageView iv_right_tick_package_contents;
    @BindView(R.id.tv_pickup_address)
    EditText tv_pickup_address;
    @BindView(R.id.tv_delivery_address)
    EditText tv_delivery_address;
    @BindView(R.id.tv_package_content)
    EditText tv_package_content;
    @BindView(R.id.et_estimated_value_of_contents)
    EditText et_estimated_value_of_contents;
    @BindView(R.id.et_any_instruction)
    EditText et_any_instruction;
    @BindView(R.id.cv_payNowBtn)
    CardView cv_payNowBtn;
    @BindView(R.id.tv_term_and_condition)
    TextView tv_term_and_condition;
    @BindView(R.id.tvBtn_pay_now)
    TextView tvBtn_pay_now;
    @BindView(R.id.tv_grandTotalItem)
    TextView tv_grandTotalItem;
    @BindView(R.id.tv_charges_amount)
    TextView tv_charges_amount;
    @BindView(R.id.tv_discount_price)
    TextView tv_discount_price;
    @BindView(R.id.tv_actual_price)
    TextView tv_actual_price;
    @BindView(R.id.layout_invoice)
    LinearLayout layout_invoice;
    @BindView(R.id.layout_price)
    LinearLayout layout_price;
    @BindView(R.id.rg_payment_home)
    RadioGroup rg_payment_option;
    boolean fetch_result = true;
    @BindView(R.id.layout_pickup_address)
    LinearLayout layout_pickup_address;
    @BindView(R.id.layout_delivery_address)
    LinearLayout layout_delivery_address;
    @BindView(R.id.layout_package_contents)
    LinearLayout layout_package_contents;
    @BindView(R.id.getDeliveryAddress)
    ImageView getDeliveryAddress;
    @BindView(R.id.getPickUpAddress)
    ImageView getPickUpAddress;
    @BindView(R.id.details_delivery_address)
    EditText details_delivery_address;
    @BindView(R.id.details_pickup_address)
    EditText details_pickup_address;
    @BindView(R.id.delivery_layout)
    LinearLayout delivery_layout;

    private List<String> seletedPackage;
    private BottomSheetSelectPackageFragment bottomSheetSelectPackageFragment;
    LatLng queriedLocation, queriedLocation2;
    String placeId1, placeId2;
    String distance = "", time = "";
    String chargesIn1Km;
    String chargesInLess5Km;
    String chargesInGreater5km;
    String totalcharges;
    /*Double chargesInDoubleInOneKm = 0.0;
    Double chargesInDoubleInFiveKm = 0.0;
    Double chargesInDoubleInTenKm = 0.0;*/
    int chargesInDoubleInOneKm = 0;
    int chargesInDoubleInFiveKm = 0;
    int chargesInDoubleInTenKm = 0;
    int chargesInDoubleInGrater10Km = 0;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
    SendPackageViewModel viewModel;
    View rootView;
    String temp;
    LoadingDialog loadingDialog;
    // Define a Place ID.
    String placeId = "INSERT_PLACE_ID_HERE";
    String payment = "null";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);
        ButterKnife.bind(this);
        hideStatusBarcolor();

        sessionManager = new SessionManager(this);

        Places.initialize(this, MAP_API);
        inItViewModel();
        viewModel.getChargeseInKm();
        initFields();
        setupOnClick();
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, SendPackageActivity.this);
    }

    @Override
    public void initFields() {
        rootView = this.findViewById(android.R.id.content).getRootView();
        tv_pickup_address.setText(FoodnFine.getAppSharedPreference().getCurrentLocation());
        if (tv_delivery_address.getText().equals("") || tv_delivery_address.getText().length() == 0) {
            iv_right_tick_delivery.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_untick));
        } else {
            iv_right_tick_delivery.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_tick));

        }
        if (tv_package_content.getText().equals("") || tv_package_content.getText().length() == 0) {
            iv_right_tick_package_contents.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_untick));
        } else {
            iv_right_tick_package_contents.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_tick));

        }

        loadingDialog = new LoadingDialog(this);
        initBottomSheets();
    }

    @Override
    public void setupOnClick() {
        iv_sendPackage_back.setOnClickListener(v -> {
            super.onBackPressed();
        });
        layout_pickup_address.setOnClickListener(v -> {
            pickupPlace();
        });
        layout_delivery_address.setOnClickListener(v -> {
            deliveryPlace2();
        });
        layout_package_contents.setOnClickListener(v -> {
            showBottomSheet();
        });
        getPickUpAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetFromSavedAddressActivity.class);
            intent.putExtra("VAL", "3");
            startActivityForResult(intent, 3);
        });
        getDeliveryAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetFromSavedAddressActivity.class);
            intent.putExtra("VAL", "4");
            startActivityForResult(intent, 4);
        });
        cv_payNowBtn.setOnClickListener(v -> {

            int selectedId = rg_payment_option.getCheckedRadioButtonId();
            switch (selectedId) {
                case R.id.rb_payment_option_op:
                    payment = "op";
                    break;
                case R.id.rb_payment_option_cod:
                    payment = "cod";
                    break;
                default:
                    payment = "";
                    break;
            }

            if (payment.isEmpty()) {
                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please select payment option......", Snackbar.LENGTH_LONG).show();
                return;
            }
            if(checkValidation()){
                allDataSaveInLocal();
            show_success_popup(temp);
            }
        });
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    public void pickupPlace() {

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, 1);
    }

    public void deliveryPlace2() {
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, 2);
    }

    @SuppressLint({"SetTextI16n", "SetTextI18n"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                //Log.d("TAG!!!", "Place: " + place.getName() + ", " + place.getId());
                placeId1 = place.getId();
                queriedLocation = place.getLatLng();

                tv_pickup_address.setText(place.getAddress());
                Proceed();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {

            }

        } else if (requestCode == 2) {

            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                placeId2 = place.getId();

                queriedLocation2 = place.getLatLng();
                Proceed();
                tv_delivery_address.setText(place.getAddress());
                delivery_layout.setVisibility(View.VISIBLE);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {

            }
        } else if (requestCode == 3) { // to fetch delivery saved address
            try {
                if (data.hasExtra("MESSAGE")) {
                    //Log.d("TESTS", data.getSerializableExtra("MESSAGE").toString());
                    AddressDetails addressDetails = (AddressDetails) data.getSerializableExtra("MESSAGE");
                    details_pickup_address.setText(addressDetails.getBuilding() + ", " + addressDetails.getHouse() + ", " +
                            addressDetails.getLandmark() + ", " + addressDetails.getLocation());
                }
            } catch (Exception ignored) {
            }
        } else if (requestCode == 4) { // to fetch pickup saved address
            try {
                if (data.hasExtra("MESSAGE")) {
                    //Log.d("TESTS", data.getSerializableExtra("MESSAGE").toString());
                    AddressDetails addressDetails = (AddressDetails) data.getSerializableExtra("MESSAGE");
                    details_delivery_address.setText(addressDetails.getBuilding() + ", " + addressDetails.getHouse() + ", " +
                            addressDetails.getLandmark() + ", " + addressDetails.getLocation());
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void Proceed() {

        if (tv_pickup_address.getText().toString()
                .equalsIgnoreCase("Select Pickup Location")) {

            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                    "⚠ Pick Up location not selected properly", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (tv_delivery_address.getText().toString()
                .equalsIgnoreCase("Select Drop Location")) {
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                    "⚠ Drop location not selected properly", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (tv_delivery_address.getText().toString().equalsIgnoreCase(tv_pickup_address.getText().toString())) {
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                    "⚠ Pick Up & Drop location can't be unique", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            showRightick();// iv_right_tick_delivery.setBackgroundResource(R.drawable.ic_round_tick);
        }
    }

    private void showRightick() {
        iv_right_tick_delivery.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_tick));
    }

    private void showRightickpackagelist() {
        iv_right_tick_package_contents.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_tick));
        layout_price.setVisibility(View.VISIBLE);
        layout_invoice.setVisibility(View.VISIBLE);
        getDistance();
    }

    private String getMapsApiUrl(String loc1, String loc2) {

        // using place-name
        // https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Kolkata&destinations=Mumbai&key=AIzaSyBBrERrtfQHfUZFpzRd9AS0Ah9cmF_3hVY
        // using place-id
        // https://maps.googleapis.com/maps/api/distancematrix/json?origins=place_id:ChIJw3uqCpR1AjoRkCeIaaIAw_A&destinations=place_id:ChIJdU2cIgp2AjoRq1h9XRhp-90&mode=bus&key=AIzaSyAnB_F34yRkvVC2tkva9qvZDpcWZ5FaihU
        return String.format("api/distancematrix/json?units=imperial&origins=%s&destinations=%s&key=%s", loc1, loc2,
                AppConstants.MAP_API);
    }

    private void initBottomSheets() {
        bottomSheetSelectPackageFragment = new BottomSheetSelectPackageFragment();
        bottomSheetSelectPackageFragment.setCallback(this);
    }

    private void showBottomSheet() {
        bottomSheetSelectPackageFragment.show(getSupportFragmentManager(), bottomSheetSelectPackageFragment.getTag());
    }

    @Override
    public void onItemsSelected(List<String> selectedItems) {
        this.seletedPackage = selectedItems;
        packagedataSet(selectedItems);
        showRightickpackagelist();
    }

    private void packagedataSet(List<String> item) {
        tv_package_content.setText("");
        for (int j = 0; j < item.size(); j++) {
            tv_package_content.append(item.get(j) + "," + "");
        }
    }

    private void getDistance() {

        loadingDialog.showDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface services = retrofit.create(ApiInterface.class);

        final Call<ResponseBody> get_loc = services.get_Distance_Duration(getMapsApiUrl(
                tv_pickup_address.getText().toString().trim(), tv_delivery_address.getText().toString().trim()));
        get_loc.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<ResponseBody> callRequest, Response<ResponseBody> response) {
                if (response.body() != null) {

                    try {
                        String json = response.body().string();
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getString("status").equalsIgnoreCase("OK")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("rows");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("elements");
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                            String status = jsonObject2.getString("status");

                            if (status.equalsIgnoreCase("NOT_FOUND")) {

                                Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                        "⚠ Please select proper \uD83D\uDCCCPickup & \uD83D\uDCCDDrop location.", Snackbar.LENGTH_LONG);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(Color.WHITE);
                                snackbar.show();
                            } else if (status.equalsIgnoreCase("OK")) {
                                JSONObject jsonObject3 = jsonObject2.getJSONObject("distance");
                                JSONObject jsonObject4 = jsonObject2.getJSONObject("duration");
                                distance = jsonObject3.getString("text");
                                String str1 = distance.split(" ")[0];
                                if (str1.contains(","))
                                    str1 = str1.replace(",", "");

                                double total_distance;
                                /*if ((Double.parseDouble(str1) == Math.floor(Double.parseDouble(str1)))
                                        && !Double.isInfinite(Double.parseDouble(str1))) {
                                    int val = Integer.parseInt(str1);
                                    total_distance = val * 1.61;
                                } else {
                                    double val2 = Double.parseDouble(str1);
                                    total_distance = val2 * 1.61;
                                }*/
                                try {
                                    double val2 = Double.parseDouble(str1);
                                    total_distance = val2 * 1.61;
                                } catch (Exception e) {
                                    int val = Integer.parseInt(str1);
                                    total_distance = val * 1.61;
                                }
                                str1 = String.format("%.2f", total_distance) + " " + "Km";
                                distance = str1;
                                Double distanceInDouble = total_distance;
                                time = jsonObject4.getString("text");
                                 temp = "Distance: " + distance + "\nTime : " + time;
//                                if (select_full_track)
//                                    temp += "\nService Type : Full Track";
//                                if (select_part_load)
//                                    temp += "\nService Type : Part Load";
                                double calculatePrice = 0.0;
                                /*if (total_distance <= 1.0) {
                                    calculatePrice = (double) chargesInDoubleInOneKm;
                                } else if (total_distance > 1.0 && total_distance <= 5.0) {
                                    calculatePrice = total_distance * chargesInDoubleInFiveKm;
                                } else if (total_distance > 5.0) {
                                    calculatePrice = total_distance * chargesInDoubleInTenKm;
                                }*/
                                calculatePrice = total_distance * Double.parseDouble(FoodnFine.getAppSharedPreference().getPerKm());

                                totalcharges = String.format("%.2f", Math.ceil(calculatePrice));
                                priceDataPopulate(String.format("%.2f", Math.ceil(calculatePrice)));
                                loadingDialog.hideDialog();
                                fetch_result = false;
                                //show_success_popup(temp);
                                Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                        "Total Distance : " + temp, 3000);
                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                        "⚠ Unable to get Distance. Please Try again", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                    "⚠ Unable to get Distance. Please Try again", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                "⚠ Please check your internet connectivity!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } catch (JSONException e) {
                        //e.printStackTrace();
                        Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),
                                "⚠ Please check your internet connectivity!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    loadingDialog.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> callRequest, Throwable t) {
                loadingDialog.hideDialog();
                Snackbar snackbar = Snackbar.make(getParent().findViewById(android.R.id.content),
                        "⚠ Please check your internet connectivity!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void show_success_popup(String temp) {

        final View dialogView = View.inflate(this, R.layout.dialog_success, null);
        final Dialog dialog = new Dialog(this);//, R.style.MyAlertDialogStyle);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        lp.width = width * 3 / 4;
        lp.height = height * 2 / 3;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        TextView txt1 = (TextView) dialog.findViewById(R.id.txt1);
        txt1.setText(temp);
        TextView txt3_done = (TextView) dialog.findViewById(R.id.txt3_done);
        TextView txt3_cancel = (TextView) dialog.findViewById(R.id.txt3_cancel);

        txt3_done.setOnClickListener(v -> {

            dialog.dismiss();

            if (payment.equals("cod")) {
                FoodnFine.getAppSharedPreference().saveRemark(et_any_instruction.getText().toString());
                FoodnFine.getAppSharedPreference().setEstimateValue(et_estimated_value_of_contents.getText().toString());
                SessionManager sessionManager = new SessionManager(SendPackageActivity.this);
                sessionManager.redirect("PACKAGE");
                Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                intent.putExtra(INTENT_STRING_ORDER_TYPE,"sendPackage");
                intent.putExtra(INTENT_STRING_TOTAL_PRICE,String.valueOf(totalcharges));
                intent.putExtra("total",String.valueOf(totalcharges));
                startActivity(intent);
            } else if (payment.equals("op")) {
                sessionManager.successCall("payment_online");
                sessionManager.setPayType("Online");
                Intent intent = new Intent(this, OnlinePaymentActivity.class);
                intent.putExtra("amount", String.valueOf(totalcharges));
                intent.putExtra("pay_amount", String.valueOf(totalcharges));
                //intent.putExtra("ordertype", "Send Package");
                intent.putExtra("INTENT_STRING_ORDER_TYPE", "sendPackage");
                startActivity(intent);
            }
        });
        txt3_cancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void inItViewModel(){
        viewModel = ViewModelProviders.of(this).get(SendPackageViewModel.class);
        viewModel.setCallback(this);
    }

    /*@Override
    public void onSuccessGetCharges(String oneKmCharges, String lessThan5KmCharges, String greaterFiveToTenKmPrice, String greaterTenKmPrice) {
        chargesInDoubleInOneKm = Integer.parseInt(oneKmCharges);
        chargesInDoubleInFiveKm = Integer.parseInt(lessThan5KmCharges);
        chargesInDoubleInTenKm = Integer.parseInt(greaterFiveToTenKmPrice);
        chargesInDoubleInGrater10Km = Integer.parseInt(greaterTenKmPrice);
    }*/

    public void onSuccessGetCharges(String oneKmCharges, String lessThan5KmCharges, String greaterFiveToTenKmPrice, String greaterTenKmPrice) {
        chargesInDoubleInOneKm = Integer.parseInt(oneKmCharges);
        chargesInDoubleInFiveKm = Integer.parseInt(lessThan5KmCharges);
        chargesInDoubleInTenKm = Integer.parseInt(greaterFiveToTenKmPrice);
        chargesInDoubleInGrater10Km = Integer.parseInt(greaterTenKmPrice);
    }

    @Override
    public void onSuccessGetCharges(String fixed_cost, String per_kilometer) {

    }

    @Override
    public void onfailure() {

    }

    @SuppressLint("SetTextI18n")
    private void priceDataPopulate(String chargesPrice) {
        tv_grandTotalItem.setText("₹" + chargesPrice);
        tv_actual_price.setText("₹" + chargesPrice);
        tv_charges_amount.setText("₹" + chargesPrice);
    }

    private boolean checkValidation(){
        boolean validated;
        int count = 0 ;
        if(tv_pickup_address.getText().toString().equalsIgnoreCase("")||tv_pickup_address.getText().length() == 0)
        {
            Toast.makeText(this, "Select Pick up Address", Toast.LENGTH_SHORT).show();
        }else {
            count++;
        }
        if(tv_delivery_address.getText().toString().equalsIgnoreCase("")||tv_delivery_address.getText().length() == 0)
        {
            Toast.makeText(this,"Select Delivery Address",Toast.LENGTH_SHORT).show();
        }else {
            count++;
        }
        if(tv_package_content.getText().toString().equalsIgnoreCase("")||tv_package_content.getText().length() == 0)
        {
            validated = false;
            Toast.makeText(this,"Select Content Type ",Toast.LENGTH_SHORT).show();
        }else {
            validated = true;
            count++;
        }
        if(count == 3){
            return validated;
        }else{
            return false;
        }

    }
    private void allDataSaveInLocal(){
        FoodnFine.getAppSharedPreference().savePickupAdd(String.valueOf(tv_pickup_address.getText()));
        FoodnFine.getAppSharedPreference().saveDeliveryAdd(String.valueOf(tv_delivery_address.getText()));
        FoodnFine.getAppSharedPreference().savePackageId(String.valueOf(tv_package_content.getText()));
        FoodnFine.getAppSharedPreference().saveDistance(distance);
        FoodnFine.getAppSharedPreference().saveCharges(totalcharges);
    }

}
