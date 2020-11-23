package snd.orgn.foodnfine.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.CategoryFragmentPagerAdapter;
import snd.orgn.foodnfine.adapter.viewPagerAdpater.ViewPagerRestrurantDetailsAdapter;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.bottomSheetFragment.BottomSheetSelectItemFragment;
import snd.orgn.foodnfine.callbacks.CallbackDeleteCartResponse;
import snd.orgn.foodnfine.callbacks.CallbackFoodcategoryList;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.fragment.RestourantFragments;
import snd.orgn.foodnfine.model.data_item.FoodCategory;
import snd.orgn.foodnfine.view_model.ActivityViewModel.FoodCategoryViewModel;

// Not REQUIRED
public class RestrurentDetailsActivity extends BaseActivity implements CallbackFoodcategoryList, CallbackDeleteCartResponse {

    @BindView(R.id.sliding_tabs_restaurantDetails)
    TabLayout tabLayout;
    @BindView(R.id.iv_restaurentDetails_back)
    ImageView iv_restaurentDetails_back;
    @BindView(R.id.iv_restaurentDetails_addItem)
    ImageView iv_restaurentDetails_addItem;
    @BindView(R.id.content_frame)
    FrameLayout content_frame;
    @BindView(R.id.tv_restaurantName)
    TextView tv_restaurantName;
    @BindView(R.id.tv_restaurantAddress)
    TextView tv_restaurantAddress;
    boolean firstLoad;
    ViewPager viewPager;
    FoodCategoryViewModel viewModel;
    ViewPagerRestrurantDetailsAdapter viewPagerAdapter;
    private CategoryFragmentPagerAdapter adapter;
    private String restId = "";
    private String restName = "";
    private String address = "";
    private Menu menu;
    private List<FoodCategory> foodCategoryList;
    private List<String> foodCategoryName;
    private List<String> foodCategoryId;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    SessionManager sessionManager;
    HashMap<String, String> data = new HashMap<>();
    private BottomSheetSelectItemFragment bottomSheetSelectGroceryItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrurent_details);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        initBottomSheets();
        restId = getIntent().getStringExtra("rest_id");
        restName = getIntent().getStringExtra("rest_name");
        address = getIntent().getStringExtra("rest_address");
        firstLoad = true;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        sessionManager = new SessionManager(getApplicationContext());
        //retrieving Data
        data = sessionManager.getDetails();
        initFields();
        initViewModel();
        viewModel.getFoodCatergoryList(restId);
        setupOnClick();
    }

    @Override
    public void initFields() {
        foodCategoryList = new ArrayList<>();
        foodCategoryName = new ArrayList<>();
        foodCategoryId = new ArrayList<>();
        tv_restaurantName.setText(restName);
        tv_restaurantAddress.setText(address);
    }

    @Override
    public void setupOnClick() {
        iv_restaurentDetails_back.setOnClickListener(v -> {
            if(DeliveryEverything.getAppSharedPreference().getItemQuantity().equals("")){
                super.onBackPressed();
            }else{
                showBottomSheet();
            }
        });
        iv_restaurentDetails_addItem.setOnClickListener(v -> {
         //   goToConfirmOrder();
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FoodCategoryViewModel.class);
        viewModel.setCallback(this);
    }


    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @Override
    public void onSuccess(List<FoodCategory> foodCategoryList) {
        this.foodCategoryList = foodCategoryList;
        //create multiple titles, but use OneFragment() for every new tab
        for (int i = 0; i < foodCategoryList.size(); i++) {
            foodCategoryName.add(foodCategoryList.get(i).getMenuCategoryName());
            foodCategoryId.add(foodCategoryList.get(i).getFoodCatMenuId());
            mFragmentList.add(new RestourantFragments());
        }
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        // How to add margin between tabs in TabLayout
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 30, 0);
            tab.requestLayout();
        }
    }

    @Override
    public void onError(String message) {

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new CategoryFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < foodCategoryName.size(); i++) {
            Bundle bundle = new Bundle();

                bundle.putString("id", foodCategoryId.get(i));
                bundle.putString("categoryName", foodCategoryName.get(i));
                bundle.putString("restId", restId);

                RestourantFragments detailsFragment = new RestourantFragments();
                detailsFragment.setArguments(bundle);
                adapter.addFragment(detailsFragment, "" + foodCategoryName.get(i), i);
                viewPager.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    private void initBottomSheets() {
        bottomSheetSelectGroceryItemFragment = new BottomSheetSelectItemFragment();
        bottomSheetSelectGroceryItemFragment.setCallback(this);
    }


    private void showBottomSheet() {
        bottomSheetSelectGroceryItemFragment.show(getSupportFragmentManager(), bottomSheetSelectGroceryItemFragment.getTag());
    }


    @Override
    public void onSucessDataDelete() {
        super.onBackPressed();
        DeliveryEverything.getAppSharedPreference().setItemQuantity("");
        overridePendingTransition(R.anim.right_in, R.anim.push_left_out);
        finish();
    }

    public void onBackPressed() {
        if (DeliveryEverything.getAppSharedPreference().getItemQuantity().equals("")) {
            super.onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.push_left_out);
            finish();
        } else {
            showBottomSheet();
        }
    }
}
