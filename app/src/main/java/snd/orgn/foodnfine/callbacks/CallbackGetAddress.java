package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.data.room.entity.AddressDetails;

public interface CallbackGetAddress {
    void onSuccessGetAddress(List<AddressDetails> addressDetailsList);
    void onErrorGetAddress(String message);
}
