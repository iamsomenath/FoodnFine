package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.rest.response.AllGrocery;

public interface CallbackAllGroceryList {
    void onSuccess(List<AllGrocery> getRestaurant);
    void onError(String message);
    void onNetworkError(String message);
}
