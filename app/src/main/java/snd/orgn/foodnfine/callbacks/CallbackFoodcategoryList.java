package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.FoodCategory;

public interface CallbackFoodcategoryList {
    void onSuccess(List<FoodCategory> foodCategoryList);
    void onError(String message);
}
