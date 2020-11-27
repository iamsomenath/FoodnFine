package snd.orgn.foodnfine.signup_mvp

interface SignupVIew {
    fun showProgress()
    fun hideProgress()
    fun setSignUpError(errResponse: String)
    fun navigateToOTP(response: String)
}