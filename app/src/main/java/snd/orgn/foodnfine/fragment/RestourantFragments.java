package snd.orgn.foodnfine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.ConfirmOrderActivity;
import snd.orgn.foodnfine.adapter.fragmentAdapter.BreakfastFragAdapter;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.callbacks.CallbackAddtoCart;
import snd.orgn.foodnfine.callbacks.CallbackFoodDetailsList;
import snd.orgn.foodnfine.callbacks.CallbackGroceryListItemSelectAdapter;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.data_item.RestFood;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.response.RestResponseCart;
import snd.orgn.foodnfine.view_model.FragmentViewModel.AddToCardViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.FoodDetailsViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.ViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.ViewModelFragment;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_CART_DETAIL;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_PROCESSING_REQUEST;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_RESTAURANT;

public class RestourantFragments extends ViewModelFragment implements CallbackFoodDetailsList , CallbackGroceryListItemSelectAdapter, CallbackAddtoCart {


    View parentView;
    private static ViewPager viewpager;
    private int page;
    Bundle strtext;
    private String title;
    private String foodCategoryId;
    private String restId;
    private String foodCategoryName;
    FoodDetailsViewModel viewModel;
    Unbinder unbinder;
    boolean firstLoad;
    BreakfastFragAdapter breakfastFragAdapter;
    @BindView(R.id.rv_breakFast)
    RecyclerView recyclerView;
   // ProgressDialog progress;
    private List<RestFood> foodLists;
    @BindView(R.id.layout_foodList_fetchingData)
    RelativeLayout layout_fetchingData;
    @BindView(R.id.layout_foodList_notFound)
    ConstraintLayout layout_dataNotAvailable;
    @BindView(R.id.layout_buttom_sheet_item)
    CardView layout_buttom_sheet_item;
    @BindView(R.id.tv_item_count)
    TextView tv_item_count;
    @BindView(R.id.tv_item_price)
    TextView tv_item_price;
    @BindView(R.id.tvBtn_buttomSheetBtnContinue)
    CardView tvBtn_buttomSheetBtnContinue;

    private String seletedCartItemQuantity;
    private String seletedCartItemId;
    private AddToCardViewModel addToCardViewModel;
    private Integer quantity;
    private Integer totalPrice;
    private Integer price;
    private LoadingDialogHelper loadingDialogHelper;
    RestResponseCart responseCart;
    SessionManager sessionManager;
    HashMap<String, String> data = new HashMap<>();
    public RestourantFragments() {
        // Required empty public constructor
    }

