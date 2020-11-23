package snd.orgn.foodnfine.callbacks;

public interface CallbackSelectedCartItemUpdate {
    void onEditedCartItem(String cartId,String qty,String price);
    void onDeletedCartItem(String cartId);
}
