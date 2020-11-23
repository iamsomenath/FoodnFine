package com.app.kgs.login_mvp

interface LoginView {

    fun showProgress()
    fun hideProgress()
    fun setLoginError(errResponse: String)
    fun navigateToHome(response: String)
}