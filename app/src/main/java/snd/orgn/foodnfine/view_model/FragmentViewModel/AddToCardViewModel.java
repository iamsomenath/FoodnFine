package snd.orgn.foodnfine.view_model.FragmentViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAddtoCart;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.AddToCartRequest;
import snd.orgn.foodnfine.rest.request.UserRequest;
import snd.orgn.foodnfine.rest.response.AddToCartRestResponse;
import snd.orgn.foodnfine.rest.response.RestResponseCart;

import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_ADDTOCART;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_CART_DETAILS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class AddToCardViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackAddtoCart callback;


    public AddToCardViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallbackAddTocart(CallbackAddtoCart callback) {
        this.callback = callback;
    }

    public void addToCart(UserData userData) {

        makeRequest(REST_REQUEST_ADDTOCART, userData);
    }

    public void getCartDetails(UserData userData) {
        makeRequest(REST_REQUEST_CART_DETAILS, userData);
    }

    private void makeRequest(String requestType, UserData userData) {

        switch (requestType) {
            case REST_REQUEST_ADDTOCART:
                AddToCartRequest request = new AddToCartRequest();
                request.setUserId(userData.getUser_id());
                request.setPid(userData.getpId());
                request.setQty(userData.getQuantity());
                request.setDevKey(userData.getDev_key());
                request.setOrderType(userData.getOrderType());
                request.setPrice(userData.getPrice());
                request.setRestGrocery(userData.getRest_id());
                makeAddToCartRequest(request);
                break;

            case REST_REQUEST_CART_DETAILS:
                UserRequest userRequest = new UserRequest();
                userRequest.setUserId(userData.getUser_id());
                userRequest.setOrderType(userData.getOrderType());
                makeCartDetailsRequest(userRequest);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void makeAddToCartRequest(AddToCartRequest request) {
        Observable<AddToCartRestResponse> userResponseObservable = getAPIInterface().add_to_cart(request.getUserId(),
                request.getPid(), request.getQty(), request.getDevKey(), request.getOrderType(),request.getPrice(),
                request.getRestGrocery());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getResponsesss().equals(1)) {

                        callback.onSuccess();
                    } else {
                        callback.onError(restResponse.getSuccess());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }


    @SuppressLint("CheckResult")
    private void makeCartDetailsRequest(UserRequest request) {
        Observable<RestResponseCart> userResponseObservable = apiInterface.getCartDetails(request.getUserId(),
                request.getOrderType());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(1)) {
                        callback.onSuccessCartDetails(restResponse);

                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

}
