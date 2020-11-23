package snd.orgn.foodnfine.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackUpdateProfile;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.view_model.ActivityViewModel.UpdateProfileViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_LOGGING_IN;

public class UpdateProfileActivity extends BaseActivity implements CallbackUpdateProfile {

    @BindView(R.id.tvBtn_updateProfile)
    CardView tvBtn_updateProfile;
    @BindView(R.id.et_input_email)
    TextInputEditText et_input_email;
    @BindView(R.id.et_input_name)
    TextInputEditText et_input_name;
    UpdateProfileViewModel viewModel;
    @BindView(R.id.iv_updateProfile_back)
    ImageView iv_updateProfile_back;
    @BindView(R.id.layout_input_name)
    TextInputLayout layout_inputName;
    @BindView(R.id.layout_text_input_email)
    TextInputLayout layout_email;
    @BindView(R.id.et_input_mobile)
    TextInputEditText et_input_mobile;
    LoadingDialogHelper loadingDialogHelper;
    View rootView;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        initFields();
        et_input_name.setText(DeliveryEverything.getAppSharedPreference().getUsername());
        et_input_name.setSelection(DeliveryEverything.getAppSharedPreference().getUsername().length());
        et_input_email.setText(DeliveryEverything.getAppSharedPreference().getUserEmail());
        et_input_mobile.setText(DeliveryEverything.getAppSharedPreference().getUserMobile());
    }

    @Override
    public void initFields() {
        rootView = this.findViewById(android.R.id.content).getRootView();
        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_LOGGING_IN);
        setupUI(rootView, UpdateProfileActivity.this);
        textcheck();
        initViewModel();
        setupOnClick();
    }

    private void textcheck(){
        et_input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                layout_inputName.setError(null);
//                if (s.length() > layout_inputName.getCounterMaxLength())
//                    layout_inputName.setError("Please enter name ");
//                else
//                    layout_inputName.setError(null);

            }
        });

        et_input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                layout_email.setError(null);
//
//                if (s.length() > layout_email.getCounterMaxLength())
//                    layout_email.setError("Please enter valid email ");
//                else
//                    layout_email.setError(null);

            }
        });
    }

    @Override
    public void setupOnClick() {
        tvBtn_updateProfile.setOnClickListener(view -> {
            if (checkValidation()) {
                loadingDialogHelper.show(getSupportFragmentManager(), "loadingDialog_updateProfileActivity");
                viewModel.updateProfile(getUserData());
            }
        });

        iv_updateProfile_back.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }

    private boolean checkValidation() {
        boolean validated = false;
        int validationcount = 0;
        if ((et_input_name.getText().toString().trim().equals("")) || (et_input_name.getText().length() == 0)) {
            et_input_name.requestFocus();
            validated = false;
            layout_inputName.setError("Please enter name");
        } else {
            validated = true;
            validationcount++;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_input_email.getText().toString().trim()).matches()) {
            et_input_email.requestFocus();
            validated = false;
            layout_email.setError("Please enter valid email");
        } else {
            validated = true;
            validationcount++;
        }

        if (validationcount == 2) {
            validationcount = 0;
            return validated;
        } else {
            return false;
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        viewModel.setCallback(this);
    }

    private UserData getUserData() {
        userData = new UserData();
        userData.setUser_eml(String.valueOf(et_input_email.getText()));
        userData.setUser_nm(String.valueOf(et_input_name.getText()));
        userData.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        return userData;
    }


    @Override
    public void onSuccess() {
        loadingDialogHelper.dismiss();
        new SessionManager(this).setEmail_Name(userData.getUser_eml(), userData.getUser_nm());
        Toast.makeText(this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialogHelper.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }
}
