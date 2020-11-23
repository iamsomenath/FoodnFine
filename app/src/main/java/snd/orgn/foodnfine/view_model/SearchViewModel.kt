package snd.orgn.foodnfine.view_model

import android.annotation.SuppressLint

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import snd.orgn.foodnfine.base.BaseViewModel
import snd.orgn.foodnfine.callbacks.CallbackSearchActivity
import snd.orgn.foodnfine.rest.api.ApiInterface

import snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE
import snd.orgn.foodnfine.rest.request.UserRequest

class SearchViewModel : BaseViewModel() {

    private val apiInterface: ApiInterface = getAPIInterface()
    private var callback: CallbackSearchActivity? = null

    fun setCallback(callback: CallbackSearchActivity) {
        this.callback = callback
    }

    fun setSearchQuery(str: String) {
        makeRequest(str)
    }

    private fun makeRequest(str: String) {
        apiCallOrderList(str)
    }

    fun cartDetails(request: UserRequest){
        makeCartDetailsRequest(request)
    }

    @SuppressLint("CheckResult")
    private fun apiCallOrderList(str: String) {
        val userResponseObservable = apiInterface.searchResult(str)
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restResponse ->
                    if (restResponse.status!!) {
                        callback!!.onSuccess(restResponse.product_list)
                    } else {
                        callback!!.onError(restResponse.msg)
                    }
                }, { e -> callback!!.onNetworkError(NETWORK_ERROR_MESSAGE) })
    }

    @SuppressLint("CheckResult")
    private fun makeCartDetailsRequest(request: UserRequest) {
        val userResponseObservable = apiInterface.getCartDetails(request.userId, request.orderType)
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restResponse ->
                    if (restResponse.status == 1) {
                        callback!!.onCartDetailsSuccess(restResponse)
                    } else {
                        callback!!.onCartDetailsError(restResponse.msg)
                    }
                }, { e -> callback!!.onCartDetailsNetworkError("Your cart is empty") })
    }
}
