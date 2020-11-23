package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackUpdateProfile;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.request.UserRequest;
import snd.orgn.foodnfine.rest.response.RestResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.ERROR_MESSAGE;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class UpdateProfileViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackUpdateProfile callback;

    public UpdateProfileViewModel()
    {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackUpdateProfile callback) {
        this.callback = callback;
    }

    public void updateProfile(UserData userData) {
        makeRequest(userData);
    }

    private void makeRequest(UserData userData) {
        UserRequest request = new UserRequest();
        request.setUserName(userData.getUser_nm());
        request.setUserEmail(userData.getUser_eml());
        request.setUserId(userData.getUser_id());
        makeUpadteProfileRequest(request);
    }

    @SuppressLint("CheckResult")
    private void makeUpadteProfileRequest(UserRequest request) {
        Observable<RestResponse> userResponseObservable = apiInterface.updateProfile(request.getUserName(),
                request.getUserEmail(), request.getUserId());
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callback.onSuccess();
                    } else {
                        callback.onError(ERROR_MESSAGE);
                    }
                }, e -> callback.onNetworkError(NETWORK_ERROR_MESSAGE+ e.getMessage()));
    }
}

