package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.login_mvp.LoginActivity;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallBackUserProfile;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.AccountDetailsViewModel;

public class AccountDetailsActivity extends BaseActivity implements CallBackUserProfile {

    @BindView(R.id.iv_accountDetails_back)
    ImageView iv_accountDetails_back;
    @BindView(R.id.layout_gotoCompleteSetup)
    LinearLayout layout_gotoCompleteSetup;
    @BindView(R.id.layout_saveAddress)
    ConstraintLayout layout_saveAddress;
    @BindView(R.id.tv_userMobileNo)
    TextView tv_userMobileNo;
    AccountDetailsViewModel viewModel;
    @BindView(R.id.layout_logout)
    LinearLayout layout_logout;
    @BindView(R.id.layout_subs)
    LinearLayout layout_subs;
    @BindView(R.id.accountDetails_about)
    ConstraintLayout accountDetails_about;
    @BindView(R.id.orders_layout)
    ConstraintLayout orders_layout;
    @BindView(R.id.layout_notification)
    LinearLayout layout_notification;
    @BindView(R.id.layout_contactus)
    ConstraintLayout layout_contactus;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        initFields();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initFields() {
        hideStatusBarcolor();
        tv_userMobileNo.setText("+91-" + FoodnFine.getAppSharedPreference().getUserMobile());
        setupOnClick();
    }

    @Override
    public void setupOnClick() {
        iv_accountDetails_back.setOnClickListener(v -> {
            super.onBackPressed();
        });

        layout_gotoCompleteSetup.setOnClickListener(v->{
            gotoUpdateProfile();
        });
        layout_saveAddress.setOnClickListener(v->{
            gotoUpdateSaveAddress();
        });
        layout_contactus.setOnClickListener(v -> {
            gotoContactUs();
        });
        layout_subs.setOnClickListener(v->{
            gotoSubscription();
        });
        layout_logout.setOnClickListener(V->{
            showLogoutPopup();
        });
        accountDetails_about.setOnClickListener(V->{
            gotoAboutPage();
        });
        orders_layout.setOnClickListener(V->{
            gotoOrderPage();
        });
        layout_notification.setOnClickListener(V->{
            gotoNotificationPage();
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        viewModel = ViewModelProviders.of(this).get(AccountDetailsViewModel.class);
        viewModel.getUserData(this, FoodnFine.getAppSharedPreference().getUserId());
    }

    private void gotoNotificationPage() {
        //Intent intent = new Intent(this, NotificationActivity.class);
        Intent intent = new Intent(this, OffersActivity.class);
        startActivity(intent);
    }

    private void gotoOrderPage() {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        intent.putExtra("FROM", "AccountDetailsActivity");
        startActivity(intent);
    }

    private void gotoAboutPage() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void gotoUpdateProfile() {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivity(intent);
    }

    private void gotoUpdateSaveAddress() {
        Intent intent = new Intent(this, SavedAddressActivity.class);
        startActivity(intent);
    }

    private void gotoContactUs() {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    private void gotoSubscription() {
        Intent intent = new Intent(this, SubscriptionActivity.class);
        startActivity(intent);
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    private void showLogoutPopup() {
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

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
        alertDialog2.setTitle(getResources().getString(R.string.dialogTitle_logout));
        alertDialog2.setMessage(getResources().getString(R.string.dialogMessage_logout));
        alertDialog2.setPositiveButton("Yes",
                (dialog, which) -> {
                    FoodnFine.getAppSharedPreference().clearData();
                    goToLoginActivity();
                });
        alertDialog2.setNegativeButton("NO",
                (dialog, which) -> {
                    dialog.dismiss();
                });
        alertDialog2.setCancelable(false);
        alertDialog2.show();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(AccountDetailsActivity.this, LoginActivity.class);
        finishAffinity();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStarted(){
        loadingDialog.showDialog();
    }

    @Override
    public void onSuccess(UserData userData) {
        loadingDialog.hideDialog();
        FoodnFine.getAppSharedPreference().setUserMobile(userData.getUserMobile());
        FoodnFine.getAppSharedPreference().setUserEmail(userData.getUser_eml());
        FoodnFine.getAppSharedPreference().setUserName(userData.getName());
    }

    @Override
    public void onError(String message) {
        loadingDialog.hideDialog();
       /* Snackbar.make(this.findViewById(android.R.id.content),
                "", Snackbar.LENGTH_LONG).show();*/
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialog.hideDialog();
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
