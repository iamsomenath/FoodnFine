package snd.orgn.foodnfine.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;

public class OtherStoreActivity extends BaseActivity {
    @BindView(R.id.iv_otherStore_back)
    ImageView iv_otherStore_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_store);
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
        iv_otherStore_back.setOnClickListener(v -> {
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
}
