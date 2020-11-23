package snd.orgn.foodnfine.callbacks;

import java.util.ArrayList;

import snd.orgn.foodnfine.model.SearchResponseListPojo;
import snd.orgn.foodnfine.rest.response.RestResponseCart;

public interface CallbackSearchActivity {
    void onSuccess(ArrayList<SearchResponseListPojo> searchList);
    void onError(String message);
    void onNetworkError(String message);

    void onCartDetailsSuccess(RestResponseCart response);
    void onCartDetailsError(String message);
    void onCartDetailsNetworkError(String message);
}
