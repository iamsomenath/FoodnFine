package snd.orgn.foodnfine.view_model.ActivityViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackFoodcategoryList;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.FoodcategoryListResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;

public class FoodCategoryViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackFoodcategoryList callback;



    public FoodCategoryViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackFoodcategoryList callback) {
        this.callback = callback;
    }


    public void getFoodCatergoryList(String data){

        makeRequest(data);
    }




    private void makeRequest(String data) {

        makeFoodcatgeoryListRequest(data);
    }




    private void makeFoodcatgeoryListRequest(String restId) {
        Observable<FoodcategoryListResponse> userResponseObservable = getAPIInterface().getFoodCategoryResquest(restId);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess(restResponse.getFoodCategory());

                    } else {
                          callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    // callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });

    }

}
