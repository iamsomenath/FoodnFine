package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackSubscription;
import snd.orgn.foodnfine.callbacks.CallbackSubscriptionRates;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.SubcriptionRequest;
import snd.orgn.foodnfine.rest.response.RestResponsePlaceOrder;
import snd.orgn.foodnfine.rest.response.SubscriptionResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class SubcriptionPlainViewModel  extends BaseViewModel {

    ApiInterface apiInterface;

    private CallbackSubscriptionRates callback;
    private CallbackSubscription callback2;

    public void setCallback(CallbackSubscriptionRates callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackSubscription callback2) {
        this.callback2 = callback2;
    }

    public SubcriptionPlainViewModel() {
        apiInterface = getAPIInterface();
    }

    public void getSubcriptionList() {
        makeRequest();
    }

    public void confirmSubcriptionPlan(SubcriptionRequest request) {
        apiCallSubscription(request);
    }

    private void makeRequest() {
        apiCallSubcriptionList();
    }

    @SuppressLint("CheckResult")
    private void apiCallSubcriptionList() {
        Observable<SubscriptionResponse> userResponseObservable = apiInterface.getSubcriptionPlain();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(1)) {
                        callback.onSuccess(restResponse.getAllSubscriptionCharges());
                    } else {
                        callback.onError(restResponse.getSuccess());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }


    @SuppressLint("CheckResult")
    private void apiCallSubscription(SubcriptionRequest request) {
        Observable<RestResponsePlaceOrder> userResponseObservable = apiInterface.confirmSubcriptionPlain(request.getUserId(),request.getSubscriptionPlanId(),request.getSubscriptionPlan(),request.getPayId(),request.getPayType());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        FoodnFine.getAppSharedPreference().setUserType(restResponse.getUser_type());
                        callback2.onSuccessSubcriptionPlan();
                    } else {
                        callback2.onError2(restResponse.getMsg());
                    }
                }, e -> {
                    callback2.onNetworkError2(NETWORK_ERROR_MESSAGE);
                });
    }
}