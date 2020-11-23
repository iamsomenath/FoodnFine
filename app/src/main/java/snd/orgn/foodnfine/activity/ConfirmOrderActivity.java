package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.CreateOrderAdapter;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackApplyCouponActivity;
import snd.orgn.foodnfine.callbacks.CallbackConfirmOrderActivity;
import snd.orgn.foodnfine.callbacks.CallbackGetCanCelOrder;
import snd.orgn.foodnfine.callbacks.CallbackSelectedCartItemUpdate;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.response.CartDatum;
import snd.orgn.foodnfine.rest.response.RestResponseCart;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.ConfirmOrderActivityViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_CART_DETAIL;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_TOTAL_PRICE;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_GROCERY;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_RESTAURANT;
import static snd.orgn.foodnfine.constant.AppConstants.RUPREES_SYMBOL;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_GROCERY;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_RESTAURANT;

public class ConfirmOrderActivity extends BaseActivity implements CallbackSelectedCartItemUpdate, CallbackConfirmOrderActivity,
        CallbackApplyCouponActivity, CallbackGetCanCelOrder {

    @BindView(R.id.rv_createOrder)
    RecyclerView rv_createOrder;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.couponcode)
    EditText couponcode;
    @BindView(R.id.applyCode)
    Button applyCode;
    @BindView(R.id.cancelCode)
    Button cancelCode;
    @BindView(R.id.priceDetails)
    LinearLayout priceDetails;
    @BindView(R.id.offers)
    TextView offers;
    CreateOrderAdapter adapter;
    @BindView(R.id.iv_confirmOrder_back)
    ImageView iv_confirmOrder_back;
    @BindView(R.id.viewDetails)
    LinearLayout viewDetails;
    @BindView(R.id.information)
    ImageView information;
    @BindView(R.id.tv_confirmOrder_type)
    TextView tv_confirmOrder_type;
    @BindView(R.id.tv_confirmOrder_totalPrice)
    TextView tv_confirmOrder_totalPrice;
    @BindView(R.id.tv_cofirmOrderDiscountPrice)
    TextView tv_cofirmOrderDiscountPrice;
    @BindView(R.id.tv_confirmOrder_delivery_address)
    EditText tv_confirmOrder_delivery_address;
    @BindView(R.id.et_ConfirmOrder_remark)
    EditText et_ConfirmOrder_remark;
    @BindView(R.id.tv_confirmOrder_item_price)
    TextView tv_confirmOrder_item_price;
    @BindView(R.id.tv_confirmOrder_item_count)
    TextView tv_confirmOrder_item_count;
    @BindView(R.id.ivBtn_confirmOrderChangeAddress)
    ImageView ivBtn_confirmOrderChangeAddress;
    @BindView(R.id.tvBtn_ConfirmOrder_continue)
    CardView tvBtn_ConfirmOrder_continue;
    @BindView(R.id.rg_payment_home)
    RadioGroup rg_payment_option;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    RestResponseCart restResponseCart;
    private List<CartDatum> cartDatumList;
    private String OrderType = "";
    private String seletedOrderType = "";
    private String seletedCartItemId = "";
    private String seletedCartItemQuantity = "";
    private String seletedRestGroceryId = "";

    private String copoun_code = "";
    private String coupon_category = "";
    private String coupon_id = "";
    private String copoun_type = "";
    private String total = "";

    public static int discountAmount = 0;
    public static int deliveryCharge = 0;
    public static int cancellationCharge = 0;
    public static int fixedCharge = (int) Double.parseDouble(DeliveryEverything.getAppSharedPreference().getFixedCost());

    private Integer quantity;
    private Integer totalPrice;
    private Integer itemprice;
    ConfirmOrderActivityViewModel viewModel;
    LoadingDialog loadingDialog;
    String placeId1;
    View rootView;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
    String payment = "null";
    SessionManager sessionManager;

    @BindView(R.id.getAddress)
    ImageView getAddress;
    @BindView(R.id.details_address)
    EditText details_address;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);

        deliveryCharge = (int) Math.floor(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getDeliveryCost()));
        //Log.d("DELIVERY", deliveryCharge+"");
        //Toast.makeText(this, "DELIVERY : " + deliveryCharge, Toast.LENGTH_SHORT).show();
        loadingDialog = new LoadingDialog(this);
        initViewModel();
        hideStatusBarcolor();

        sessionManager = new SessionManager(this);

        initFields();
        setupOnClick();
        dataPopulate();
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, ConfirmOrderActivity.this);

        loadingDialog.showDialog();
        viewModel.GetAllCancelOrder(DeliveryEverything.getAppSharedPreference().getUserId());
    }

    @Override
    public void initFields() {
        restResponseCart = new RestResponseCart();
        restResponseCart = (RestResponseCart) getIntent().getSerializableExtra(INTENT_STRING_CART_DETAIL);
        OrderType = getIntent().getStringExtra(INTENT_STRING_ORDER_TYPE);
        DeliveryEverything.getAppSharedPreference().saveDeliveryAdd(String.valueOf(tv_confirmOrder_delivery_address.getText()));
        seletedRestGroceryId = restResponseCart.getRestGrocery();
        DeliveryEverything.getAppSharedPreference().setSeletedRestGroceryId(seletedRestGroceryId);
        if(OrderType.equalsIgnoreCase(ORDER_TYPE_GROCERY)){
            seletedOrderType = TYPE_GROCERY;
        }else if(OrderType.equalsIgnoreCase(ORDER_TYPE_RESTAURANT)){
            seletedOrderType = TYPE_RESTAURANT;
        } else
            seletedOrderType = OrderType;
        cartDatumList = new ArrayList<>();
        cartDatumList.addAll(restResponseCart.getCartData());
        DeliveryEverything.getAppSharedPreference().saveArrayList(restResponseCart.getCartData());
        initRecyclerOrderList();
        adapter.clearcartDetails();
        adapter.addAllcartDeatils(cartDatumList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupOnClick() {
        iv_confirmOrder_back.setOnClickListener(v -> {
            super.onBackPressed();
            finish();
        });
        ivBtn_confirmOrderChangeAddress.setOnClickListener(v -> {
            findPlace();
        });

        tvBtn_ConfirmOrder_continue.setOnClickListener(v->{

            /*if (et_ConfirmOrder_remark.getText().toString().equals("")||et_ConfirmOrder_remark.getText().toString().isEmpty()){
                Toast.makeText(this,"Enter Any instructions?",Toast.LENGTH_SHORT).show();
            }else{
                DeliveryEverything.getAppSharedPreference().saveRemark(String.valueOf(et_ConfirmOrder_remark.getText()));
                goToSelectPaymentMethods();
            }*/
            DeliveryEverything.getAppSharedPreference().saveRemark(String.valueOf(et_ConfirmOrder_remark.getText()));
            goToSelectPaymentMethods();
        });

        getAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetFromSavedAddressActivity.class);
            intent.putExtra("VAL", "3");
            startActivityForResult(intent, 3);
        });

        information.setOnClickListener(view -> {
            Typeface font = Typeface.createFromAsset(getAssets(), "Raleway_Regular.ttf");
            /*Toast toast = Toast.makeText(
                    this,
                    "Delivery charges\n\n" +
                            "1. Up to 1Km : ₹" + DeliveryEverything.getAppSharedPreference().getCost1() + "\n" +
                            "2. 1Km - 5Km : ₹" + DeliveryEverything.getAppSharedPreference().getCost2() + "\n" +
                            "3. 5Km - 10Km : ₹" + DeliveryEverything.getAppSharedPreference().getCost3() + "\n" +
                            "4. Above 10Km : ₹" + DeliveryEverything.getAppSharedPreference().getCost4(),
                    Toast.LENGTH_LONG
            );*/
            Toast toast = Toast.makeText(
                    this,
                    "Delivery charges\n\n" +
                            "1. Fixed Cost : ₹" + DeliveryEverything.getAppSharedPreference().getFixedCost() + "\n" +
                            "2. Per Km Charges : ₹" + DeliveryEverything.getAppSharedPreference().getPerKm(),
                    Toast.LENGTH_LONG
            );
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTypeface(font);
            toastTV.setTextSize(15f);
            toastTV.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 10);
            toast.show();
        });

        offers.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), OffersActivity.class);
            startActivity(intent);
        });

        cancelCode.setOnClickListener(view -> {
            discountAmount = 0;
            applyCode.setVisibility(View.VISIBLE);
            cancelCode.setVisibility(View.GONE);
            couponcode.setText("");
            String price2 = String.valueOf(restResponseCart.getSumPrice() + deliveryCharge);
            tv_confirmOrder_item_price.setText("₹ " + price2 + ".00");

            copoun_code = "";
            coupon_category = "";
            coupon_id = "";
            copoun_type = "";
        });

        applyCode.setOnClickListener(view -> {
            if (couponcode.getText().toString().isEmpty()) {
                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please enter coupon code", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (OrderType.equalsIgnoreCase(ORDER_TYPE_GROCERY)) {
                coupon_category = "2";
            } else if (OrderType.equalsIgnoreCase(ORDER_TYPE_RESTAURANT)) {
                coupon_category = "1";
            } else
                coupon_category = "0";
            loadingDialog.showDialog();
            viewModel.applyCoupon(couponcode.getText().toString(), DeliveryEverything.getAppSharedPreference().getUserId(),
                    coupon_category, sessionManager.getShopId());
        });

        viewDetails.setOnClickListener(view -> {
            total = String.valueOf(restResponseCart.getSumPrice() + deliveryCharge + cancellationCharge + fixedCharge - discountAmount);
            Intent intent = new Intent(getApplicationContext(), ConfirmOrderDetails.class);
            intent.putExtra("PRICE", String.valueOf(restResponseCart.getSumPrice()));
            intent.putExtra("DC", String.valueOf(deliveryCharge));
            intent.putExtra("DIS", String.valueOf(discountAmount));
            intent.putExtra("CANCEL", String.valueOf(cancellationCharge));
            intent.putExtra("FC", String.valueOf(fixedCharge));
            intent.putExtra("TOTAL", total);
            startActivity(intent);
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ConfirmOrderActivityViewModel.class);
        viewModel.setCallback(this);
        viewModel.setCallback2(this);
        viewModel.setCallback3(this);
    }

    private void initRecyclerOrderList() {
        adapter = new CreateOrderAdapter(this);
        adapter.setCallback(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_createOrder.setLayoutManager(layoutManager);
        rv_createOrder.setAdapter(adapter);
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @SuppressLint("SetTextI18n")
    private void dataPopulate() {
        tv_confirmOrder_type.setText(OrderType);
        tv_confirmOrder_delivery_address.setText(DeliveryEverything.getAppSharedPreference().getCurrentLocation());
        tv_confirmOrder_totalPrice.setText(RUPREES_SYMBOL + restResponseCart.getSumPrice());
        String quant = String.valueOf(restResponseCart.getSumcartCount());
        String price = String.valueOf(restResponseCart.getSumPrice() + deliveryCharge + cancellationCharge + fixedCharge - discountAmount);
        if (quant.equals("1")) {
            tv_confirmOrder_item_count.setText(quant + " item");
        } else {
            tv_confirmOrder_item_count.setText(quant + " items");
        }
        tv_confirmOrder_item_price.setText(RUPREES_SYMBOL + price + ".00");
        Log.d("TESTING", deliveryCharge + "");
    }

    public void findPlace() {
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, 1);
    }

    @SuppressLint({"SetTextI16n", "SetTextI18n"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                placeId1 = place.getId();
                /*queriedLocation = place.getLatLng();
                String lat = Double.toString(Objects.requireNonNull(place.getLatLng()).latitude);
                String lon = Double.toString(place.getLatLng().longitude);*/

                Location startPoint = new Location("locationA");
                startPoint.setLatitude(place.getLatLng().latitude);
                startPoint.setLongitude(place.getLatLng().longitude);
                Location endPoint = new Location("locationB");
                endPoint.setLatitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()));
                endPoint.setLongitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()));
                double distance = startPoint.distanceTo(endPoint) / 1000;
                //Toast.makeText(activity, distance + "", Toast.LENGTH_SHORT).show();
                if (distance <= 1)
                    //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInOneKm));
                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost1());
                else if (distance < 5)
                    //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInFiveKm));
                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost2());
                else if (distance < 10)
                    //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInTenKm));
                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost3());
                else //if(distance>10)
                    //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInGrater10Km));
                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost4());

                tv_confirmOrder_delivery_address.setText(place.getAddress());
                DeliveryEverything.getAppSharedPreference().saveDeliveryAdd(String.valueOf(tv_confirmOrder_delivery_address.getText()));
                DeliveryEverything.getAppSharedPreference().saveCurrentLocation(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 3) { // to fetch pickup saved address
            try {
                if (data.hasExtra("MESSAGE")) {
                    //Log.d("TESTS", data.getSerializableExtra("MESSAGE").toString());
                    AddressDetails addressDetails = (AddressDetails) data.getSerializableExtra("MESSAGE");
                    details_address.setText(addressDetails.getBuilding() + ", " + addressDetails.getHouse() + ", " +
                            addressDetails.getLandmark() + ", " + addressDetails.getLocation());
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onEditedCartItem(String cartId, String qty, String price) {
        loadingDialog.showDialog();
        this.seletedCartItemQuantity = qty;
        this.seletedCartItemId = cartId;

        quantity = Integer.parseInt(qty);
        itemprice = (int) Double.parseDouble(price);
        totalPrice = quantity * itemprice;
        viewModel.updateCartItem(getuserdataForUpdateCartCart());
    }

    @Override
    public void onDeletedCartItem(String cartId) {
        loadingDialog.hideDialog();
        UserData userData = new UserData();
        userData.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        userData.setCartId(cartId);
        viewModel.deleteCartItem(userData);
        Toast.makeText(this, "Cart items deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSuccessUpadteCartItem() {
        viewModel.getCartDetails(getuserdataForCartDetails());
    }

    @Override
    public void onSuccessDeleteCartItem() {
        viewModel.getCartDetails(getuserdataForCartDetails());
    }

    @Override
    public void onSuccessCartDetails(RestResponseCart cartDetails) {
        loadingDialog.hideDialog();
        this.restResponseCart = cartDetails;
        cartDatumList = new ArrayList<>();
        cartDatumList.addAll(restResponseCart.getCartData());
        DeliveryEverything.getAppSharedPreference().saveArrayList(restResponseCart.getCartData());
        seletedRestGroceryId = restResponseCart.getRestGrocery();
        DeliveryEverything.getAppSharedPreference().setSeletedRestGroceryId(seletedRestGroceryId);
        initRecyclerOrderList();
        adapter.clearcartDetails();
        adapter.addAllcartDeatils(cartDatumList);
        adapter.notifyDataSetChanged();
        dataPopulate();
    }

    @Override
    public void onError(String message) {loadingDialog.hideDialog();
        loadingDialog.hideDialog();
    }

    @Override
    public void onNetworkError(String message) {loadingDialog.hideDialog();
        loadingDialog.hideDialog();
    }

    private UserData getuserdataForUpdateCartCart() {
        UserData userData = new UserData();
        userData.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        userData.setCartId(seletedCartItemId);
        userData.setPrice(itemprice);
        userData.setQuantity(seletedCartItemQuantity);
        return userData;
    }

    private UserData getuserdataForCartDetails() {
        UserData data = new UserData();
        data.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        switch (OrderType) {
            case ORDER_TYPE_GROCERY:
            case "grocery":
                data.setOrderType(TYPE_GROCERY);
                seletedOrderType = TYPE_GROCERY;
                break;
            case ORDER_TYPE_RESTAURANT:
                case "restaurant":
                data.setOrderType(TYPE_RESTAURANT);
                seletedOrderType = TYPE_RESTAURANT;
                break;
            default:
                String type = "";
                SessionManager sessionManager = new SessionManager(this);
                if (sessionManager.getKeyOrderType().equalsIgnoreCase("5")) {
                    type = "Swadesi Product";
                } else if (sessionManager.getKeyOrderType().equalsIgnoreCase("6")) {
                    type = "Medicine Product";
                } else {
                    type = "Electronic Product";
                }
                data.setOrderType(type);
                seletedOrderType = type;
                break;
        }

        return data;
    }

    private void goToSelectPaymentMethods(){

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
            scrollView.scrollTo(0, scrollView.getBottom());
            Snackbar.make(this.findViewById(android.R.id.content),
                    "Please select payment option", Snackbar.LENGTH_SHORT).show();
            return;
        }

        int selectedId2 = radioGroup.getCheckedRadioButtonId();
        RadioButton type2 = (RadioButton) findViewById(selectedId2);
        if (selectedId2 == -1) {
            //Toast.makeText(ConfirmOrderActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
            Snackbar.make(this.findViewById(android.R.id.content),
                    "Please select Contact OR Non-contact delivery", Snackbar.LENGTH_SHORT).show();
            return;
        } /*else {
            Toast.makeText(ConfirmOrderActivity.this, type.getText(), Toast.LENGTH_SHORT).show();
        }*/

        total = String.valueOf(restResponseCart.getSumPrice() + deliveryCharge + cancellationCharge + fixedCharge - discountAmount);
        if (payment.equals("cod")) {
            sessionManager.successCall("payment_cod");
            sessionManager.setPayType("COD");
            DeliveryEverything.getAppSharedPreference().saveDeliveryAdd(String.valueOf(tv_confirmOrder_delivery_address.getText()));
            DeliveryEverything.getAppSharedPreference().setOrderType(seletedOrderType);
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.redirect("NORMAL");
            Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
            //intent.putExtra(INTENT_STRING_TOTAL_PRICE, String.valueOf(restResponseCart.getSumPrice()));
            intent.putExtra(INTENT_STRING_TOTAL_PRICE, total);
            intent.putExtra(INTENT_STRING_ORDER_TYPE, OrderType);
            // newly added
            intent.putExtra("delivery_charge", String.valueOf(deliveryCharge));
            intent.putExtra("cancellation_charge", String.valueOf(cancellationCharge));
            intent.putExtra("discount_amount", String.valueOf(discountAmount));
            intent.putExtra("copoun_type", copoun_type);
            intent.putExtra("copoun_code", copoun_code);
            intent.putExtra("coupon_id", coupon_id);
            intent.putExtra("coupon_category", coupon_category);
            intent.putExtra("total", total);
            intent.putExtra("order_actual_amount", String.valueOf(restResponseCart.getSumPrice()));
            intent.putExtra("remark_type", type2.getText().toString());
            //Toast.makeText(this, deliveryCharge + " " + cancellationCharge + " " + discountAmount + " ", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, copoun_type + " " + copoun_code + " " + coupon_id + " " + coupon_category, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else /*if (payment.equals("op"))*/ {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Confirm");
            alertDialog.setMessage("Do you want to place order?");
            alertDialog.setIcon(R.drawable.confirm);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(Html.fromHtml("<font color='#009494'>Yes"),
                    (dialog, which) -> {
                        dialog.dismiss();
                        sessionManager.successCall("payment_online");
                        sessionManager.setPayType("Online");
                        Intent intent = new Intent(this, OnlinePaymentActivity.class);
                        //intent.putExtra("amount", String.valueOf(restResponseCart.getSumPrice()));
                        intent.putExtra("amount", total);
                        //intent.putExtra("pay_amount", String.valueOf(restResponseCart.getSumPrice()));
                        intent.putExtra("pay_amount", total);
                        DeliveryEverything.getAppSharedPreference().setOrderType(seletedOrderType);
                        intent.putExtra("ordertype", OrderType);
                        // newly added
                        intent.putExtra("delivery_charge", String.valueOf(deliveryCharge));
                        intent.putExtra("cancellation_charge", String.valueOf(cancellationCharge));
                        intent.putExtra("discount_amount", String.valueOf(discountAmount));
                        intent.putExtra("copoun_type", copoun_type);
                        intent.putExtra("copoun_code", copoun_code);
                        intent.putExtra("coupon_id", coupon_id);
                        intent.putExtra("coupon_category", coupon_category);
                        intent.putExtra("total", total);
                        intent.putExtra("order_actual_amount", String.valueOf(restResponseCart.getSumPrice()));
                        intent.putExtra("remark_type", type2.getText().toString());
                        startActivity(intent);
                    });
            alertDialog.setNegativeButton(Html.fromHtml("<font color='#00585e'>No"),
                    (dialog, which) -> dialog.cancel());
            alertDialog.show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessCoupon(JSONObject response) {
        loadingDialog.hideDialog();
        int price = restResponseCart.getSumPrice();
        //Log.d("RESPONSE", response.toString() + " " + price);
        try {
            int minimum_order_amount = (int) Double.parseDouble(response.getString("minimum_order_amount"));
            int discount_amt = 0;
            //Log.d("RESPONSE", minimum_order_amount + " " + discount_amt);
            if (price >= minimum_order_amount) {
                cancelCode.setVisibility(View.VISIBLE);
                applyCode.setVisibility(View.GONE);

                Toast.makeText(this, "Coupon code applied successfully", Toast.LENGTH_SHORT).show();

                if (response.getString("discount_type").equals("flat"))
                    discount_amt = (int) Double.parseDouble(response.getString("discount_amt"));
                else
                    discount_amt = (int) (price * Double.parseDouble(response.getString("discount_amt"))) / 100;

                discountAmount = discount_amt;

                copoun_code = response.getString("code");
                coupon_category = response.getString("coupon_category");
                coupon_id = response.getString("coupon_id");
                copoun_type = response.getString("discount_type");

                String price2 = String.valueOf(restResponseCart.getSumPrice() + deliveryCharge + cancellationCharge - discountAmount);
                tv_confirmOrder_item_price.setText("₹ " + price2 + ".00");
            } else {
                /*new MaterialDialog.Builder(this)
                        //.title(getResources().getString(R.string.dialogTitle_logout))
                        .content("Minimum order amount should be ₹" + minimum_order_amount + " to apply this code")
                        .positiveText("Cancel")
                        .positiveColor(ContextCompat.getColor(this, R.color.button_and_vespac_red_color))
                        .onPositive((dialog, which) -> {
                            dialog.dismiss();
                        }).show();*/

                android.app.AlertDialog.Builder alertDialog2 = new android.app.AlertDialog.Builder(this);
                //alertDialog2.setTitle("Unable");
                alertDialog2.setMessage("Minimum order amount should be ₹" + minimum_order_amount + " to apply this code");
                alertDialog2.setPositiveButton("Ok",
                        (dialog, which) -> {
                            dialog.dismiss();
                        });
                alertDialog2.setCancelable(false);
                alertDialog2.show();
            }
        } catch (JSONException e) {
            Log.d("EXCEP", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    public void onFailureCoupon(String response) {
        loadingDialog.hideDialog();
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkErrorCoupon(String message) {
        loadingDialog.hideDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessGetCancelOrder(JSONObject response) {
        loadingDialog.hideDialog();
        try {
            cancellationCharge = Integer.parseInt(response.getString("order_amount"));
            dataPopulate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailureGetCancelOrder(String response) {
        loadingDialog.hideDialog();
        //Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkErrorGetCancelOrder(String message) {
        loadingDialog.hideDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
