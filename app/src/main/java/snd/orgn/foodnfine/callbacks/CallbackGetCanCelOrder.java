package snd.orgn.foodnfine.callbacks;

import org.json.JSONObject;

public interface CallbackGetCanCelOrder {
    void onSuccessGetCancelOrder(JSONObject response);
    void onFailureGetCancelOrder(String response);
    void onNetworkErrorGetCancelOrder(String message);
}
