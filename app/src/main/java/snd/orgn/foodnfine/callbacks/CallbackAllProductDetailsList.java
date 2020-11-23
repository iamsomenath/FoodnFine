package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.AllGroceryProduct;

public interface CallbackAllProductDetailsList {
    void onSuccess(List<AllGroceryProduct> allGroceryProductList);
    void onErrorNoDataFound(String message);
}
