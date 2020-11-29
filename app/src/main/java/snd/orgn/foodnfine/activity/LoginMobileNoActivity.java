package snd.orgn.foodnfine.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackloginMobileNo;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.view_model.ActivityViewModel.LoginMobileNoViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_LOGGING_IN;

public class LoginMobileNoActivity extends BaseActivity implements CallbackloginMobileNo {

    @BindView(R.id.cv_loginBtn)
    CardView tvBtn_continue;
    @BindView(R.id.et_login_mobileNo)
    EditText et_login_mobileNo;
    LoginMobileNoViewModel viewModel;

    private String otpSend;
    private String user_id;
    View rootView;
    GoogleApiClient apiClient;
    static int RESOLVE_HINT = 1;
    LoadingDialogHelper loadingDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login_mobile_no);
        ButterKnife.bind(this);

        getFcmToken();
        hideStatusBarcolor();
        initFields();
    }

    @Override
    public void initFields() {
        rootView = this.findViewById(android.R.id.content).getRootView();
        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_LOGGING_IN);
        setupUI(rootView, LoginMobileNoActivity.this);
        initViewModel();
        setupOnClick();
        openDailogMobileNumber();
    }

    @Override
    public void setupOnClick() {

        tvBtn_continue.setOnClickListener(v -> {
            loadingDialogHelper.show(getSupportFragmentManager(), "loadingDialog_loginActivity");
            viewModel.phoneNumbercheck(getUserData());
        });
    }


    private void getFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        FoodnFine.getAppSharedPreference().saveDevKey(token);
                    }
                });
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginMobileNoViewModel.class);
        viewModel.setCallback(this);
    }


    private void gotoOtpPage() {
        Intent intent = new Intent(this, OtpCheckActivity.class);
        intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        intent.putExtra("otp", String.valueOf(otpSend));
        intent.putExtra("user_id", String.valueOf(user_id));
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
        startActivity(intent);
    }


    private UserData getUserData() {
        UserData userData = new UserData();
        userData.setUserMobile(String.valueOf(et_login_mobileNo.getText()));
        userData.setDev_key(String.valueOf(FoodnFine.getAppSharedPreference().getDevKey()));
        return userData;
    }


    private void openDailogMobileNumber(){
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener */)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        apiClient.connect();
        rootView = getWindow().getDecorView().getRootView();
        requestHint(rootView);
    }

    // Construct a request for phone numbers and show the picker
    private void requestHint(View view) {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                apiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            view.requestFocus();
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // ;  <-- will need to process phone number string
                et_login_mobileNo.setText(credential.getId().split("\\+91")[1]);
            }
        }
    }


    private void hideStatusBarcolor(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @Override
    public void onSuccess(String otp, String user_id) {
        loadingDialogHelper.dismiss();
        this.otpSend = otp;
        this.user_id = user_id;
        gotoOtpPage();
    }

    @Override
    public void onError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, "It seems you have no or low network connection!", Toast.LENGTH_SHORT).show();
    }
}
