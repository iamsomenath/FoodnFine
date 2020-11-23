package snd.orgn.foodnfine.callbacks;

public interface CallbackOtpVerification {
    void onSuccess();
    void onSuccessByPhoneNo();
    void onError(String message);
    void onErrorResendOtp(String message);
    void onNetworkError(String message);
    void onNetworkErrorResendOtp(String message);
}
