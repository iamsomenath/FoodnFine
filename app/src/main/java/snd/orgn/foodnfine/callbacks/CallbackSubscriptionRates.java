package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllSubscriptionCharge;

public interface CallbackSubscriptionRates {
    void onSuccess(List<AllSubscriptionCharge>allSubscriptionChargeList);
    void onError(String message);
    void onNetworkError(String message);
}
