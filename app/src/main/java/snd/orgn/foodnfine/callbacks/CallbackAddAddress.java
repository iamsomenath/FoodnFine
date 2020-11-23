package snd.orgn.foodnfine.callbacks;

public interface CallbackAddAddress {
    void onSuccess();
    void onError(String message);
    void onNetworkError(String message);
}
