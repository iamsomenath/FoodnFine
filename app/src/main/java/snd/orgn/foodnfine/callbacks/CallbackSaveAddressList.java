package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.data.room.entity.AddressDetails;

public interface CallbackSaveAddressList {
    void onSuccessGetAddress(List<AddressDetails> addressDetailsList);
    void onSuccessDeleteAddress();
}
