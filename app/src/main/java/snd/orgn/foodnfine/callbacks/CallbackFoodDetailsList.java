package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.RestFood;

public interface CallbackFoodDetailsList {
    void onSuccess(List<RestFood> foodList);
    void onErrorNodataFound(String message);
}
