package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAllGroceryList;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.GroceryRestResponse;
import snd.orgn.foodnfine.rest.response.KmChargesRestResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class GroceryListViewModel extends BaseViewModel {
    ApiInterface apiInterface;

    CallbackAllGroceryList callback;
    CallbackGetChargesInKM callbackGetChargesInKM;

    public GroceryListViewModel() {
        apiInterface = getAPIInterface();
        // newOrderListPojo = new ArrayList<>();
        // appDatabase = DeliveryEverything.getAppDatabase();
    }

    public void setCallback(CallbackAllGroceryList callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackGetChargesInKM callbackGetChargesInKM) {
        this.callbackGetChargesInKM = callbackGetChargesInKM;
    }

    public void getRestrurentList() {
        makeRequest();
    }

    public void getKMCharges(){
        apiCall_getKmIncharges();
    }

    private void makeRequest() {

        apiCallRestrurentList();
    }

    @SuppressLint("CheckResult")
    private void apiCallRestrurentList() {
        Observable<GroceryRestResponse> userResponseObservable = getAPIInterface().getAllGrocery();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccess(restResponse.getAllGrocery());
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

    @SuppressLint("CheckResult")
    private void apiCall_getKmIncharges() {
        Observable<KmChargesRestResponse> userResponseObservable = apiInterface.getKmcharges();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callbackGetChargesInKM.onSuccessGetCharges(restResponse.getFixed_cost(), restResponse.getPer_kilometer());
                    } else {
                        callbackGetChargesInKM.onfailure();
                    }
                }, e -> {
                    callbackGetChargesInKM.onfailure();
                });
    }
}
