package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BackHandledFragment;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.fragment.payment_cod;

import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_TOTAL_PRICE;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_GROCERY;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_RESTAURANT;

public class SelectPaymentActivity extends BaseActivity  implements BackHandledFragment.BackHandlerInterface {

    private BackHandledFragment selectedFragment;
    @BindView(R.id.tv_item_total)
    TextView tv_item_total;
    @BindView(R.id.iv_select_payment_back)
    ImageView iv_select_payment_back;
    String totalCharges;
    private String orderType="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        orderType = getIntent().getStringExtra(INTENT_STRING_ORDER_TYPE);
        totalCharges = getIntent().getStringExtra(INTENT_STRING_TOTAL_PRICE);

        if(orderType.equals(ORDER_TYPE_GROCERY)){
            DeliveryEverything.getAppSharedPreference().saveCharges(totalCharges);
        }else if(orderType.equals(ORDER_TYPE_RESTAURANT)){
            DeliveryEverything.getAppSharedPreference().saveCharges(totalCharges);
        }else{
            //totalCharges = DeliveryEverything.getAppSharedPreference().getCharges();
            DeliveryEverything.getAppSharedPreference().saveCharges(totalCharges);
            DeliveryEverything.getAppSharedPreference().setOrderType(orderType);
        }

        tv_item_total.setText("Item total " + " " + "\u20B9" + totalCharges);

        initFields();
        setupOnClick();
    }

    @Override
    public void initFields() {
        //Fragment newFragment = new payment_home();
        //if(getIntent().getStringExtra(""))
        Fragment newFragment = new payment_cod();
        getSupportFragmentManager().beginTransaction().add(R.id.container_payment, newFragment).commit();
    }

    @Override
    public void setupOnClick() {
        iv_select_payment_back.setOnClickListener(v->{
            super.onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        getFragmentManager().popBackStack();
        //cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            getFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }
}
