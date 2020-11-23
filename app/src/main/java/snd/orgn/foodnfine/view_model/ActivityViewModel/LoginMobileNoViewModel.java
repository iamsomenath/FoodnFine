package snd.orgn.foodnfine.view_model.ActivityViewModel;


import android.annotation.SuppressLint;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackloginMobileNo;
import snd.orgn.foodnfine.constant.RetrofitInstance;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.UserRequest;

import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class LoginMobileNoViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackloginMobileNo callback;

    public LoginMobileNoViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackloginMobileNo callback) {
        this.callback = callback;
    }


    public void phoneNumbercheck(UserData userData) {
        makeRequest(userData);
    }

    private void makeRequest(UserData userData) {
        UserRequest request = new UserRequest();
        request.setDevKey(userData.getDev_key());
        request.setUserMobile(userData.getUserMobile());
        makePhoneNumberCheckRequest(request);
    }


    @SuppressLint("CheckResult")
    private void makePhoneNumberCheckRequest(UserRequest request) {

        /*Observable<RestResponse> userResponseObservable = apiInterface.phoneNumberCheck(request.getUserMobile(), request.getDevKey());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callback.onSuccess(restResponse.getOtp());
                    } else {
                        callback.onError(restResponse.getStat());
                    }
                }, e ->
                        callback.onNetworkError(NETWORK_ERROR_MESSAGE));*/


        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<ResponseBody> call = service.phoneNumberCheck(request.getUserMobile(), request.getDevKey());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getString("success").equalsIgnoreCase(WEBSERVICE_SUCCESS))
                        callback.onSuccess(jsonObject.getString("otp"), jsonObject.getString("otp"));
                    else
                        callback.onError(jsonObject.getString("stat"));

                } catch (Exception e) {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onNetworkError(NETWORK_ERROR_MESSAGE);
            }
        });
    }
}
