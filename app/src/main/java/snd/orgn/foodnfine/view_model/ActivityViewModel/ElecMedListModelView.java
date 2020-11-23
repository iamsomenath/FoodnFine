package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackElecMedList;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.ElecMedRestResponse;

import static snd.orgn.foodnfine.constant.AppConstants.WEB_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class ElecMedListModelView extends BaseViewModel {

    private ApiInterface apiInterface;
    private CallbackElecMedList callback;

    public ElecMedListModelView() {
        apiInterface = getAPIInterface();
    }

    public void setCallback(CallbackElecMedList callback) {
        this.callback = callback;
    }

    public void getLists(String type) {
        apiCallList(type);
    }

    @SuppressLint("CheckResult")
    private void apiCallList(String type) {
        Observable<ElecMedRestResponse> userResponseObservable = apiInterface.get_all_shop(type);
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getStatus().equals(WEB_SUCCESS)) {
                        callback.onSuccess(restResponse.getAll_shops());
                    } else {
                        callback.onError(restResponse.getMsg());
                    }
                }, e -> {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                });
    }
}
