package snd.orgn.foodnfine

import android.app.Activity
import com.app.kgs.signup_mvp.SignupInterceptor


class SignupPresenter internal constructor(private var signupVIew: SignupVIew?, private val signupInterceptor: SignupInterceptor) :
        SignupInterceptor.OnSignUpFinishedListener {

    fun validateCredentials(email: String, name: String, password: String, phone: String, activity: Activity
    ) {
        if (signupVIew != null) {
            signupVIew!!.showProgress()
        }
        signupInterceptor.signup(email, name, password, phone, this, activity)
    }

    override fun onSuccess(response: String) {
        if (signupVIew != null) {
            signupVIew!!.hideProgress()
            signupVIew!!.navigateToOTP(response)
        }
    }

    override fun onSignupFailure(errResponse: String) {
        if (signupVIew != null) {
            signupVIew!!.hideProgress()
            signupVIew!!.setSignUpError(errResponse)
        }
    }
}
