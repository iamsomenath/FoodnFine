package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.rest.response.AllRestaurant;

public interface CallbackAllRestaurantList {
    void onSuccess(List<AllRestaurant> getRestaurant);
    void onError(String message);
    void onNetworkError(String message);

}
