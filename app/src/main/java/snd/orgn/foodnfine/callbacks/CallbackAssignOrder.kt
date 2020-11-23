package snd.orgn.foodnfine.callbacks

public interface CallbackAssignOrder {
    fun onSuccessAssignOrder()
    fun onErrorAssignOrder(message: String?)
}