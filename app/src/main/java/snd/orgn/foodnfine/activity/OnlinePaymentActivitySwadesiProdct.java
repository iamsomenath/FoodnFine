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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.PlaceOrderRequest;
import snd.orgn.foodnfine.util.LoadingDialog;

import static snd.orgn.foodnfine.constant.ErrorMessageConstant.ERROR_MESSAGE;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

// currently not required
public class OnlinePaymentActivitySwadesiProdct extends AppCompatActivity {

    private static final String TAG = OnlinePaymentActivitySwadesiProdct.class.getSimpleName();
    LoadingDialog loadingDialog;

    private String TransactionId, grand_total_order;

    String product_name, qty, total_price, price, product_desc, weight, unit, cat_id, rest_grocery;
    String userId, payId, payStatus, delivarAdd, remark, orderType, paymode, product_id;

    String payAmount;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    private LinearLayout mainlayout;
    JSONArray orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_online_payment);
        //Fabric.with(this, new Crashlytics());

        mainlayout = findViewById(R.id.mainlayout);

        product_name = getIntent().getStringExtra("product_name");
        product_id = getIntent().getStringExtra("product_id");
        qty = getIntent().getStringExtra("qty");
        total_price = getIntent().getStringExtra("totalPrice");
        payAmount = getIntent().getStringExtra("PAY_AMOUNT");
        price = getIntent().getStringExtra("price");
        product_desc = getIntent().getStringExtra("product_desc");
        weight = getIntent().getStringExtra("weight");
        unit = getIntent().getStringExtra("unit");
        cat_id = getIntent().getStringExtra("cat_id");
        rest_grocery = "3";

        userId  = getIntent().getStringExtra("userId");
        paymode = "Online";
        payId = getIntent().getStringExtra("payId");
        payStatus = getIntent().getStringExtra("payStatus");
        delivarAdd = getIntent().getStringExtra("delivarAdd");
        remark = getIntent().getStringExtra("remark");
        orderType = getIntent().getStringExtra("orderType");

        launchPayUMoney(getIntent().getStringExtra("PAY_AMOUNT"), "SWADESI");
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

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog();

        JSONArray reqArr = new JSONArray();
        try {
            JSONObject reqObj2 = new JSONObject();
            reqObj2.put("product_name", product_name);
            reqObj2.put("product_id", product_id);
            reqObj2.put("qty", qty);
            //reqObj2.put("total_price", total_price);
            reqObj2.put("total_price", String.valueOf(Integer.parseInt(qty) * Double.parseDouble(price)));
            //reqObj2.put("price", price);
            reqObj2.put("price", String.valueOf(Double.parseDouble(price)));
            reqObj2.put("product_desc", product_desc);
            reqObj2.put("weight", weight);
            reqObj2.put("unit", unit);
            reqObj2.put("cat_id", cat_id);
            reqObj2.put("pid", cat_id);
            reqObj2.put("rest_grocery", rest_grocery);
            reqArr.put(reqObj2);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        orderDetails = reqArr;
        PlaceOrderRequest request = new PlaceOrderRequest();

        request.setUserId(userId);
        request.setPayMode("Online");
        request.setPayId(payId);
        request.setPayStatus(payStatus);
        request.setTotalPrice(payAmount);
        //request.setTotalPrice(String.valueOf(Integer.parseInt(qty) * Double.parseDouble(price)));
        request.setDelivarAdd(delivarAdd);
        request.setRemark(remark);
        request.setOrderType(orderType);
        request.setOrderDetails(orderDetails);
        request.setRestGrocery(rest_grocery);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call call = api.placeOrderNew(request.getOrderDetails(), request.getUserId(),
                request.getRemark(), request.getTotalPrice(), "Online", request.getPayId(), request.getPayStatus(),
                request.getDelivarAdd(), request.getOrderType(), new SessionManager(this).getShopId(), TransactionId,
                "INR", "0", new SessionManager(this).getShopId(),
                "", "", "", "", "", "",
                "", "", "", "", "");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        Toast.makeText(OnlinePaymentActivitySwadesiProdct.this, "Order submitted Successfully", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                        startActivity(new Intent(OnlinePaymentActivitySwadesiProdct.this, MyOrdersActivity.class));
                        finishAffinity();
                    } else
                        Snackbar.make(mainlayout, jsonObject.getString("msg"), Snackbar.LENGTH_LONG).show();

                } catch (Exception e) {
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

    private void launchPayUMoney(String p_amount, String type) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Done");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("PayUmoney Payment");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        userId = FoodnFine.getAppSharedPreference().getUserId();
        SessionManager sessionManager =  new SessionManager(this);
        String txnId = System.currentTimeMillis() + "";
        String phone = FoodnFine.getAppSharedPreference().getUserMobile();
        String productName = getIntent().getStringExtra("product_name");
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
                .setIsDebug(false)
                .setKey("PjUlu58x")
                .setMerchantId("6536779");

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
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        //postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        Log.d("!!!", postParams);

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
            progressDialog = new ProgressDialog(OnlinePaymentActivitySwadesiProdct.this);
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

                System.out.println("Response " + responseStringBuffer.toString());
                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        /**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         */
                        case "payment_hash":
                            merchantHash = response.getString(key);
                            break;
                        default:
                            break;
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
                Toast.makeText(OnlinePaymentActivitySwadesiProdct.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, OnlinePaymentActivitySwadesiProdct.this, R.style.AppTheme_default, false);
            }
        }
    }
}
