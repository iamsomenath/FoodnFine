package snd.orgn.foodnfine.base;

import androidx.lifecycle.ViewModel;

import snd.orgn.foodnfine.helper.other.NetworkHelper;
import snd.orgn.foodnfine.rest.api.ApiInterface;

public class BaseViewModel extends ViewModel {

    private static ApiInterface apiInterface;

    public ApiInterface getAPIInterface() {
        if (apiInterface == null) {
            apiInterface = NetworkHelper.getClient().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
