package snd.orgn.foodnfine.callbacks;

import snd.orgn.foodnfine.model.utility.UserData;

public interface CallBackUserProfile {
    void onStarted();
    void onSuccess(UserData userData);
    void onError(String message);
    void onNetworkError(String message);
}
