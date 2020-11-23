package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackSaveAddressList;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.rest.api.ApiInterface;

public class SavedAddressViewModel extends BaseViewModel {

    private ApiInterface apiInterface;
    CallbackSaveAddressList callback;
    private List<AddressDetails> addressLists;

    public SavedAddressViewModel() {

        addressLists = new ArrayList<>();
    }


    public void setCallback(CallbackSaveAddressList callback) {
        this.callback = callback;
    }


    @SuppressLint("CheckResult")
    public void deleteAddressDetailsFromDatabase(String addressId) {

    }
}
