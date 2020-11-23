package snd.orgn.foodnfine.callbacks

interface CallbackCancelOrder {
    fun onSuccessCancel(response : String?)
    fun onErrorCancel(message: String?)
    fun onNetworkErrorCancel(message: String?)
}