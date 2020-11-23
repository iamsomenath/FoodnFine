package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.model.data_item.Order;

public interface CallbackOrderListActivity {
    void onSuccess(List<Order> orderlists);
    void onError(String message);
    void onNetworkError(String message);
}
