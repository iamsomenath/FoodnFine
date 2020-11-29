package snd.orgn.foodnfine.view_model.FragmentViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAllDeleteCartItem;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.RestRespnseDeleteCart;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class DeleteAllCartItemViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackAllDeleteCartItem callback;

    public DeleteAllCartItemViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackAllDeleteCartItem callback) {
        this.callback = callback;
    }

    public void deleteAllCartItem() {

        makeRequest();
    }

    private void makeRequest() {
        String request = FoodnFine.getAppSharedPreference().getUserId();
        makeDeleteAllcartItemRequest(request);
    }

    @SuppressLint("CheckResult")
    private void makeDeleteAllcartItemRequest(String userId) {
        Observable<RestRespnseDeleteCart> userResponseObservable = apiInterface.deleteAllCartDetails(userId);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess();
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }
}

