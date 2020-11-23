package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.callbacks.CallbackCancelOrder;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackOrderListActivity;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.OrderDetailsResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.ERROR_MESSAGE;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class OrderListActivityViewModel extends BaseViewModel {
    private CallbackOrderListActivity callback;
    private CallbackCancelOrder callbackCancelOrder;
    ApiInterface apiInterface;

    public OrderListActivityViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackOrderListActivity callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackCancelOrder callbackCancelOrder) {
        this.callbackCancelOrder = callbackCancelOrder;
    }

    public void getOrderList() {
        makeRequest();
    }

    private void makeRequest() {
        apiCallOrderList(DeliveryEverything.getAppSharedPreference().getUserId());
    }

    public void cancelOrder(String userId, String orderId) {
        apiCancelOrder(userId, orderId);
    }

    @SuppressLint("CheckResult")
    private void apiCallOrderList(String userId) {
        Observable<OrderDetailsResponse> userResponseObservable = getAPIInterface().customerOrderList(userId);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccess(restResponse.getOrders());
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

    @SuppressLint("CheckResult")
    private void apiCancelOrder(String userId, String orderId) {

        /*Observable<OrderDetailsResponse> userResponseObservable = getAPIInterface().cancelOrder(userId, cancelOrder);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccess(restResponse.getOrders());
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<ResponseBody> call = service.cancelOrder(userId, orderId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("TESTING", jsonObject.toString());
                        if (jsonObject.getInt("status") == 1) {
                            callbackCancelOrder.onSuccessCancel(jsonObject.getString("msg"));
                        } else {
                            callbackCancelOrder.onErrorCancel(jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        //e.printStackTrace();
                        Log.d("TESTING", Objects.requireNonNull(e.getMessage()));
                        callbackCancelOrder.onErrorCancel(ERROR_MESSAGE + e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callbackCancelOrder.onNetworkErrorCancel(NETWORK_ERROR_MESSAGE);
            }
        });
    }

}
