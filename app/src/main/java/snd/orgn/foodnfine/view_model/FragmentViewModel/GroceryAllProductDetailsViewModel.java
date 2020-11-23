package snd.orgn.foodnfine.view_model.FragmentViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAllProductDetailsList;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.FoodRequest;
import snd.orgn.foodnfine.rest.response.GroceryAllProductListResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;

public class GroceryAllProductDetailsViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackAllProductDetailsList callback;

    public GroceryAllProductDetailsViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackAllProductDetailsList callback) {
        this.callback = callback;
    }


    public void getAllProductList(UserData data) {

        makeRequest(data);
    }


    private void makeRequest(UserData data) {
        FoodRequest request = new FoodRequest();
        request.setCategoryId(data.getCatId());
        makeAllProductListRequest(request);
    }


    @SuppressLint("CheckResult")
    private void makeAllProductListRequest(FoodRequest request) {
        Observable<GroceryAllProductListResponse> userResponseObservable = apiInterface.getAllProductListResquest(request.getCategoryId());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess(restResponse.getAllProduct());

                    } else {
                        callback.onErrorNoDataFound(restResponse.getMsg());
                    }
                }, e -> {
                     //callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }
}