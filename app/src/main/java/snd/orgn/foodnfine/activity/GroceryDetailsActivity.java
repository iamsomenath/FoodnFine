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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.CategoryFragmentPagerAdapter;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.bottomSheetFragment.BottomSheetSelectItemFragment;
import snd.orgn.foodnfine.callbacks.CallbackDeleteCartResponse;
import snd.orgn.foodnfine.callbacks.CallbackGroceryMainCategoryList;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.fragment.GroceryFragments;
import snd.orgn.foodnfine.model.data_item.AllGrocerycategory;
import snd.orgn.foodnfine.view_model.ActivityViewModel.GroceryMaincategoryViewModel;

public class GroceryDetailsActivity extends BaseActivity implements CallbackGroceryMainCategoryList, CallbackDeleteCartResponse {

    @BindView(R.id.sliding_tabs_groceryDetails)
    TabLayout tabLayout;
    @BindView(R.id.iv_groceryDetails_back)
    ImageView iv_groceryDetails_back;

    @BindView(R.id.content_frame)
    FrameLayout content_frame;
    @BindView(R.id.tv_groceryName)
    TextView tv_groceryName;
    @BindView(R.id.tv_groceryAddress)
    TextView tv_groceryAddress;
    private String clickble = "";
    ViewPager viewPager;
    GroceryMaincategoryViewModel viewModel;
    // ViewPagerRestrurantDetailsAdapter viewPagerAdapter;
    private CategoryFragmentPagerAdapter adapter;
    private BottomSheetSelectItemFragment bottomSheetSelectGroceryItemFragment;
    TabLayout.Tab tab;
    String pageType = "";
    private String restId = "";
    private String restName = "";
    private String address = "";
    private Menu menu;
    private List<AllGrocerycategory> grocerycategoryList;
    private List<String> groceryCategoryName;
    private List<String> groceryfoodCategoryId;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    SessionManager sessionManager;
    HashMap<String, String> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_details);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        initBottomSheets();
        restId = getIntent().getStringExtra("rest_id");
        restName = getIntent().getStringExtra("rest_name");
        address = getIntent().getStringExtra("rest_address");
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        initFields();
        initViewModel();
        viewModel.getGroceyCatergoryList();
        viewModel.deleteAllCartItem(FoodnFine.getAppSharedPreference().getUserId());

        clickble = "";
        setupOnClick();
    }

    @Override
    public void initFields() {
        grocerycategoryList = new ArrayList<>();
        groceryCategoryName = new ArrayList<>();
        groceryfoodCategoryId = new ArrayList<>();
        tv_groceryName.setText(restName);
        tv_groceryAddress.setText(address);
    }

    @Override
    public void setupOnClick() {
        iv_groceryDetails_back.setOnClickListener(v -> {
            if(FoodnFine.getAppSharedPreference().getItemQuantity().equals("")){
                super.onBackPressed();
            }else{
                showBottomSheet();
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroceryMaincategoryViewModel.class);
        viewModel.setCallback(this);
    }


    @Override
    public void onSuccess(List<AllGrocerycategory> grocerycategories) {
        this.grocerycategoryList = grocerycategories;
        //create multiple titles, but use OneFragment() for every new tab
        for (int i = 0; i < grocerycategories.size(); i++) {
            groceryCategoryName.add(grocerycategories.get(i).getCategoryName());
            groceryfoodCategoryId.add(grocerycategories.get(i).getCategoryId());
            mFragmentList.add(new GroceryFragments());
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
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError() {
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new CategoryFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < groceryCategoryName.size(); i++) {
            Bundle bundle = new Bundle();

            bundle.putString("id", groceryfoodCategoryId.get(i));
            bundle.putString("categoryName", groceryCategoryName.get(i));
            bundle.putString("restId", restId);

            GroceryFragments detailsFragment = new GroceryFragments();
            detailsFragment.setArguments(bundle);
            adapter.addFragment(detailsFragment, "" + groceryCategoryName.get(i), i);
            viewPager.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
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
        FoodnFine.getAppSharedPreference().setItemQuantity("");
        overridePendingTransition(R.anim.right_in, R.anim.push_left_out);
        finish();
    }

    public void onBackPressed() {
        if (FoodnFine.getAppSharedPreference().getItemQuantity().equals("")) {
            super.onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.push_left_out);
            finish();
        } else {
            showBottomSheet();
        }
    }
}
