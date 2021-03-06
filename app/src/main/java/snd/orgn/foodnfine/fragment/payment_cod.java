package snd.orgn.foodnfine.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import snd.orgn.foodnfine.activity.DasboardActivity;
import snd.orgn.foodnfine.callbacks.CallbackAssignOrder;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.MyOrdersActivity;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BackHandledFragment;
import snd.orgn.foodnfine.callbacks.CallbackSendPackage;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.model.utility.PickupData;
import snd.orgn.foodnfine.rest.request.PlaceOrderRequest;
import snd.orgn.foodnfine.rest.response.CartDatum;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.SendPackageViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.TYPE_ELECTRONIC;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_GROCERY;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_MEDICAL;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_RESTAURANT;
import static snd.orgn.foodnfine.constant.AppConstants.TYPE_SWADESI;

public class payment_cod extends BackHandledFragment implements CallbackSendPackage, CallbackAssignOrder {

    private static final String TAG = "payCod";
    TextView payable_amount;
    CardView btn_submit_cod;
    String package_id, pickupAdd, deliveryAdd, distance, userId;
    private String PaymentId = "0";
    private String pay_type = "COD";
    private String pay_stat = "Pending";
    private JSONArray orderDetails;
    private String remark = "remark";
    private String orderType = "order_type";
    private String groceryRestaurantId = "restId";
    private List<CartDatum> cartDatumList;

    String grand_total_order;
    LoadingDialog loadingDialog;

    SendPackageViewModel viewModel;
    View view;

    public payment_cod() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_payment_cod, container, false);
        pickupAdd = FoodnFine.getAppSharedPreference().getPickupAdd();
        deliveryAdd = FoodnFine.getAppSharedPreference().getDeliveryAdd();
        package_id = FoodnFine.getAppSharedPreference().getPackageId();
        distance = FoodnFine.getAppSharedPreference().getDistance();
        grand_total_order = FoodnFine.getAppSharedPreference().getCharges();
        userId = FoodnFine.getAppSharedPreference().getUserId();
        remark = FoodnFine.getAppSharedPreference().getRemarkse();
        orderType = FoodnFine.getAppSharedPreference().getOrderType();
        //groceryRestaurantId = DeliveryEverything.getAppSharedPreference().getSelectedRestGroceryId();
        groceryRestaurantId = new SessionManager(getActivity()).getKeyOrderType();
        cartDatumList = new ArrayList<>();
        orderDetails = new JSONArray();
        loadingDialog = new LoadingDialog(getContext());
        btn_submit_cod = view.findViewById(R.id.btn_submit_payment_cod);
        payable_amount = view.findViewById(R.id.tv_payment_cod_payAmount);

        initViewModel();

        payable_amount.setText("₹" + getActivity().getIntent().getStringExtra("total"));

        btn_submit_cod.setOnClickListener(v -> orderConfirm());

        // Inflate the layout for this fragment
        return view;
    }

    //font changing method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        FontChanger fontChanger = new FontChanger(getActivity().getAssets(), "ProximaNovaLight.otf");
