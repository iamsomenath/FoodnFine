package snd.orgn.foodnfine.view_model.FragmentViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackFoodDetailsList;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.FoodRequest;
import snd.orgn.foodnfine.rest.response.FoodcategoryListResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;

public class FoodDetailsViewModel extends BaseViewModel {
    private ApiInterface apiInterface;
    private CallbackFoodDetailsList callback;


    public FoodDetailsViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackFoodDetailsList callback) {
        this.callback = callback;
    }


    public void getFoodList(UserData data) {

        makeRequest(data);
    }


    private void makeRequest(UserData data) {
        FoodRequest request = new FoodRequest();
        request.setRestId(data.getRest_id());
        request.setFoodCatId(data.getFood_catid());
        makeFoodListRequest(request);
    }


    private void makeFoodListRequest(FoodRequest request) {
        Observable<FoodcategoryListResponse> userResponseObservable = getAPIInterface().getFoodResquest(request.getRestId(), request.getFoodCatId());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        callback.onSuccess(restResponse.getRestFood());

                    } else {
                        callback.onErrorNodataFound(restResponse.getMsg());
                    }
                }, e -> {
                    // callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });

    }
}
