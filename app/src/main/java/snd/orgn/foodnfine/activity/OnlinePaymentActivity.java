package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.rest.response.RestResponseAssignOrder;
import snd.orgn.foodnfine.rest.response.RestResponsePickup;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.helper.other.NetworkHelper;
import snd.orgn.foodnfine.model.utility.PickupData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.PickupRequest;
import snd.orgn.foodnfine.rest.request.PlaceOrderRequest;
import snd.orgn.foodnfine.rest.response.CartDatum;
import snd.orgn.foodnfine.util.LoadingDialog;

import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.ERROR_MESSAGE;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class OnlinePaymentActivity extends AppCompatActivity {

    private static final String TAG = OnlinePaymentActivity.class.getSimpleName();
    SessionManager sessionManager;
    LoadingDialog loadingDialog;

    private String TransactionId;
    JSONArray orderDetails;

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private LinearLayout mainlayout;

    String fulladdress;
    String dabbacount;
    String areacode;
    String deliverytime;
    String grand_total;
    String grand_total_order;
    private String pay_type = "Online";
    private String pay_stat = "Paid";
    String source, call, message;
    String redirect, addWalletCall;
    private String bill_name;
    private String bill_email;
    private String postal_code;
    private String phone_no;
    private String payableAmount;
    private String pay_total, payable_amount;
    private String userId;
    private String deliverydate;

    private String copoun_code = "";
    private String coupon_category = "";
    private String coupon_id = "";
    private String copoun_type = "";
    private String discountAmount = "";
    private String deliveryCharge = "";
    private String cancellationCharge = "";
    private String order_actual_amount = "";
    private String remark_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_online_payment);

        mainlayout = findViewById(R.id.mainlayout);

        source = getIntent().getStringExtra("source");
        call = getIntent().getStringExtra("call");
        message = getIntent().getStringExtra("message");

        sessionManager = new SessionManager(getApplication());
        HashMap<String, String> data = sessionManager.getDetails();
        payableAmount = data.get(SessionManager.PAYABLE_AMOUNT);
        bill_name = data.get(SessionManager.USER_NAME);
        bill_email = data.get(SessionManager.USER_EMAIL);
        String bill_address = data.get(SessionManager.DELIVERY_ADDRESS);
        postal_code = data.get(SessionManager.AREA_CODE);
        phone_no = data.get(SessionManager.USER_MOBILE);
        addWalletCall = data.get(SessionManager.ADD_WALLET_CALL);

        deliverytime = data.get(SessionManager.DELIVERY_TIME);
        deliverydate = data.get(SessionManager.DELIVERY_DATE);

        grand_total = data.get(SessionManager.GRAND_TOTAL);
        grand_total_order = data.get(SessionManager.GRAND_TOTAL_ORDER);
        fulladdress = data.get(SessionManager.LOCATION) + "#" + data.get(SessionManager.DELIVERY_ADDRESS);
        areacode = data.get(SessionManager.DELIVERY_PIN);
        dabbacount = data.get(SessionManager.DABBA_COUNT);
        redirect = data.get(SessionManager.REDIRECT);

        //Log.d(TAG, "paymentOnline: " + phone_no + " " + bill_name);
        //Log.d(TAG, "onCreate: " + payableAmount);
        //Log.d(TAG, "onCreate: " + redirect);
        userId = data.get(SessionManager.USER_ID);
        String u_name = data.get(SessionManager.USER_NAME);
        String u_email = data.get(SessionManager.USER_EMAIL);

        grand_total = data.get(SessionManager.CART_VALUE);
        //pay_total = data.get(SessionManager.PAYABLE_AMOUNT);
        pay_total = getIntent().getStringExtra("pay_amount");


        if (sessionManager.getRedirect().equalsIgnoreCase("package")) {
            payable_amount = getIntent().getStringExtra("pay_amount");
            launchPayUMoney(getIntent().getStringExtra("pay_amount"), sessionManager.getRedirect());
        } else {
            launchPayUMoney(pay_total, sessionManager.getRedirect());
        }

        /*switch (redirect) {
            case "placeorder":
                launchPayUMoney(pay_total, "Alacarte");
                break;
            default:
                //paymentOnline(amount);
                amount = getIntent().getStringExtra("amount");
                launchPayUMoney(amount, "Wallet Payment");
                break;
        }*/

        copoun_code = getIntent().getStringExtra("copoun_code");
        coupon_category = getIntent().getStringExtra("coupon_category");
        coupon_id = getIntent().getStringExtra("coupon_id");
        copoun_type = getIntent().getStringExtra("copoun_type");
        discountAmount = getIntent().getStringExtra("discount_amount");
        deliveryCharge = getIntent().getStringExtra("delivery_charge");
        cancellationCharge = getIntent().getStringExtra("cancellation_charge");
        order_actual_amount = getIntent().getStringExtra("order_actual_amount");
        remark_type = getIntent().getStringExtra("remark_type");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Result Code is -1 send from Payumoney activity
        //Log.d("OnlinePaymentActivity!!", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && intent != null) {
            TransactionResponse transactionResponse = intent.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {

                    //TransactionId = transactionResponse.getTransactionDetails();
                    //Log.d("!!!!PAYU!!!--", merchantResponse);

                    //Success Transaction, Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();
                    //Log.d("!!!!PAYU!!!S", payuResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(payuResponse);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        TransactionId = jsonObject1.getString("txnid");
                        placeOrder();
                        /*if (jsonObject1.getString("productinfo").equalsIgnoreCase("Wallet Payment"))
                            ;//addMoneyRetrofit();
                        else *//*if (jsonObject1.getString("productinfo").equalsIgnoreCase("Alacarte"))*//*
                            placeOrder();*/
                       /* else
                            subsretrofit();*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Failure Transaction, Response from Payumoney
                    //String payuResponse = transactionResponse.getPayuResponse();
                    //Log.d("!!!!PAYU!!!F", payuResponse);
                    Toast toast = Toast.makeText(getApplicationContext(), "Payment Failed...", Toast.LENGTH_SHORT);
                    TextView view1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    view1.setTextColor(Color.WHITE);
                    view1.setTypeface(Typeface.createFromAsset(getAssets(), "ProximaNovaLight.otf"));
                    toast.show();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }  /*else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            }*/ else {
                Log.d(TAG, "Both objects are null!");
            }
        } else {
            //Failure Transaction, Response from Payumoney
            //String payuResponse = transactionResponse.getPayuResponse();
            //Log.d("!!!!PAYU!!!F", payuResponse);
            Toast toast = Toast.makeText(getApplicationContext(), "Payment was cancelled...", Toast.LENGTH_SHORT);
            TextView view1 = (TextView) toast.getView().findViewById(android.R.id.message);
            view1.setTextColor(Color.WHITE);
            view1.setTypeface(Typeface.createFromAsset(getAssets(), "ProximaNovaLight.otf"));
            toast.show();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
        }
    }

    @SuppressLint("CheckResult")
    public void placeOrder() {

        if (sessionManager.getRedirect().equalsIgnoreCase("package")) {

            String pickupAdd = FoodnFine.getAppSharedPreference().getPickupAdd();
            String deliveryAdd = FoodnFine.getAppSharedPreference().getDeliveryAdd();
            String package_id = FoodnFine.getAppSharedPreference().getPackageId();
            String distance = FoodnFine.getAppSharedPreference().getDistance();
            userId = FoodnFine.getAppSharedPreference().getUserId();
            String remark = FoodnFine.getAppSharedPreference().getRemarkse();
            String orderType = FoodnFine.getAppSharedPreference().getOrderType();

            PickupData data = new PickupData();
            data.setPickupAdd(pickupAdd);
            data.setDeliveryAdd(deliveryAdd);
            data.setPackageId(package_id);
            data.setCharges(payable_amount);
            data.setDistance(distance);
            data.setPayId(TransactionId);
            data.setPayType(pay_type);
            data.setPayStat(pay_stat);
            data.setUserId(userId);
            data.setOrderType(orderType);
            data.setRemark(remark);
            data.setEstimateValue(FoodnFine.getAppSharedPreference().getEstimateValue());

            PickupRequest request = new PickupRequest();
            request.setPickupAdd(data.getPickupAdd());
            request.setDelivarAdd(data.getDeliveryAdd());
            request.setPackageId(data.getPackageId());
            request.setDistance(data.getDistance());
            request.setCharges(data.getCharges());
            request.setPayId(data.getPayId());
            request.setPayStat(data.getPayStat());
            request.setUserId(data.getUserId());
            request.setPayType(data.getPayType());
            request.setRemark(data.getRemark());
            request.setOrderType(data.getOrderType());
            request.setEstimate_value(data.getEstimateValue());
            makeSendPackageRequest(request);

        } else {

            loadingDialog = new LoadingDialog(this);
            loadingDialog.showDialog();

            List<CartDatum> cartDatumList = FoodnFine.getAppSharedPreference().getArrayList();
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
                    reqObj2.put("cat_id", c.getCatId());
                    reqObj2.put("pid", c.getProductId());
                    reqObj2.put("rest_grocery", c.getRestGrocery());
                    reqArr.put(reqObj2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            orderDetails = reqArr;
            String parent_id = cartDatumList.get(0).getCartId();
            PlaceOrderRequest request = getPlaceOrderRequest();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WebConstants.DOMAIN_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call call = api.placeOrderNew(request.getOrderDetails(), request.getUserId(),
                    request.getRemark(), request.getTotalPrice(), "Online", request.getPayId(), request.getPayStatus(),
                    request.getDelivarAdd(), request.getOrderType(), sessionManager.getShopId(), TransactionId, "INR",
                    parent_id, sessionManager.getShopId(), cancellationCharge, copoun_code, copoun_type, coupon_category, coupon_id,
                    deliveryCharge, discountAmount, order_actual_amount, remark_type,
                    FoodnFine.getAppSharedPreference().getFixedCost(), order_actual_amount);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        loadingDialog.hideDialog();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            Toast.makeText(OnlinePaymentActivity.this, "Order submitted Successfully", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                            startActivity(new Intent(OnlinePaymentActivity.this, MyOrdersActivity.class));
                            finishAffinity();
                        } else
                            Snackbar.make(mainlayout, jsonObject.getString("msg"), Snackbar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        loadingDialog.hideDialog();
                        Snackbar.make(mainlayout, ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(mainlayout, NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }


    public ApiInterface getAPIInterface() {
        ApiInterface apiInterface = null;
        if (apiInterface == null) {
            apiInterface = NetworkHelper.getClient().create(ApiInterface.class);
        }
        return apiInterface;
    }

    @SuppressLint("CheckResult")
    private void makeSendPackageRequest(PickupRequest request) {

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog();

        Observable<RestResponsePickup> userResponseObservable = getAPIInterface().pickupRequest(request.getUserId(),
                request.getPickupAdd(), request.getDelivarAdd(), request.getPackageId(), request.getDistance(),
                request.getCharges(), request.getPayStat(), request.getPayType(), request.getPayId(),
                request.getRemark(), "sendPackage", request.getEstimate_value(), TransactionId, "INR",
                FoodnFine.getAppSharedPreference().getLatitude(), FoodnFine.getAppSharedPreference().getLongitude());

        //Log.d("MYREQUEST", request.getEstimate_value() + " " + request.getCharges()+ " "+ request.getDistance());

        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS) && restResponse.getResponse().equals(WEB_SUCCESS)) {
                        loadingDialog.hideDialog();

                        Toast.makeText(OnlinePaymentActivity.this, "Payment Successful!!!", Toast.LENGTH_SHORT).show();

                        // need to call a new api with restResponse.getOrder_no()
                        makeAssignOrderRequest(restResponse.getOrder_number());

                    } else {
                        loadingDialog.hideDialog();
                        Snackbar.make(mainlayout, ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                    }
                }, e -> {
                    loadingDialog.hideDialog();
                    Snackbar.make(mainlayout, NETWORK_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
                });
    }

    @SuppressLint("CheckResult")
    private void makeAssignOrderRequest(String order_number) {

        Observable<RestResponseAssignOrder> userResponseObservable = getAPIInterface().delivery_boy_assign_order(order_number);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /*finishAffinity();
                        startActivity(new Intent(OnlinePaymentActivity.this, DasboardActivity.class));*/

                        Intent intent = new Intent(this, MyOrdersActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText(OnlinePaymentActivity.this, restResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OnlinePaymentActivity.this, DasboardActivity.class));
                        finishAffinity();
                    }
                }, e -> {
                    Toast.makeText(OnlinePaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OnlinePaymentActivity.this, DasboardActivity.class));
                    finishAffinity();
                });
    }

    private PlaceOrderRequest getPlaceOrderRequest() {

        String deliveryAdd, userId;
        String PaymentId = TransactionId;
        String pay_type = "Online";
        String pay_stat = "Paid";
        String remark;
        String orderType;
        String groceryRestaurantId;

        deliveryAdd = FoodnFine.getAppSharedPreference().getDeliveryAdd();
        grand_total_order = FoodnFine.getAppSharedPreference().getCharges();
        userId = FoodnFine.getAppSharedPreference().getUserId();
        remark = FoodnFine.getAppSharedPreference().getRemarkse();
        orderType = FoodnFine.getAppSharedPreference().getOrderType();
        groceryRestaurantId = FoodnFine.getAppSharedPreference().getSelectedRestGroceryId();

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();

        placeOrderRequest.setUserId(userId);
        placeOrderRequest.setPayMode(pay_type);
        placeOrderRequest.setPayId(PaymentId);
        placeOrderRequest.setPayStatus(pay_stat);
        //placeOrderRequest.setTotalPrice(grand_total_order);
        placeOrderRequest.setTotalPrice(pay_total);
        placeOrderRequest.setDelivarAdd(deliveryAdd);
        placeOrderRequest.setRemark(remark);
        placeOrderRequest.setOrderType(orderType);
        placeOrderRequest.setOrderDetails(orderDetails);
        placeOrderRequest.setRestGrocery(groceryRestaurantId);
        return placeOrderRequest;
    }

    private void launchPayUMoney(String p_amount, String type) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Done");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("PayUmoney Payment");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        userId = FoodnFine.getAppSharedPreference().getUserId();
        String txnId = System.currentTimeMillis() + "";
        String phone = FoodnFine.getAppSharedPreference().getUserMobile();
        String productName = type;
        String firstName = sessionManager.getKeyName().isEmpty() ? ("Not Found"): sessionManager.getKeyName();
        String email = sessionManager.getKeyEmail().isEmpty() ? ("testmail" + userId + "@gmail.com") : sessionManager.getKeyEmail();
        String udf1 = FoodnFine.getAppSharedPreference().getDeliveryAdd();
        //String udf2 = postal_code;

        builder.setAmount(p_amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                .setUdf1(udf1)
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(true)
                .setKey("lSjsLexZ")
                .setMerchantId("7249904");
        try {
            mPaymentParams = builder.build();
            generateHashFromServer(mPaymentParams);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {

        HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.PRODUCT_INFO, params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.FIRSTNAME, params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        //Log.d("!!!", postParams);

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }

    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    /**
     * This AsyncTask generates hash from server.
     */
    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(OnlinePaymentActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {

            String merchantHash = "";
            try {
                URL url = new URL(WebConstants.DOMAIN_NAME + "moneyhash.php");

                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                //System.out.println("Response " + responseStringBuffer.toString());
                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    /*
                     * This hash is mandatory and needs to be generated from merchant's server side
                     */
                    if ("payment_hash".equals(key)) {
                        merchantHash = response.getString(key);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return merchantHash;
        }

        @Override
        protected void onPostExecute(String merchantHash) {
            super.onPostExecute(merchantHash);

            progressDialog.dismiss();
            if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(OnlinePaymentActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, OnlinePaymentActivity.this, R.style.AppTheme_default, false);
            }
        }
    }
}

