package snd.orgn.foodnfine.callbacks;

import snd.orgn.foodnfine.rest.response.RestResponseCart;

public interface CallbackConfirmOrderActivity {
    void onSuccessUpadteCartItem();
    void onSuccessDeleteCartItem();
    void onSuccessCartDetails(RestResponseCart cartDetails);
    void onError(String message);
    void onNetworkError(String message);
}
