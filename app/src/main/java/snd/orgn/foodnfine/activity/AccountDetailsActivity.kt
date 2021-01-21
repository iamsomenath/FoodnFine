package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_account_details.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.activity.OffersActivity
import snd.orgn.foodnfine.application.FoodnFine.Companion.appSharedPreference
import snd.orgn.foodnfine.base.BaseActivity
import snd.orgn.foodnfine.callbacks.CallBackUserProfile
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.login_mvp.LoginActivity
import snd.orgn.foodnfine.model.utility.UserData
import snd.orgn.foodnfine.util.LoadingDialog
import snd.orgn.foodnfine.view_model.ActivityViewModel.AccountDetailsViewModel

class AccountDetailsActivity : BaseActivity(), CallBackUserProfile {

    var loadingDialog: LoadingDialog? = null
    var viewModel: AccountDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        ButterKnife.bind(this)
        loadingDialog = LoadingDialog(this)
        initFields()
    }

    @SuppressLint("SetTextI18n")
    override fun initFields() {
        hideStatusBarcolor()
        tv_userMobileNo!!.text = "+91-" + appSharedPreference!!.userMobile
        tv_userName!!.text = appSharedPreference!!.username
        setupOnClick()
    }

    override fun setupOnClick() {
        iv_accountDetails_back!!.setOnClickListener { v: View? -> super.onBackPressed() }
        layout_gotoCompleteSetup!!.setOnClickListener { v: View? -> gotoUpdateProfile() }
        layout_saveAddress!!.setOnClickListener { v: View? -> gotoUpdateSaveAddress() }
        layout_contactus!!.setOnClickListener { v: View? -> gotoContactUs() }
        layout_subs!!.setOnClickListener { v: View? -> gotoSubscription() }
        layout_logout!!.setOnClickListener { V: View? -> showLogoutPopup() }
        accountDetails_about!!.setOnClickListener { V: View? -> gotoAboutPage() }
        orders_layout!!.setOnClickListener { V: View? -> gotoOrderPage() }
        layout_notification!!.setOnClickListener { V: View? -> gotoNotificationPage() }
    }

    public override fun onResume() {
        super.onResume()
        viewModel = ViewModelProviders.of(this).get(AccountDetailsViewModel::class.java)
        viewModel!!.getUserData(this, appSharedPreference!!.userId)
    }

    private fun gotoNotificationPage() {
        //Intent intent = new Intent(this, NotificationActivity.class);
        val intent = Intent(this, OffersActivity::class.java)
        startActivity(intent)
    }

    private fun gotoOrderPage() {
        val intent = Intent(this, MyOrdersActivity::class.java)
        intent.putExtra("FROM", "AccountDetailsActivity")
        startActivity(intent)
    }

    private fun gotoAboutPage() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun gotoUpdateProfile() {
        val intent = Intent(this, UpdateProfileActivity::class.java)
        startActivity(intent)
    }

    private fun gotoUpdateSaveAddress() {
        val intent = Intent(this, SavedAddressActivity::class.java)
        startActivity(intent)
    }

    private fun gotoContactUs() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun gotoSubscription() {
        val intent = Intent(this, SubscriptionActivity::class.java)
        startActivity(intent)
    }

    private fun hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white_background)
        }
    }

    private fun showLogoutPopup() {
        /*new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.dialogTitle_logout))
                .content(getResources().getString(R.string.dialogMessage_logout))
                .positiveText(getResources().getString(R.string.dialogPositiveButtonText_logout))
                .positiveColor(ContextCompat.getColor(this, R.color.button_and_vespac_red_color))
                .negativeText(getResources().getString(R.string.dialogPositiveButtonText_cancel))
                .negativeColor(ContextCompat.getColor(this, R.color.colorTranslucentButton))
                .onPositive((dialog, which) -> {
                    DeliveryEverything.getAppSharedPreference().clearData();
                    goToLoginActivity();
                })
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                }).show();*/
        val alertDialog2 = AlertDialog.Builder(this)
        alertDialog2.setTitle(resources.getString(R.string.dialogTitle_logout))
        alertDialog2.setMessage(resources.getString(R.string.dialogMessage_logout))
        alertDialog2.setPositiveButton("Yes"
        ) { dialog: DialogInterface?, which: Int ->
            appSharedPreference!!.clearData()
            SessionManager(this).logoutUser()
            goToLoginActivity()
        }
        alertDialog2.setNegativeButton("NO"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        alertDialog2.setCancelable(false)
        alertDialog2.show()
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@AccountDetailsActivity, LoginActivity::class.java)
        finishAffinity()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onStarted() {
        loadingDialog!!.showDialog()
    }

    override fun onSuccess(userData: UserData) {
        loadingDialog!!.hideDialog()
        appSharedPreference!!.userMobile = userData.userMobile
        appSharedPreference!!.userEmail = userData.user_eml
        appSharedPreference!!.setUserName(userData.name)
    }

    override fun onError(message: String) {
        loadingDialog!!.hideDialog()
        /* Snackbar.make(this.findViewById(android.R.id.content),
                "", Snackbar.LENGTH_LONG).show();*/
    }

    override fun onNetworkError(message: String) {
        loadingDialog!!.hideDialog()
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}