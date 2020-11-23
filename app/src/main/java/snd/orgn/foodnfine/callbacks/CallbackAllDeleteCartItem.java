package snd.orgn.foodnfine.callbacks;

public interface CallbackAllDeleteCartItem {
    void onSuccess();
    void onError(String message);
    void onNetworkError(String message);
}
