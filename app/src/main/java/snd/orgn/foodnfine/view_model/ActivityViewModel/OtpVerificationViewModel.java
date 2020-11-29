package snd.orgn.foodnfine.view_model.ActivityViewModel;


import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackOtpVerification;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.UserRequest;
import snd.orgn.foodnfine.rest.response.RestResponse;

import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_OTP_SUCESSFULL_LOGIN;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_RESENT_OTP;
import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class OtpVerificationViewModel extends BaseViewModel {
    private ApiInterface apiInterface;
    private CallbackOtpVerification callback;


    public OtpVerificationViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackOtpVerification callback) {
        this.callback = callback;
    }

    public void loginByOtp_user(UserData userData) {
        makeRequest(REST_REQUEST_OTP_SUCESSFULL_LOGIN,userData);

    }

    public void resentOtp(UserData userData) {
        makeRequest(REST_REQUEST_RESENT_OTP,userData);
    }


    private void makeRequest(String requestType,UserData userData) {

        switch (requestType){
            case REST_REQUEST_RESENT_OTP:
                UserRequest request = new UserRequest();
                request.setUserMobile(userData.getUserMobile());
                request.setDevKey(userData.getDev_key());
                apiCall_resentOtp(request);
                break;
            case REST_REQUEST_OTP_SUCESSFULL_LOGIN:
                UserRequest userrequest = new UserRequest();
                userrequest.setOtp(userData.getOtp());
                userrequest.setUserMobile(userData.getUserMobile());
                makeOptCheckRequest(userrequest);
                break;

        }
    }


    private void makeRequest(UserData userData) {
        UserRequest request = new UserRequest();
        request.setOtp(userData.getOtp());
        request.setUserMobile(userData.getUserMobile());
        makeOptCheckRequest(request);
    }


    @SuppressLint("CheckResult")
    private void makeOptCheckRequest(UserRequest request) {
        Observable<RestResponse> userResponseObservable = getAPIInterface().verifyOtp(request.getUserMobile(), request.getOtp());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        FoodnFine.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess();
                    } else {
                        callback.onError(restResponse.getStat());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });

    }


    @SuppressLint("CheckResult")
    private void apiCall_resentOtp(UserRequest request) {
        Observable<RestResponse> userResponseObservable = getAPIInterface().resendOtp(request.getUserMobile(),request.getDevKey());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                    callback.onSuccessByPhoneNo();
                    } else {
                        callback.onErrorResendOtp(restResponse.getStat());
                    }
                }, e -> {
                    callback.onNetworkErrorResendOtp(NETWORK_ERROR_MESSAGE);
                });

    }

}