    public static RestourantFragments newInstance(String page, String title) {
        RestourantFragments fragmentFirst = new RestourantFragments();
        Bundle args = new Bundle();
//        args.putString("0", page);
//        args.putString(PAGE_TYPE_BREAK_FAST, title);
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_break_fast, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, parentView);
        sessionManager = new SessionManager(getActivity());
        //Retriving Data
        data = sessionManager.getDetails();
        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_PROCESSING_REQUEST);
        initViewModel();
        strtext = this.getArguments();
        initViewModelForAddtocart();
        setFetchingDataLayout();
        if (strtext != null) {
            foodCategoryId = strtext.getString("id");
            foodCategoryName = strtext.getString("categoryName");
            restId = strtext.getString("restId");
            viewModel.getFoodList(getdata());
            initRecyclerBreakfastListView();
        }
        foodLists = new ArrayList<>();
        firstLoad = true;

        setOnClick();
        return parentView;
    }

    private void initRecyclerBreakfastListView() {

        breakfastFragAdapter = new BreakfastFragAdapter(getActivity(),getActivity());
        breakfastFragAdapter.setCallback(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(breakfastFragAdapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FoodDetailsViewModel.class);
        viewModel.setCallback(this);
    }

    @Override
    public void onSuccess(List<RestFood> foodList) {

        this.foodLists = foodList;
        if (foodList.size() != 0) {
           // progress.dismiss();
            setDataAvailableLayout();
            breakfastFragAdapter.clearFoodList();
            breakfastFragAdapter.addFoodList(foodList);
            breakfastFragAdapter.notifyDataSetChanged();
        } else {
            setNoDataLayout();
          //  progress.dismiss();
        }
    }

    @Override
    public void onErrorNodataFound(String message) {
      //  progress.dismiss();
        setNoDataLayout();
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private UserData getdata() {
        UserData data = new UserData();
        data.setFood_catid(foodCategoryId);
        data.setRest_id(restId);
        return data;
    }

    private void setNoDataLayout() {
        recyclerView.setVisibility(View.GONE);
        layout_dataNotAvailable.setVisibility(View.VISIBLE);
        layout_fetchingData.setVisibility(View.GONE);
    }

    private void setFetchingDataLayout() {
        recyclerView.setVisibility(View.GONE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.VISIBLE);
    }

    private void setDataAvailableLayout() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.GONE);
    }

    private void setOnClick() {
        tvBtn_buttomSheetBtnContinue.setOnClickListener(v -> {
            layout_buttom_sheet_item.setVisibility(View.GONE);
            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
            intent.putExtra(INTENT_STRING_ORDER_TYPE,ORDER_TYPE_RESTAURANT);
            intent.putExtra(INTENT_STRING_CART_DETAIL, (Serializable) responseCart);
            startActivity(intent);
        });

    }

    private void initViewModelForAddtocart() {
        addToCardViewModel = ViewModelProviders.of(this).get(AddToCardViewModel.class);
        addToCardViewModel.setCallbackAddTocart(this);
    }

    @Override
    public void onSuccess() {
        //   Toast.makeText(getActivity(),"Item added to cart successfully",Toast.LENGTH_SHORT).show();
        addToCardViewModel.getCartDetails(getuserdataForCartDetails());

    }

    @Override
    public void onSuccessCartDetails(RestResponseCart cartDetails) {
        loadingDialogHelper.dismiss();
        this.responseCart = cartDetails;
        layout_buttom_sheet_item.setVisibility(View.VISIBLE);
        DeliveryEverything.getAppSharedPreference().setItemQuantity(cartDetails.getSumcartCount().toString());
        DeliveryEverything.getAppSharedPreference().setItemPrice(cartDetails.getSumPrice().toString());
        bottomsheetdataPopulate();

    }


    @Override
    public void onNetworkError(String message) {
      //  progress.dismiss();
    }



    @Override
    public void onAddBttomPres(String itemQuantity, String productId, String productPrice) {
        this.seletedCartItemQuantity = itemQuantity;
        this.seletedCartItemId = productId;

        quantity = Integer.parseInt(itemQuantity);
        price = Integer.parseInt(productPrice);
        totalPrice = quantity * price;
        loadingDialogHelper.show(getActivity().getSupportFragmentManager(), "dialog_processingRequest_addtocart");
        addToCardViewModel.addToCart(getuserdataForAddtoCart());
    }

    private UserData getuserdataForAddtoCart() {
        UserData userData = new UserData();
        userData.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        userData.setpId(seletedCartItemId);
        userData.setRest_id(restId);
        userData.setPrice(totalPrice);
        userData.setQuantity(seletedCartItemQuantity);
        userData.setDev_key(DeliveryEverything.getAppSharedPreference().getDevKey());
        userData.setOrderType("restaurant");
        return userData;
    }


    private UserData getuserdataForCartDetails() {
        UserData data = new UserData();
        data.setUser_id(DeliveryEverything.getAppSharedPreference().getUserId());
        data.setOrderType("restaurant");
        return data;
    }

    private void bottomsheetdataPopulate(){
        try{
            String quant = DeliveryEverything.getAppSharedPreference().getItemQuantity();
            String price = DeliveryEverything.getAppSharedPreference().getItemPrice();
            if (quant.equals("1")) {
                tv_item_count.setText(quant + " items");
                tv_item_price.setText("₹ " + price + ".00");
            } else {
                tv_item_count.setText(quant + " items");
                tv_item_price.setText("₹ " + price + ".00");
            }
        }catch (Exception e){e.printStackTrace();}

    }

}
