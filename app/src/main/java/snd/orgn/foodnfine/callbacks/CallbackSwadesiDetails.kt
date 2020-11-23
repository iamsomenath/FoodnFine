package snd.orgn.foodnfine.callbacks

interface CallbackSwadesiDetails {
    fun onSuccess(getdetails: String)
    fun onError(message: String)
    fun onNetworkError(message: String)
}