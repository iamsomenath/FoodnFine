package snd.orgn.foodnfine.activity;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackOtpVerification;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.view_model.ActivityViewModel.OtpVerificationViewModel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_LOGGING_IN;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_PROCESSING_REQUEST;

public class OtpCheckActivity extends BaseActivity implements CallbackOtpVerification {

    @BindView(R.id.tvBtn_verify_continue)
    CardView cvBtn_resend;
    @BindView(R.id.tv_mobileNumber)
    TextView mobileNumber;
    @BindView(R.id.pinview_checkOtp)
    Pinview pinview;

    @BindView(R.id.countdownTimer_otpVerification)
    TextView countDownTimer;
    @BindView(R.id.layout_countdownTimer)
    LinearLayout layoutCounter;
    @BindView(R.id.tv_btn_change)
    TextView tv_btn_change;
    @BindView(R.id.et_checkOtp)
    EditText et_checkOtp;
    OtpVerificationViewModel viewModel;
    LoadingDialogHelper loadingDialogHelper;
    LoadingDialogHelper loadingDialog_resendOtp;
    View rootView;
    String userMobile;
    String receiveOtp;

    private boolean visible_resentBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_check);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        rootView = this.findViewById(android.R.id.content).getRootView();
        setupUI(rootView, OtpCheckActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        userMobile =  getIntent().getStringExtra("phone_number");
//        receiveOtp =  getIntent().getStringExtra("otp");
        mobileNumber.setText("+91 " + userMobile);

      //  et_checkOtp.setText(receiveOtp);
     //   resendBtnOn = false;
        initFields();
        setupOnClick();
        startCounter();

    }

    @Override
    public void initFields() {
        rootView = this.findViewById(android.R.id.content).getRootView();
        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_LOGGING_IN);
        loadingDialog_resendOtp = getLoadingDialog(LOADING_DIALOG_PROCESSING_REQUEST);
        setupUI(rootView, OtpCheckActivity.this);
        initViewModel();
        otpCheck();
        setupOnClick();
    }

    @Override
    public void setupOnClick() {
        cvBtn_resend.setOnClickListener(view -> {
            loadingDialog_resendOtp.show(getSupportFragmentManager(), "loadingDialog_otpVerification_resend");
            viewModel.resentOtp(getUserDataForResendOtp());

        });
        tv_btn_change.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginMobileNoActivity.class);
            startActivity(intent);
        });
    }

    private void otpCheck() {
        pinview.setPinViewEventListener((pinview1, b) -> {

            if (pinview1.getValue().length() == 4) {
                // loadingDialog_Login.show(getSupportFragmentManager(), "loadingDialog_otpVerification_resend");
                loadingDialogHelper.show(getSupportFragmentManager(), "loadingDialog_otpVerification");
                receiveOtp = pinview1.getValue().toString();
                viewModel.loginByOtp_user(getUserData());
                // hideKeyboard();
            }
        });
    }

    private void startCounter() {
        cvBtn_resend.setVisibility(View.GONE);
        layoutCounter.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                visible_resentBtn = false;
                countDownTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                layoutCounter.setVisibility(View.GONE);
                visible_resentBtn = true;
                cvBtn_resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(OtpVerificationViewModel.class);
        viewModel.setCallback(this);
    }

    private UserData getUserData() {
        UserData userData = new UserData();
        userData.setOtp(receiveOtp);
        userData.setUserMobile(String.valueOf(userMobile));
        return userData;
    }

    private UserData getUserDataForResendOtp() {
        UserData userData = new UserData();
        userData.setUserMobile(String.valueOf(userMobile));
        userData.setDev_key(String.valueOf(DeliveryEverything.getAppSharedPreference().getDevKey()));
        return userData;
    }

    private void hideStatusBarcolor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @SuppressLint("MissingPermission")
    private void goToCallMobilePhone(){
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(OtpCheckActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OtpCheckActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +userMobile.trim()));
                startActivity(callIntent);
            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + userMobile.trim()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void goTodashboard(){
        Intent intent = new Intent(this, GPSActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess() {
        loadingDialogHelper.dismiss();
        DeliveryEverything.getAppSharedPreference().setUserMobile(userMobile);
        goTodashboard();
    }

    @Override
    public void onSuccessByPhoneNo() {
        loadingDialog_resendOtp.dismiss();
        startCounter();
       // goTodashboard();
    }

    @Override
    public void onError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResendOtp(String message) {
        loadingDialog_resendOtp.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkErrorResendOtp(String message) {
        loadingDialog_resendOtp.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