//        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public String getTagText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void orderConfirm() {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Confirm");
            alertDialog.setMessage("Do you want to place order?");
            alertDialog.setIcon(R.drawable.confirm);
            alertDialog.setCancelable(false);
            //alertDialog.setPositiveButton(Html.fromHtml("<font color='#009494'>Yes"), (dialog, which) -> {
            alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                loadingDialog.showDialog();
                if (TYPE_GROCERY.equals(orderType)) {
                    cartDatumList = FoodnFine.getAppSharedPreference().getArrayList();
                    JSONArray reqArr = new JSONArray();
                    try {
                        for (CartDatum c : cartDatumList) {
                            JSONObject reqObj2 = new JSONObject();
                            reqObj2.put("product_name", c.getProductName());
                            reqObj2.put("product_id", c.getProductId());
                            reqObj2.put("qty", c.getQty());
                            //reqObj2.put("total_price", c.getTotalPrice());
                            reqObj2.put("total_price", String.valueOf(Integer.parseInt(c.getQty()) * Double.parseDouble(c.getPrice())));
                            reqObj2.put("price", c.getPrice());
                            reqObj2.put("product_desc", c.getProductDesc());
                            reqObj2.put("weight", c.getWeight());
                            reqObj2.put("unit", c.getUnit());
                            reqObj2.put("cat_id", c.getCatId());
                            reqObj2.put("rest_grocery", c.getRestGrocery());
                            reqArr.put(reqObj2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    orderDetails = reqArr;
                    viewModel.placeOrderRequest(getPlaceOrderRequest());
                }
            });
            //alertDialog.setNeutralButton(Html.fromHtml("<font color='#00585e'>No"),
            alertDialog.setNeutralButton("No",
                    (dialog, which) -> dialog.cancel());
            alertDialog.show();
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SendPackageViewModel.class);
        viewModel.setCallbackSendPackage(this);
        viewModel.setCallback2(this);
    }

    private PlaceOrderRequest getPlaceOrderRequest() {
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();

        placeOrderRequest.setUserId(userId);
        placeOrderRequest.setPayMode(pay_type);
        placeOrderRequest.setPayId(PaymentId);
        placeOrderRequest.setPayStatus(pay_stat);
        //placeOrderRequest.setTotalPrice(String.valueOf(payable_amount.getText()));
        placeOrderRequest.setTotalPrice(grand_total_order);
        placeOrderRequest.setDelivarAdd(deliveryAdd);
        placeOrderRequest.setRemark(remark);
        placeOrderRequest.setOrderType(orderType);
        placeOrderRequest.setOrderDetails(orderDetails);
        placeOrderRequest.setRestGrocery(groceryRestaurantId);
        placeOrderRequest.setRestGroceryid(new SessionManager(getActivity()).getShopId());
        // newly added
        placeOrderRequest.setCancellation_charge(getActivity().getIntent().getStringExtra("cancellation_charge"));
        placeOrderRequest.setCopoun_code(getActivity().getIntent().getStringExtra("copoun_code"));
        placeOrderRequest.setCopoun_type(getActivity().getIntent().getStringExtra("copoun_type"));
        placeOrderRequest.setCoupon_category(getActivity().getIntent().getStringExtra("coupon_category"));
        placeOrderRequest.setCoupon_id(getActivity().getIntent().getStringExtra("coupon_id"));
        placeOrderRequest.setDelivery_charge(getActivity().getIntent().getStringExtra("delivery_charge"));
        placeOrderRequest.setDiscount_amount(getActivity().getIntent().getStringExtra("discount_amount"));
        placeOrderRequest.setOrder_actual_amount(getActivity().getIntent().getStringExtra("order_actual_amount"));
        placeOrderRequest.setRemark_type(getActivity().getIntent().getStringExtra("remark_type"));

        //Log.d("TESTTING", placeOrderRequest.getDelivery_charge() + " " + placeOrderRequest.getCancellation_charge() + " " + placeOrderRequest.getDiscount_amount());
        //Log.d("TESTTING", placeOrderRequest.getCoupon_category() + " " + placeOrderRequest.getCoupon_id() + " " + placeOrderRequest.getCopoun_type() + " " + placeOrderRequest.getCopoun_code());

        return placeOrderRequest;
    }

    @Override
    public void onSuccess(String orderNo) {
        // need to call a new api with restResponse.getOrder_no()
        loadingDialog.hideDialog();
        Toast.makeText(getActivity(), "Order submitted Successfully", Toast.LENGTH_SHORT).show();

        loadingDialog.showDialog();
        //viewModel.makeOrderRequest(orderNo);
        gotoOrderDetails();
    }

    @Override
    public void OnError(String message) {
        loadingDialog.hideDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialog.hideDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void gotoOrderDetails() {
        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
        //Intent intent = new Intent(getActivity(), DasboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finishAffinity();
    }

    @Override
    public void onSuccessAssignOrder() {
        loadingDialog.hideDialog();
        //Toast.makeText(getContext(), "Hurray.........", Toast.LENGTH_SHORT).show();
        gotoOrderDetails();
    }

    @Override
    public void onErrorAssignOrder(@Nullable String message) {
        loadingDialog.hideDialog();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}