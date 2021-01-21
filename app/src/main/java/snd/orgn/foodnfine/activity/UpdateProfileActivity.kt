package snd.orgn.foodnfine.activity

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_update_profile.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.application.FoodnFine.Companion.appSharedPreference
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallbackUpdateProfile
import snd.orgn.foodnfine.constant.AppConstants
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper
import snd.orgn.foodnfine.model.utility.UserData
import snd.orgn.foodnfine.view_model.ActivityViewModel.UpdateProfileViewModel

class UpdateProfileActivity : BaseActivity(), CallbackUpdateProfile {

    var loadingDialogHelper: LoadingDialogHelper? = null
    var rootView: View? = null
    var userData: UserData? = null
    var viewModel: UpdateProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        ButterKnife.bind(this)
        hideStatusBarcolor()
        initFields()
        et_input_name!!.setText(appSharedPreference!!.username)
        et_input_name!!.setSelection(appSharedPreference!!.username.length)
        et_input_email!!.setText(appSharedPreference!!.userEmail)
        et_input_mobile!!.setText(appSharedPreference!!.userMobile)
    }

    override fun initFields() {
        rootView = findViewById<View>(android.R.id.content).rootView
        loadingDialogHelper = getLoadingDialog(AppConstants.LOADING_DIALOG_LOGGING_IN)
        setupUI(rootView, this@UpdateProfileActivity)
        textcheck()
        initViewModel()
        setupOnClick()
    }

    private fun textcheck() {
        et_input_name!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                layout_input_name!!.error = null
                //                if (s.length() > layout_inputName.getCounterMaxLength())
//                    layout_inputName.setError("Please enter name ");
//                else
//                    layout_inputName.setError(null);
            }
        })
        et_input_email!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                layout_text_input_email!!.error = null
                //
//                if (s.length() > layout_email.getCounterMaxLength())
//                    layout_email.setError("Please enter valid email ");
//                else
//                    layout_email.setError(null);
            }
        })
    }

    override fun setupOnClick() {
        tvBtn_updateProfile!!.setOnClickListener { view: View? ->
            if (checkValidation()) {
                loadingDialogHelper!!.show(supportFragmentManager, "loadingDialog_updateProfileActivity")
                viewModel!!.updateProfile(getUserData())
            }
        }
        iv_updateProfile_back!!.setOnClickListener { v: View? -> super.onBackPressed() }
    }

    private fun checkValidation(): Boolean {
        var validated = false
        var validationcount = 0
        if (et_input_name!!.text.toString().trim { it <= ' ' } == "" || et_input_name!!.text!!.length == 0) {
            et_input_name!!.requestFocus()
            validated = false
            layout_input_name!!.error = "Please enter name"
        } else {
            validated = true
            validationcount++
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_input_email!!.text.toString().trim { it <= ' ' }).matches()) {
            et_input_email!!.requestFocus()
            validated = false
            layout_text_input_email!!.error = "Please enter valid email"
        } else {
            validated = true
            validationcount++
        }
        return if (validationcount == 2) {
            validationcount = 0
            validated
        } else {
            false
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel::class.java)
        viewModel!!.setCallback(this)
    }

    @JvmName("getUserData1")
    private fun getUserData(): UserData {
        userData = UserData()
        userData!!.user_eml = et_input_email!!.text.toString()
        userData!!.user_nm = et_input_name!!.text.toString()
        userData!!.user_id = appSharedPreference!!.userId
        return userData as UserData
    }

    override fun onSuccess() {
        loadingDialogHelper!!.dismiss()
        SessionManager(this).setEmail_Name(userData!!.user_eml, userData!!.user_nm)
        Toast.makeText(this, "Profile Update Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onError(message: String) {
        loadingDialogHelper!!.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onNetworkError(message: String) {
        loadingDialogHelper!!.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }
}