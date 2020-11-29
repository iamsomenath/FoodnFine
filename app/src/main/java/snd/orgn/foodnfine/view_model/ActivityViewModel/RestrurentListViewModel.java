package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAllRestaurantList;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.RestrurentRestResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class RestrurentListViewModel extends BaseViewModel {

    ApiInterface apiInterface;

    CallbackAllRestaurantList callback;


    public RestrurentListViewModel() {
        apiInterface = getAPIInterface();
        // newOrderListPojo = new ArrayList<>();
//            appDatabase = DeliveryEverything.getAppDatabase();

    }


    public void setCallback(CallbackAllRestaurantList callback) {
        this.callback = callback;

    }


    public void getRestrurentList() {
        makeRequest();
    }

    private void makeRequest() {

        apiCallRestrurentList();
    }


    @SuppressLint("CheckResult")
    private void apiCallRestrurentList() {
        Observable<RestrurentRestResponse> userResponseObservable = getAPIInterface().getAllRestaurant();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccess(restResponse.getAllRestaurant());
                    } else {
                        callback.onError(restResponse.getMsg());
                    }

                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }
}
