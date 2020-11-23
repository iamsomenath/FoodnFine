package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackAddAddress;
import snd.orgn.foodnfine.callbacks.CallbackAddressDetailsFromId;
import snd.orgn.foodnfine.callbacks.CallbackGetAddress;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.room.database.AppDatabase;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.model.user_data.UserDataAddAddress;
import snd.orgn.foodnfine.model.utility.SaveAddressDetails;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.RestResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_GET_ADDRESS_LIST;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_SAVE_ADDRESS;
import static snd.orgn.foodnfine.constant.AppConstants.REST_REQUEST_UPDATE_ADDRESS;
import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class AddAdressViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackAddAddress callbackAddAddress;
    private CallbackGetAddress callbackGetAddress;
    private CallbackAddressDetailsFromId callbackAddressDetailsFromId;
    private AppDatabase appDatabase;
    private List<AddressDetails> addressLists;
    private int totalDataCount_AddressDetail = 0;
    private int totalDataSavedCount_addressDetail = 0;
    private Boolean addressDetailListFetched = false;
    private AddressDetails addressDetails;


    public AddAdressViewModel() {
        apiInterface = getAPIInterface();
        appDatabase = DeliveryEverything.getAppDatabase();
        addressLists = new ArrayList<>();
        addressDetails = new AddressDetails();

    }

    public void setCallbackAddAddress(CallbackAddAddress callback) {
        this.callbackAddAddress = callback;
    }

    public void setCallbackGetAddress(CallbackGetAddress callback) {
        this.callbackGetAddress = callback;
    }

    public void setCallbackgetAddressDetails(CallbackAddressDetailsFromId callback) {
        this.callbackAddressDetailsFromId = callback;
    }


    public void saveAddress(UserDataAddAddress userData) {
        makeRequest(REST_REQUEST_SAVE_ADDRESS, userData);
    }


    public void updateAddress(UserDataAddAddress userData) {
        makeRequest(REST_REQUEST_UPDATE_ADDRESS, userData);
    }


    public void getAddressDetails( String addressId) {
        getAddressDetailsFromDatabase(addressId);

    }

    public void getaddressList(UserDataAddAddress userData) {
        makeRequest(REST_REQUEST_GET_ADDRESS_LIST, userData);
    }

    private void makeRequest(String requestType, UserDataAddAddress userData) {

        switch (requestType) {
            case REST_REQUEST_SAVE_ADDRESS:
                SaveAddressDetails request = new SaveAddressDetails();
                request.setUserId(userData.getUserId());
                request.setLocation(userData.getLocation());
                request.setHouse(userData.getHouse());
                request.setBuilding(userData.getBuilding());
                request.setLandmark(userData.getLandmark());
                request.setInstruction(userData.getInstruction());
                request.setContactNumber(userData.getContactNumber());
                request.setContactPerson(userData.getContactPerson());
                request.setLocationType(userData.getLocationType());
                request.setOtherDescription(userData.getOtherDescription());

                makeApiCallSaveAddressRequest(request);
                break;
            case REST_REQUEST_UPDATE_ADDRESS:
                SaveAddressDetails updateRequest = new SaveAddressDetails();
                updateRequest.setUserAddId(userData.getUserAddressId());
                updateRequest.setUserId(userData.getUserId());
                updateRequest.setLocation(userData.getLocation());
                updateRequest.setHouse(userData.getHouse());
                updateRequest.setBuilding(userData.getBuilding());
                updateRequest.setLandmark(userData.getLandmark());
                updateRequest.setInstruction(userData.getInstruction());
                updateRequest.setContactNumber(userData.getContactNumber());
                updateRequest.setContactPerson(userData.getContactPerson());
                updateRequest.setLocationType(userData.getLocationType());
                updateRequest.setOtherDescription(userData.getOtherDescription());

                makeApiCallUpdateAddressRequest(updateRequest);
                break;
            case REST_REQUEST_GET_ADDRESS_LIST:
                SaveAddressDetails user = new SaveAddressDetails();
                user.setUserId(userData.getUserId());
                apiCallGetAddressList(user);
            break;
        }
    }


    @SuppressLint("CheckResult")
    private void makeApiCallSaveAddressRequest(SaveAddressDetails request) {
        Observable<RestResponse> userResponseObservable = apiInterface.addAddressLocation(request.getUserId(),
                request.getLocation(), request.getHouse(), request.getBuilding(), request.getLandmark(),
                request.getInstruction(), request.getContactPerson(), request.getContactNumber(), request.getLocationType(),
                request.getOtherDescription());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {

                        callbackAddAddress.onSuccess();

                    } else {
                        callbackAddAddress.onError(restResponse.getStat());
                    }
                }, e -> {
                    callbackAddAddress.onNetworkError(NETWORK_ERROR_MESSAGE);
                });

    }

    @SuppressLint("CheckResult")
    private void makeApiCallUpdateAddressRequest(SaveAddressDetails request) {
        Observable<RestResponse> userResponseObservable = apiInterface.updateAddressLocation(request.getUserAddId(), request.getUserId(), request.getLocation(), request.getHouse(), request.getBuilding(), request.getLandmark(), request.getInstruction(), request.getContactPerson(), request.getContactNumber(), request.getLocationType(), request.getOtherDescription());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {

                        callbackAddAddress.onSuccess();

                    } else {
                        callbackAddAddress.onError(restResponse.getStat());
                    }
                }, e -> {
                    callbackAddAddress.onNetworkError(NETWORK_ERROR_MESSAGE);
                });

    }

    private void apiCallGetAddressList(SaveAddressDetails request) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<List<AddressDetails>> call = service.getUserAddressList(request.getUserId());
        call.enqueue(new Callback<List<AddressDetails>>() {
            @Override
            public void onResponse(Call<List<AddressDetails>> call, retrofit2.Response<List<AddressDetails>> response) {
                if (response.body() != null) {
                    addressLists.clear();
                    addressLists.addAll(response.body());
                    callbackGetAddress.onSuccessGetAddress(addressLists);
                }
            }

            @Override
            public void onFailure(Call<List<AddressDetails>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callbackGetAddress.onErrorGetAddress(t.getMessage());
               // loadingDialog.hideDialog();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getAddressDetailsFromDatabase(String addressId) {
        try {
            Observable.create(s -> { // create observable
                addressDetails = appDatabase.getAddressDetailsListDao().getUserAddressDetails(addressId);
                s.onNext(Boolean.TRUE);
            }).subscribeOn(Schedulers.single()) //mention background thread
                    .observeOn(AndroidSchedulers.mainThread())// mention foreground/ui thread where you need the output
                    .subscribe(x -> {
                        if (x == Boolean.TRUE) {
                            callbackAddressDetailsFromId.onSuccessAddressDetails(addressDetails);

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
