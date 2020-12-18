package snd.orgn.foodnfine.signup_mvp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONException
import org.json.JSONObject
import snd.orgn.foodnfine.login_mvp.LoginActivity
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.util.snackbar
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.util.NetworkChangeReceiver
import snd.orgn.foodnfine.util.toast

class SignUpActivity : AppCompatActivity(), SignupVIew {

    lateinit var signuppresenter: SignupPresenter
    lateinit var sessionManager: SessionManager
    lateinit var loadingDialog: LoadingDialog
    internal var network: Boolean? = false
    lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signuppresenter = SignupPresenter(this, SignupInterceptor())
        sessionManager = SessionManager(this)
        loadingDialog = LoadingDialog(this)
        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver.isNetworkAvailable

        login.setOnClickListener {
            overridePendingTransition(
                    R.anim.left_in,
                    R.anim.right_out
            )
            finish()
        }

        signup.setOnClickListener {
            when {
                name.text.toString().isEmpty() -> {
                    name.error = "Please Enter Name"
                    name.requestFocus()
                    //contentLayout.snackbar("Please Enter Name")
                }
                mobile.text.toString().isEmpty() -> {
                    mobile.error = "Please Enter Mobile Number"
                    mobile.requestFocus()
                    //contentLayout.snackbar("Please Enter Mobile Number")
                }
                mobile.text.toString().length != 10 -> {
                    mobile.error = "Please Enter Valid Mobile Number"
                    mobile.requestFocus()
                    //contentLayout.snackbar("Please Enter Valid Mobile Number")
                }
                etEmail.text.toString().isEmpty() -> {
                    etEmail.error = "Please Enter Email"
                    etEmail.requestFocus()
                    //contentLayout.snackbar("Please Enter Email")
                }
                !isValidEmail(etEmail.text.toString()) -> {
                    etEmail.error = "Please Enter Valid Email"
                    etEmail.requestFocus()
                    //contentLayout.snackbar("Please Enter Valid Mobile Number")
                }
                etPassword.text.toString().isEmpty() -> {
                    etPassword.error = "Please Enter Password"
                    etPassword.requestFocus()
                    //contentLayout.snackbar("Please Enter Password")
                }
                etConfirmPassword.text.toString().isEmpty() -> {
                    etConfirmPassword.error = "Please Enter Confirm Password"
                    etConfirmPassword.requestFocus()
                    //contentLayout.snackbar("Please Enter Password")
                }
                etPassword.text.toString() != etConfirmPassword.text.toString() -> {
                    etConfirmPassword.error = "Password & Confirm Password not same"
                    etConfirmPassword.requestFocus()
                    //contentLayout.snackbar("Password should be of minimum 6 character")
                }
                else -> {
                    if (network!!) {
                        signuppresenter.validateCredentials(
                                etEmail.text.toString(),
                                name.text.toString(),
                                etPassword.text.toString(),
                                mobile.text.toString(),
                                "dev_key",
                                this@SignUpActivity
                        )
                    } else {
                        //toast("Please check your internet connection!!!")
                        contentLayout.snackbar("Please check your internet connection!!!")
                    }
                }
            }
        }
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun showProgress() {
        loadingDialog.showDialog()
    }

    override fun hideProgress() {
        loadingDialog.hideDialog()
    }

    override fun setSignUpError(errResponse: String) {
        //toast(errResponse)
        contentLayout.snackbar(errResponse)
    }

    override fun navigateToOTP(response: String) {
        try {
            val jsonObject = JSONObject(response)
            //Log.d("User_details", jsonObject.toString())
            if (jsonObject.getString("success") == "SUCCESS") {
                /*sessionManager.setLogin(
                        true, jsonObject.getString("id"), name.text.toString(),
                        etEmail.text.toString(), mobile.text.toString()
                )
                DeliveryEverything.getAppSharedPreference().userId = jsonObject.getString("id")
                DeliveryEverything.getAppSharedPreference().userMobile = jsonObject.getString("contact")
                DeliveryEverything.getAppSharedPreference().userEmail = jsonObject.getString("contact")
                DeliveryEverything.getAppSharedPreference().setUserName(jsonObject.getString("user_name"))*/
                showPopup("Registration Successful. Please Login...")
            } else
                contentLayout.snackbar(jsonObject.getString("message"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun showPopup(message: String) {
        /*val dialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        dialog.titleText = getString(R.string.app_name)
        dialog.setCancelable(false)
        dialog.contentText = message
        dialog.confirmText = "LOGIN"
        dialog.setConfirmClickListener { sDialog ->
            sDialog.dismissWithAnimation()
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(
                    R.anim.left_in,
                    R.anim.right_out
            )
            finishAffinity()
        }
        dialog.show()*/
        toast("Your registration is successful. Please login to proceed.")
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
        )
        finishAffinity()
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
        finish()
    }
}
