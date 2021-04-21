package snd.orgn.foodnfine.bottomSheetFragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.callbacks.CallbackAllDeleteCartItem;
import snd.orgn.foodnfine.callbacks.CallbackDeleteCartResponse;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.FragmentViewModel.DeleteAllCartItemViewModel;

public class BottomSheetSelectItemFragment extends BottomSheetDialogFragment implements CallbackAllDeleteCartItem {

    View rootView;
    @BindView(R.id.tv_cart_clear)
    TextView tv_cart_clear;
    @BindView(R.id.tvBtn_buttomSheetBtnStay)
    CardView tvBtn_buttomSheetBtnStay;
    @BindView(R.id.iv_cross)
    ImageView iv_cross;
    DeleteAllCartItemViewModel viewModel;
    private String itemCount = "";
    private String itemPrice = "";
    LoadingDialog loadingDialog;
    private CallbackDeleteCartResponse callbackDeleteCartSuccesRes;
    public BottomSheetSelectItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_buttom_sheet_clear_cart, container, false);
        ButterKnife.bind(this, rootView);
        initViewModel();
        // Inflate the layout for this fragment

        initFields();
        setupOnclick();
        return rootView;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DeleteAllCartItemViewModel.class);
        viewModel.setCallback(this);
    }

    public void setCallback(CallbackDeleteCartResponse callback) {
        this.callbackDeleteCartSuccesRes = callback;
    }

    private void setupOnclick() {

        tvBtn_buttomSheetBtnStay.setOnClickListener(v -> {
            dismiss();
        });
        iv_cross.setOnClickListener(v -> {
            dismiss();
        });
        tv_cart_clear.setOnClickListener(v -> {
            loadingDialog.showDialog();
            viewModel.deleteAllCartItem();
        });
    }

    private void initFields() {
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public void onSuccess() {
        loadingDialog.hideDialog();
        Toast.makeText(getContext(), "Your all cart items removed successfully", Toast.LENGTH_SHORT).show();
        FoodnFine.getAppSharedPreference().setItemQuantity("");
        callbackDeleteCartSuccesRes.onSucessDataDelete();
        dismiss();
    }

    @Override
    public void onError(String message) {
        loadingDialog.hideDialog();
    }

    @Override
    public void onNetworkError(String message) {
        loadingDialog.hideDialog();
    }

}
