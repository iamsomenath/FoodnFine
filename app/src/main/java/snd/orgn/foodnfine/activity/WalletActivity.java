package snd.orgn.foodnfine.activity;

import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.viewPagerAdpater.ViewPagerAdapter;
import snd.orgn.foodnfine.base.BaseActivity;

import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET_HISTORY;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.iv_wallet_back)
    ImageView iv_wallet_back;
    boolean firstLoad;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout.Tab tab;
    String pageType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        firstLoad = true;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initFields();
        setupOnClick();
    }

    @Override
    public void initFields() {
        initViewPageSetup();
    }

    @Override
    public void setupOnClick() {
        iv_wallet_back.setOnClickListener(v -> {
            super.onBackPressed();

        });
    }

    private void initViewPageSetup(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        switch (pageType) {

            case PAGE_TYPE_WALLET_HISTORY:
                tab = tabLayout.getTabAt(1);
                tab.select();
                viewPager.setCurrentItem(1);
                break;
            case PAGE_TYPE_WALLET:
                tab = tabLayout.getTabAt(0);
                tab.select();
                viewPager.setCurrentItem(0);
                break;
            default:
                tab = tabLayout.getTabAt(0);
                tab.select();
                viewPager.setCurrentItem(0);
                break;
        }
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

}
