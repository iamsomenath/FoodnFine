package snd.orgn.foodnfine.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BackHandledFragment;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.util.LoadingDialog;


public class payment_wallet extends BackHandledFragment {

    private static final String TAG = "payWallet";
    String paystasts = "Paid", paytype = "Wallet";

    TextView btn_add_money, wallet_balance, payable_amount;
    LinearLayout msg;
    Button btn_submit_wallet;
    String w_balance, userId;
    int iw_balance, igrand_total;
    SessionManager sessionManager;
    String addAmount;
 //   List<pojo_checkData> data;
    String lunchdeliveryslot;
    String dinnerdeliveryslot;
    String fulladdress;
    String coupon_id;
    private String PaymentId = "000000";
    private String pay_type = "Wallet";
    private String pay_stat = "Paid";
    private String redirect, pay_total, total_amt,vehicleNo, deliverytime, deliverydate;
    LoadingDialog loadingDialog;

    HashMap<String, String> dataDetails;

    SharedPreferences prefs;

    public payment_wallet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_wallet, container, false);

        sessionManager = new SessionManager(getActivity().getApplication());

        dataDetails = sessionManager.getDetails();
        //w_balance = dataDetails.get(SessionManager.WALLET_BALANCE);
        userId = dataDetails.get(SessionManager.USER_ID);

        getMoneyWallet();

        prefs = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        fulladdress = dataDetails.get(SessionManager.LOCATION) + "#" + dataDetails.get(SessionManager.DELIVERY_ADDRESS);
        coupon_id = dataDetails.get(SessionManager.COUPON_ID);
        redirect = dataDetails.get(SessionManager.REDIRECT);
        //total_amt = dataDetails.get(SessionManager.CART_VALUE);
        //pay_total = dataDetails.get(SessionManager.PAYABLE_AMOUNT);

        loadingDialog = new LoadingDialog(getContext());
        btn_add_money = view.findViewById(R.id.btn_add_money_payment_wallet);
        btn_submit_wallet = view.findViewById(R.id.btn_submit_payment_wallet);
        wallet_balance = view.findViewById(R.id.tv_payment_wallet_walletBalance);
        payable_amount = view.findViewById(R.id.tv_payment_wallet_payAmount);
        msg = view.findViewById(R.id.layout_paymentWallet_msg);

        deliverytime = dataDetails.get(SessionManager.DELIVERY_TIME);
        deliverydate = dataDetails.get(SessionManager.DELIVERY_DATE);
        vehicleNo = dataDetails.get(SessionManager.VEHICLE_NO);

        total_amt = dataDetails.get(SessionManager.CART_VALUE);
        pay_total = dataDetails.get(SessionManager.PAYABLE_AMOUNT);
        payable_amount.setText(pay_total);

        return view;
    }

    //font changing method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        FontChanger fontChanger = new FontChanger(getActivity().getAssets(), "ProximaNovaLight.otf");
//        fontChanger.replaceFonts((ViewGroup) this.getView());
    }


    public void placeOrder() {

        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.showDialog();

//        ArrayList<Cart_Details> dummydata = sessionManager.getArrayList();
//        JSONArray reqArr = new JSONArray();
//        try {
//            for (Cart_Details c : dummydata) {
//                JSONObject reqObj2 = new JSONObject();
//                reqObj2.put("menu_name", c.getFoodName());
//                reqObj2.put("menu_id", c.getFoodId());
//                reqObj2.put("price", c.getSellingPrice());
//                reqObj2.put("quantity", c.getQuantity());
//                reqArr.put(reqObj2);
//            }
//            //loadingDialog.showDialog();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Constants.ROOT_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            AppInterface api = retrofit.create(AppInterface.class);
//            Call<pojo_placeMenu> call = api.placeorder(reqArr, total_amt, pay_total, deliverytime, userId, fulladdress,
//                    pay_stat, pay_type, PaymentId, coupon_id, dataDetails.get(SessionManager.DABBA_TYPE),vehicleNo);
//            call.enqueue(new Callback<pojo_placeMenu>() {
//                @Override
//                public void onResponse(Call<pojo_placeMenu> call, Response<pojo_placeMenu> response) {
//                    if (response.body() != null) {
//
//                        assert response.body() != null;
//                        if (response.body().getSuccess().equalsIgnoreCase("SUCCESS")) {
//
//                            String orderId = response.body().getOrderId();
//
//                            sessionManager.message("success");
//                            sessionManager.setCartCount("0");
//                            sessionManager.update_dhaba("", "", "");
//
//                            Toast toast = Toast.makeText(getActivity(), "Your orders (Order ID - " + orderId +
//                                    ") has been placed successfully", Toast.LENGTH_LONG);
//                            TextView view1 = (TextView) toast.getView().findViewById(android.R.id.message);
//                            view1.setTextColor(Color.WHITE);
//                            view1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf"));
//                            toast.show();
//
//                            Intent intent = new Intent(getActivity(), OrderList.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            getActivity().finish();
//                        } else {
//                            Toast toast = Toast.makeText(getActivity(), "Payment Failed...", Toast.LENGTH_SHORT);
//                            TextView view1 = (TextView) toast.getView().findViewById(android.R.id.message);
//                            view1.setTextColor(Color.WHITE);
//                            view1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf"));
//                            toast.show();
//                            getActivity().finish();
//                        }
//                    }
//                    loadingDialog.hideDialog();
//                }
//
//                @Override
//                public void onFailure(Call<pojo_placeMenu> call, Throwable t) {
//                    Log.d(TAG, "onFailure: " + t.getMessage());
//                    loadingDialog.hideDialog();
//                }
//            });
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Do you want to place order?");
        alertDialog.setIcon(R.drawable.checked);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                placeOrder();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void getMoneyWallet() {

        final LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.showDialog();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        final AppInterface services = retrofit.create(AppInterface.class);
//
//        final Call<List<pojo_checkWallet>> wallet_bal = services.checkWallet(userId);
//        wallet_bal.enqueue(new Callback<List<pojo_checkWallet>>() {
//            @Override
//            public void onResponse(Call<List<pojo_checkWallet>> callRequest, Response<List<pojo_checkWallet>> response) {
//                if (response.body() != null) {
//                    for (int i = 0; i < response.body().size(); i++) {
//                        w_balance = response.body().get(i).getWBalance();
//                        sessionManager.walletBalance(w_balance);
//                        wallet_balance.setText(w_balance);
//
//                        proceed_next();
//                    }
//                    loadingDialog.hideDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<pojo_checkWallet>> callRequest, Throwable t) {
//                loadingDialog.hideDialog();
//                //Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }

    private void proceed_next() {
        iw_balance = Integer.parseInt(w_balance);
        igrand_total = Integer.parseInt(pay_total);

        if (igrand_total > iw_balance) {

            addAmount = String.valueOf(igrand_total - iw_balance);
            msg.setVisibility(View.VISIBLE);

            AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                    setMessage("\u2709 Wallet Balance ₹" + iw_balance + " is low to place order amount ₹" + igrand_total +
                            ". Please add money to wallet to proceed payment.").show();
            TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            textView.setTextSize(16);
            textView.setTextColor(Color.RED);
            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf");
            textView.setTypeface(face);

            getFragmentManager().popBackStack();
        } else {

            msg.setVisibility(View.GONE);
            btn_submit_wallet.setClickable(true);
            btn_submit_wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderConfirm();
                }
            });
        }
    }

}
