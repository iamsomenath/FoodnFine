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

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.ConfirmOrderActivity;
import snd.orgn.foodnfine.adapter.fragmentAdapter.GroceryFragAdpter;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.callbacks.CallbackAddtoCart;
import snd.orgn.foodnfine.callbacks.CallbackAllProductDetailsList;
import snd.orgn.foodnfine.callbacks.CallbackGroceryListItemSelectAdapter;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;
import snd.orgn.foodnfine.model.data_item.AllGroceryProduct;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.response.RestResponseCart;
import snd.orgn.foodnfine.view_model.FragmentViewModel.AddToCardViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.GroceryAllProductDetailsViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.ViewModel;
import snd.orgn.foodnfine.view_model.FragmentViewModel.ViewModelFragment;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_CART_DETAIL;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ORDER_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_PROCESSING_REQUEST;
import static snd.orgn.foodnfine.constant.AppConstants.ORDER_TYPE_GROCERY;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_DESSERTS;

/**
 * create an instance of this fragment.
 */
public class GroceryFragments extends ViewModelFragment implements CallbackAllProductDetailsList, CallbackGroceryListItemSelectAdapter, CallbackAddtoCart {

    View parentView;
    @BindView(R.id.rv_desserts)
    RecyclerView recyclerView;

    Bundle strtext;

    private String groceryCategoryId;
    private String restId;
    GroceryAllProductDetailsViewModel viewModel;
    RestResponseCart responseCart;

    GroceryFragAdpter groceryFragAdpter;
   // ProgressDialog progress;
    private List<AllGroceryProduct> allGroceryProducts;
    @BindView(R.id.layout_foodList_fetchingData)
    RelativeLayout layout_fetchingData;
    @BindView(R.id.layout_groceryList_notFound)
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

    public GroceryFragments() {
        // Required empty public constructor
    }


    public static GroceryFragments newInstance(String page, String title) {
        GroceryFragments thirdFragment = new GroceryFragments();
        Bundle args = new Bundle();
//        args.putString("2", page);
//        args.putString(PAGE_TYPE_DESSERTS, title);
        thirdFragment.setArguments(args);
        return thirdFragment;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //private static ViewPager viewpager;
        int page = getArguments().getInt("2", 0);
        String title = getArguments().getString(PAGE_TYPE_DESSERTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_desserts, container, false);
        ButterKnife.bind(this, parentView);
        SessionManager sessionManager = new SessionManager(getActivity());
        //Retriving Data
        HashMap<String, String> data = sessionManager.getDetails();
        loadingDialogHelper = getLoadingDialog(LOADING_DIALOG_PROCESSING_REQUEST);

        initViewModel();
        initViewModelForAddtocart();
        strtext = this.getArguments();

        setFetchingDataLayout();
        if (strtext != null) {
            groceryCategoryId = strtext.getString("id");
            String groceryCategoryName = strtext.getString("categoryName");
            restId = strtext.getString("restId");
            viewModel.getAllProductList(getdata());
            initRecyclerBreakfastListView();
        }
        allGroceryProducts = new ArrayList<>();
        responseCart = new RestResponseCart();

        setOnClick();
        return parentView;
    }

    private void setOnClick() {
        tvBtn_buttomSheetBtnContinue.setOnClickListener(v -> {
            layout_buttom_sheet_item.setVisibility(View.GONE);
            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
            intent.putExtra(INTENT_STRING_ORDER_TYPE,ORDER_TYPE_GROCERY);
            intent.putExtra(INTENT_STRING_CART_DETAIL, (Serializable) responseCart);
            startActivity(intent);
        });
    }

    private void initRecyclerBreakfastListView() {

        groceryFragAdpter = new GroceryFragAdpter(getActivity(), getActivity());
        groceryFragAdpter.setCallback(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryFragAdpter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroceryAllProductDetailsViewModel.class);
        viewModel.setCallback(this);
    }

    private void initViewModelForAddtocart() {
        addToCardViewModel = ViewModelProviders.of(this).get(AddToCardViewModel.class);
        addToCardViewModel.setCallbackAddTocart(this);
    }

    @Override
    public void onSuccess(List<AllGroceryProduct> allGroceryProductList) {
        this.allGroceryProducts = allGroceryProductList;
        if (allGroceryProductList.size() != 0) {
            setDataAvailableLayout();
            groceryFragAdpter.clearProductList();
            groceryFragAdpter.addAllGroceryProduct(allGroceryProductList);
            groceryFragAdpter.notifyDataSetChanged();
        } else {
            setNoDataLayout();
        }
    }

    @Override
    public void onErrorNoDataFound(String message) {
      //  progress.dismiss();
        setNoDataLayout();
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
        FoodnFine.getAppSharedPreference().setItemQuantity(cartDetails.getSumcartCount().toString());
        FoodnFine.getAppSharedPreference().setItemPrice(cartDetails.getSumPrice().toString());
        bottomsheetdataPopulate();
    }

    @Override
    public void onError(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Something went wrong!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onNetworkError(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "It seems your device don't have or no internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private UserData getdata() {
        UserData data = new UserData();
        data.setCatId(groceryCategoryId);
        return data;
    }

    public void setNoDataLayout() {
        recyclerView.setVisibility(View.GONE);
        layout_dataNotAvailable.setVisibility(View.VISIBLE);
        layout_fetchingData.setVisibility(View.GONE);
    }

    public void setFetchingDataLayout() {
        recyclerView.setVisibility(View.GONE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.VISIBLE);
    }

    public void setDataAvailableLayout() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_dataNotAvailable.setVisibility(View.GONE);
        layout_fetchingData.setVisibility(View.GONE);
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
//        DeliveryEverything.getAppSharedPreference().setItemQuantity(itemQuantity);
//        DeliveryEverything.getAppSharedPreference().setItemPrice(itemPrice);
//        showBottomSheet();
    }

    private UserData getuserdataForAddtoCart() {
        UserData userData = new UserData();
        userData.setUser_id(FoodnFine.getAppSharedPreference().getUserId());
        userData.setpId(seletedCartItemId);
        userData.setPrice(totalPrice);
        userData.setRest_id(restId);
        userData.setQuantity(seletedCartItemQuantity);
        userData.setDev_key(FoodnFine.getAppSharedPreference().getDevKey());
        userData.setOrderType("grocery");
        return userData;
    }

    private UserData getuserdataForCartDetails() {
        UserData data = new UserData();
        data.setUser_id(FoodnFine.getAppSharedPreference().getUserId());
        data.setOrderType("grocery");
        return data;
    }

    private void bottomsheetdataPopulate(){
        try{
            String quant = FoodnFine.getAppSharedPreference().getItemQuantity();
            String price = FoodnFine.getAppSharedPreference().getItemPrice();
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
