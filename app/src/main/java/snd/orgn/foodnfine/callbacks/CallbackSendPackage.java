package snd.orgn.foodnfine.callbacks;

public interface CallbackSendPackage {
    void onSuccess(String order_no);
    void OnError(String message);
    void onNetworkError(String message);
}
