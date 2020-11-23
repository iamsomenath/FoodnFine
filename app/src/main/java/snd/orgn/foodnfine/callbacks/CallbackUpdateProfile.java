package snd.orgn.foodnfine.callbacks;

public interface CallbackUpdateProfile {
    void onSuccess();
    void onError(String message);
    void onNetworkError(String message);
}
