package snd.orgn.foodnfine.callbacks;

public interface CallbackloginMobileNo {
    void onSuccess(String otp, String user_id);
    void onError(String message);
    void onNetworkError(String message);
}
