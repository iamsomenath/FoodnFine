package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAllDeleteCartItem;
import snd.orgn.foodnfine.callbacks.CallbackGroceryMainCategoryList;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.GroceryMainCategoryListResponse;
import snd.orgn.foodnfine.rest.response.RestRespnseDeleteCart;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class GroceryMaincategoryViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackGroceryMainCategoryList callback;
    private CallbackAllDeleteCartItem callbackAllDeleteCartItem;

    public GroceryMaincategoryViewModel() {
        this.apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackGroceryMainCategoryList callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackAllDeleteCartItem callbackAllDeleteCartItem) {
        this.callbackAllDeleteCartItem = callbackAllDeleteCartItem;
    }

    public void getGroceyCatergoryList(){

        makeRequest();
    }

    public void deleteAllCartItem(String user_id) {

        makeDeleteAllcartItemRequest(user_id);
    }

    private void makeRequest() {

        makeGrocerycatgeoryListRequest();
    }

    @SuppressLint("CheckResult")
    private void makeGrocerycatgeoryListRequest() {
        Observable<GroceryMainCategoryListResponse> userResponseObservable = apiInterface.getGroceryCategoryResquest();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess(restResponse.getAllGrocerycategory());

                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                     callback.onNetworkError();
                });
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
                        callbackAllDeleteCartItem.onSuccess();
                    } else {
                        callbackAllDeleteCartItem.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callbackAllDeleteCartItem.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }
}
