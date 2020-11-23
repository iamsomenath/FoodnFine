package snd.orgn.foodnfine.activity;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;

public class SelectServiceActivity extends BaseActivity {

    @BindView(R.id.cvBtn_subscribe)
    CardView cvBtn_subscribe;
    @BindView(R.id.tvBtn_dailyBooking)
    CardView tvBtn_dailyBooking;
    @BindView(R.id.iv_selectService_back)
    ImageView iv_selectService_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        initFields();
        setupOnClick();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void setupOnClick() {
        tvBtn_dailyBooking.setOnClickListener(v -> {
            goToDailyBooking();
        });
        cvBtn_subscribe.setOnClickListener(v -> {
            goToSubcribe();
        });
        iv_selectService_back.setOnClickListener(v->{
            super.onBackPressed();
        });
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }


    private void goToDailyBooking() {
        Intent intent = new Intent(this, DailyBookingActivity.class);
        //intent.putExtra("phone_number", String.valueOf(et_login_mobileNo.getText()));
        startActivity(intent);
    }

    private void goToSubcribe() {
        Intent intent = new Intent(this, SubcriptionActivity.class);
        startActivity(intent);
    }

}
