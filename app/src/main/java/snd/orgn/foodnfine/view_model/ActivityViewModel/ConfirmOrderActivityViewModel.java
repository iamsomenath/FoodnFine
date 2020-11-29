package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.callbacks.CallbackApplyCouponActivity;
import snd.orgn.foodnfine.callbacks.CallbackGetCanCelOrder;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackConfirmOrderActivity;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.AddToCartRequest;
import snd.orgn.foodnfine.rest.request.UserRequest;
import snd.orgn.foodnfine.rest.response.RestRespnseDeleteCart;
import snd.orgn.foodnfine.rest.response.RestResponseCart;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_CART_DETAILS;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_DELETE_CART_ITEM;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_UPDATE_CART_ITEM;
import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.ERROR_MESSAGE;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class ConfirmOrderActivityViewModel extends BaseViewModel {
    private CallbackConfirmOrderActivity callback;
    private CallbackApplyCouponActivity callbackApplyCouponActivity;
    private ApiInterface apiInterface;

    public ConfirmOrderActivityViewModel() {
        this.apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackConfirmOrderActivity callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackApplyCouponActivity callbackApplyCouponActivity) {
        this.callbackApplyCouponActivity = callbackApplyCouponActivity;
    }

    public void updateCartItem(UserData userData) {

        makeRequest(REST_REQUEST_UPDATE_CART_ITEM, userData);
    }

    public void getCartDetails(UserData userData) {
        makeRequest(REST_REQUEST_CART_DETAILS, userData);
    }

    public void deleteCartItem(UserData userData) {
        makeRequest(REST_REQUEST_DELETE_CART_ITEM, userData);
    }

    public void applyCoupon(String coupon_code, String user_id, String coupon_category, String res_id_gro_id){
        makeApplyCouponRequest(coupon_code, user_id, coupon_category, res_id_gro_id);
    }

    private void makeRequest(String requestType, UserData userData) {

        switch (requestType) {
            case REST_REQUEST_UPDATE_CART_ITEM:
                AddToCartRequest request = new AddToCartRequest();
                request.setUserId(userData.getUser_id());
                request.setPid(userData.getCartId());
                request.setQty(userData.getQuantity());
                request.setPrice(userData.getPrice());
                makeUpdateCartItemRequest(request);
                break;

            case REST_REQUEST_CART_DETAILS:
                UserRequest userRequest = new UserRequest();
                userRequest.setUserId(userData.getUser_id());
                userRequest.setOrderType(userData.getOrderType());
                makeCartDetailsRequest(userRequest);
                break;

            case REST_REQUEST_DELETE_CART_ITEM:
                AddToCartRequest deleteRequest = new AddToCartRequest();
                deleteRequest.setUserId(userData.getUser_id());
                deleteRequest.setPid(userData.getCartId());
                makeDeleteCartItemRequest(deleteRequest);
                break;
        }
    }


    @SuppressLint("CheckResult")
    private void makeUpdateCartItemRequest(AddToCartRequest request) {
        //Log.d("TEST1", request.getUserId()+ " " + request.getPid() + " " + request.getQty() + " " + request.getPrice());
        Observable<RestRespnseDeleteCart> userResponseObservable = apiInterface.update_cart(request.getUserId(), request.getPid(), request.getQty(), request.getPrice());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccessUpadteCartItem();
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                    Log.d("ERROR!!!", e.getMessage());
                });
    }


    @SuppressLint("CheckResult")
    private void makeDeleteCartItemRequest(AddToCartRequest request) {
        Observable<RestRespnseDeleteCart> userResponseObservable = apiInterface.delete_cart(request.getUserId(), request.getPid());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {

                        callback.onSuccessUpadteCartItem();
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }


    @SuppressLint("CheckResult")
    private void makeCartDetailsRequest(UserRequest request) {
        Observable<RestResponseCart> userResponseObservable = apiInterface.getCartDetails(request.getUserId(), request.getOrderType());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(1)) {
                        callback.onSuccessCartDetails(restResponse);
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

    @SuppressLint("CheckResult")
    private void makeApplyCouponRequest(String coupon_code, String user_id, String coupon_category, String res_id_gro_id) {
        Log.d("TESTING", coupon_code + " " + user_id + " " + coupon_category + " " + res_id_gro_id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<ResponseBody> call = service.used_coupon(coupon_code, user_id, coupon_category, res_id_gro_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        //Log.d("TESTING", jsonObject.toString());
                        if(jsonObject.getInt("result") == 1){
                            callbackApplyCouponActivity.onSuccessCoupon(jsonObject.getJSONObject("coupon_detail"));
                        }else{
                            callbackApplyCouponActivity.onFailureCoupon(jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        //e.printStackTrace();
                        //Log.d("TESTING", e.getMessage());
                        callbackApplyCouponActivity.onNetworkErrorCoupon(ERROR_MESSAGE + e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callbackApplyCouponActivity.onNetworkErrorCoupon(NETWORK_ERROR_MESSAGE);
            }
        });
    }
}

