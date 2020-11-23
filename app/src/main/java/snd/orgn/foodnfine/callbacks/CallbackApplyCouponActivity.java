package snd.orgn.foodnfine.callbacks;

import org.json.JSONObject;

public interface CallbackApplyCouponActivity {
    void onSuccessCoupon(JSONObject response);
    void onFailureCoupon(String response);
    void onNetworkErrorCoupon(String message);
}
