package snd.orgn.foodnfine.activity;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackSubscription;
import snd.orgn.foodnfine.callbacks.CallbackSubscriptionRates;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.data_item.AllSubscriptionCharge;
import snd.orgn.foodnfine.rest.request.SubcriptionRequest;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.SubcriptionPlainViewModel;

import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_PROCESSING_REQUEST;

public class SubcriptionActivity extends BaseActivity implements CallbackSubscriptionRates, CallbackSubscription {
    @BindView(R.id.iv_subcription_back)
    ImageView iv_subcription_back;
    @BindView(R.id.selected_type_category)
    Spinner selected_type_category;
    @BindView(R.id.tv_subcription_charges)
    TextView tv_subcription_charges;

    @BindView(R.id.btn_subcriptionConfirm)
    CardView cvBtn_subscribe;
    @BindView(R.id.rg_payment_subcription)
    RadioGroup rg_payment_subcription;
    SubcriptionPlainViewModel viewModel;
    private List<AllSubscriptionCharge> subscriptionChargeList;
    private List<String> subscriptionPlanName;
    private String subcription_charges = "00.00";
    private String subcription_id = "1";
    private String selectSubcriptionPlanName = "";
    private String pay_id = "0";
    private String payType = "COD";
    private LoadingDialogHelper loadingDialogHelper;
    LoadingDialog loadingDialog;
    String payment = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        hideStatusBarcolor();
        initFields();
        setupOnClick();
    }

    @Override
    public void initFields() {
        initViewModel();
        loadingDialog.showDialog();
        viewModel.getSubcriptionList();
        subscriptionChargeList = new ArrayList<>();
        subscriptionPlanName = new ArrayList<>();

        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_PROCESSING_REQUEST);
    }

    @Override
    public void setupOnClick() {
        iv_subcription_back.setOnClickListener(v -> {
            super.onBackPressed();
        });
        cvBtn_subscribe.setOnClickListener(v -> {

            int selectedId_meal = rg_payment_subcription.getCheckedRadioButtonId();
            switch (selectedId_meal) {

                case R.id.rb_payment_option_wallet:
                    payment = "wallet";
                    break;

                case R.id.rb_payment_option_op:
                    payment = "op";
                    break;

                case R.id.rb_payment_option_cod:
                    payment = "cod";
                    break;

                default:
                    payment = "null";
                    break;
            }
            if (payment.equals("null")) {
                Toast.makeText(this, "Please Select payment Type", Toast.LENGTH_SHORT).show();
            } else {
                if (payment.equals("cod")) {

                    loadingDialogHelper.show(getSupportFragmentManager(), "subcriptionDialog_subcriptionActivity");
                    viewModel.confirmSubcriptionPlan(getRequsetData());

                } else /*if (payment.equals("op"))*/ {
                    Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                            "We are taking payment only in 'Working Time'", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SubcriptionPlainViewModel.class);
        viewModel.setCallback(this);
        viewModel.setCallback2(this);
    }

    @Override
    public void onSuccess(List<AllSubscriptionCharge> allSubscriptionChargeList) {
        loadingDialog.hideDialog();
        subscriptionChargeList.clear();
        this.subscriptionChargeList = allSubscriptionChargeList;
        int dataCounter = 0;
        //  subscriptionPlanName.add("Select plain ");

        for (AllSubscriptionCharge allSubscriptionCharge : allSubscriptionChargeList) {
            subscriptionPlanName.add(allSubscriptionCharge.getMonth());
            dataCounter++;
            if (dataCounter == allSubscriptionChargeList.size()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subscriptionPlanName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selected_type_category.setAdapter(adapter);
                setupSpinner();
            }
        }
    }

    @Override
    public void onError(String message) {
        loadingDialog.hideDialog();
        //loadingDialogHelper.dismiss();
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialog.hideDialog();
        //loadingDialogHelper.dismiss();
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onSuccessSubcriptionPlan() {
        loadingDialog.hideDialog();
        loadingDialogHelper.dismiss();
        showSuccessPopup();
    }

    @Override
    public void onError2(String message) {
        loadingDialogHelper.dismiss();
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError2(String message) {
        loadingDialogHelper.dismiss();
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setupSpinner() {
        selected_type_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  if (position != 0) {
                selectSubcriptionPlanName = subscriptionChargeList.get(position).getMonth();
                subcription_id = subscriptionChargeList.get(position).getSubscriptionPlanId();
                subcription_charges = subscriptionChargeList.get(position).getCharges();
                tv_subcription_charges.setText("₹" + subcription_charges + ".00");

                //  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private SubcriptionRequest getRequsetData() {
        SubcriptionRequest request = new SubcriptionRequest();
        request.setPayId(pay_id);
        request.setPayType(payType);
        request.setSubscriptionPlan(selectSubcriptionPlanName);
        request.setSubscriptionPlanId(subcription_id);
        request.setUserId(FoodnFine.getAppSharedPreference().getUserId());

        return request;
    }

    private void showSuccessPopup() {
        /*new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.dialogTitle_success))
                .content(getResources().getString(R.string.dialogMessage_user_subcription))
                .positiveText(getResources().getString(R.string.dialogPositiveButtonText_subcription))
                .positiveColor(ContextCompat.getColor(this, R.color.colorTranslucentButton))
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    super.onBackPressed();
                }).show();*/

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
        alertDialog2.setTitle(getResources().getString(R.string.dialogTitle_success));
        alertDialog2.setMessage(getResources().getString(R.string.dialogMessage_user_subcription));
        alertDialog2.setPositiveButton(getResources().getString(R.string.dialogPositiveButtonText_subcription),
                (dialog, which) -> super.onBackPressed());
        /*alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "You clicked on NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });*/
        alertDialog2.setCancelable(false);
        alertDialog2.show();
    }
}
