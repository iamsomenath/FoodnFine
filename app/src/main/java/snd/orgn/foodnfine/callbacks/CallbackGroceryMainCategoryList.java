package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllGrocerycategory;

public interface CallbackGroceryMainCategoryList {
    void onSuccess(List<AllGrocerycategory> foodCategoryList);
    void onError(String message);
    void onNetworkError();
}
