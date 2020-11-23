package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.callbacks.CallbackAssignOrder;
import snd.orgn.foodnfine.rest.response.RestResponseAssignOrder;
import snd.orgn.foodnfine.rest.response.RestResponsePickup;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.callbacks.CallbackSendPackage;
import snd.orgn.foodnfine.model.utility.PickupData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.PickupRequest;
import snd.orgn.foodnfine.rest.request.PlaceOrderRequest;
import snd.orgn.foodnfine.rest.response.KmChargesRestResponse;
import snd.orgn.foodnfine.rest.response.RestResponsePlaceOrder;

import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class SendPackageViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackGetChargesInKM callback;
    private CallbackSendPackage callbackSendPackage;
    private CallbackAssignOrder callbackAssignOrder;

    public SendPackageViewModel() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackGetChargesInKM callback) {
        this.callback = callback;
    }

    public void setCallback2(CallbackAssignOrder callbackAssignOrder) {
        this.callbackAssignOrder = callbackAssignOrder;
    }

    public void setCallbackSendPackage(CallbackSendPackage callback) {
        this.callbackSendPackage = callback;
    }

    public void getChargeseInKm() {
        apiCall_getKmIncharges();
    }

    public void sendPackageRequest(PickupData data) {

        makeRequest(data);
    }

    public void placeOrderRequest(PlaceOrderRequest request) {

        makeRequestPlaceOrder(request);
    }

    public void makeOrderRequest(String orderNumber) {

        makeAssignOrderRequest(orderNumber);
    }

    private void makeRequestPlaceOrder(PlaceOrderRequest placeOrderRequest) {
        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setDelivarAdd(placeOrderRequest.getDelivarAdd());
        orderRequest.setUserId(placeOrderRequest.getUserId());
        orderRequest.setOrderDetails(placeOrderRequest.getOrderDetails());
        orderRequest.setOrderType(placeOrderRequest.getOrderType());
        orderRequest.setPayId(placeOrderRequest.getPayId());
        orderRequest.setPayStatus(placeOrderRequest.getPayStatus());
        orderRequest.setPayMode(placeOrderRequest.getPayMode());
        orderRequest.setRemark(placeOrderRequest.getRemark());
        orderRequest.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderRequest.setRestGrocery(placeOrderRequest.getRestGrocery());
        orderRequest.setRestGroceryid(placeOrderRequest.getRestGroceryid());
        // newly added
        orderRequest.setCancellation_charge(placeOrderRequest.getCancellation_charge());
        orderRequest.setCopoun_code(placeOrderRequest.getCopoun_code());
        orderRequest.setCopoun_type(placeOrderRequest.getCopoun_type());
        orderRequest.setCoupon_category(placeOrderRequest.getCoupon_category());
        orderRequest.setCoupon_id(placeOrderRequest.getCoupon_id());
        orderRequest.setDelivery_charge(placeOrderRequest.getDelivery_charge());
        orderRequest.setDiscount_amount(placeOrderRequest.getDiscount_amount());
        orderRequest.setOrder_actual_amount(placeOrderRequest.getOrder_actual_amount());
        orderRequest.setRemark_type(placeOrderRequest.getRemark_type());
        makeOrderPlaceRequest(orderRequest);
    }

    private void makeRequest(PickupData data) {

        PickupRequest request = new PickupRequest();
        request.setPickupAdd(data.getPickupAdd());
        request.setDelivarAdd(data.getDeliveryAdd());
        request.setPackageId(data.getPackageId());
        request.setDistance(data.getDistance());
        request.setCharges(data.getCharges());
        request.setPayId(data.getPayId());
        request.setPayStat(data.getPayStat());
        request.setUserId(data.getUserId());
        request.setPayType(data.getPayType());
        request.setRemark(data.getRemark());
        request.setOrderType(data.getOrderType());
        request.setEstimate_value(data.getEstimateValue());
        makeSendPackageRequest(request);
    }

    @SuppressLint("CheckResult")
    private void apiCall_getKmIncharges() {
        Observable<KmChargesRestResponse> userResponseObservable = apiInterface.getKmcharges();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callback.onSuccessGetCharges(restResponse.getFixed_cost(), restResponse.getPer_kilometer());
                    } else {
                        callback.onfailure();
                    }
                }, e -> callback.onfailure());
    }

    @SuppressLint("CheckResult")
    private void makeSendPackageRequest(PickupRequest request) {
        //Log.d("MYREQUEST", request.getEstimate_value() + " " + request.getCharges()+ " "+ request.getDistance());
        Observable<RestResponsePickup> userResponseObservable = apiInterface.pickupRequest(request.getUserId(),
                request.getPickupAdd(), request.getDelivarAdd(), request.getPackageId(), request.getDistance(),
                request.getCharges(), request.getPayStat(), request.getPayType(), request.getPayId(),
                request.getRemark(), "sendPackage", request.getEstimate_value(), "", "INR",
                DeliveryEverything.getAppSharedPreference().getLatitude(), DeliveryEverything.getAppSharedPreference().getLongitude());

        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS) && restResponse.getResponse().equals(WEB_SUCCESS)) {
                        /// DeliveryEverything.getAppSharedPreference().saveUserId(restResponse.getUserId());
                        //Log.d("TEST!!", restResponse.getOrder_number());
                        callbackSendPackage.onSuccess(restResponse.getOrder_number());
                    } else {
                        callbackSendPackage.OnError(restResponse.getMessage());
                    }
                }, e -> {
                    callbackSendPackage.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

    @SuppressLint("CheckResult")
    private void makeOrderPlaceRequest(PlaceOrderRequest request) {
        //Log.d("TESTTING", request.getDelivery_charge() + " " + request.getCancellation_charge() + " " + request.getDiscount_amount());
        //Log.d("TESTTING", request.getCoupon_category() + " " + request.getCoupon_id() + " " + request.getCopoun_type() + " " + request.getCopoun_code());
        Observable<RestResponsePlaceOrder> userResponseObservable = apiInterface.placeOrder(request.getOrderDetails(),
                request.getUserId(), request.getRemark(), request.getTotalPrice(), request.getPayMode(), request.getPayId(),
                request.getPayStatus(), request.getDelivarAdd(), request.getOrderType(), request.getRestGroceryid(),
                "", "INR", request.getCancellation_charge(),
                request.getCopoun_code(), request.getCopoun_type(), request.getCoupon_category(), request.getCoupon_id(),
                request.getDelivery_charge(), request.getDiscount_amount(), request.getOrder_actual_amount(), request.getRemark_type(),
                DeliveryEverything.getAppSharedPreference().getFixedCost(), request.getOrder_actual_amount());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        Log.d("TEST", restResponse.getOrderNo());
                        callbackSendPackage.onSuccess(restResponse.getOrderNo());

                    } else {
                        callbackSendPackage.OnError(restResponse.getMsg());
                    }
                }, e -> {
                    callbackSendPackage.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }

    @SuppressLint("CheckResult")
    private void makeAssignOrderRequest(String order_number) {

        Observable<RestResponseAssignOrder> userResponseObservable = apiInterface.delivery_boy_assign_order(order_number);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callbackAssignOrder.onSuccessAssignOrder();
                    } else {
                        callbackAssignOrder.onErrorAssignOrder(restResponse.getMsg());
                    }
                }, e -> {
                    callbackAssignOrder.onErrorAssignOrder(NETWORK_ERROR_MESSAGE);
                });
    }
}