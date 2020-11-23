package snd.orgn.foodnfine

interface SignupVIew {
    fun showProgress()
    fun hideProgress()
    fun setSignUpError(errResponse: String)
    fun navigateToOTP(response: String)
}