package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllElectMedPojo;

public interface CallbackElecMedList {
    void onSuccess(List<AllElectMedPojo> lists);
    void onError(String message);
    void onNetworkError(String message);
}
