package snd.orgn.foodnfine.bottomSheetFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.fragmentAdapter.SelectPackageAdapter;
import snd.orgn.foodnfine.callbacks.CallbackButtomSheetSelectPackage;
import snd.orgn.foodnfine.callbacks.CallbackSeletedItem;
import snd.orgn.foodnfine.callbacks.CallbackSelectedPackages;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;
import snd.orgn.foodnfine.view_model.FragmentViewModel.BottomSheetPackageListViewModel;

public class BottomSheetSelectPackageFragment extends BottomSheetDialogFragment implements CallbackButtomSheetSelectPackage,
        CallbackSelectedPackages {

    View rootView;

    @BindView(R.id.rv_recyclerViewPackageContain)
    RecyclerView rv_selectPakage;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.cv_done)
    CardView cv_done;
    private CallbackSeletedItem callbackSeletedItem;

    private SelectPackageAdapter adapter;
    BottomSheetPackageListViewModel viewModel;
    private List<PackageDetails> packageDetailsList;
    private List<String> selectedPackageList;

    public BottomSheetSelectPackageFragment() {
        // Required empty public constructor
    }


    public void setCallback(CallbackSeletedItem callback) {
        this.callbackSeletedItem = callback;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_bootm_sheet_select_package, container, false);
        ButterKnife.bind(this, rootView);
        // Inflate the layout for this fragment
        initViewModel();
        viewModel.getpackageList();
        initFields();
        setupOnclick();
        return rootView;
    }

    private void setupOnclick() {
        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });

        cv_done.setOnClickListener(v->{
            callbackSeletedItem.onItemsSelected(selectedPackageList);
            dismiss();
        });
    }

    private void initFields() {
        packageDetailsList = new ArrayList<>();
        selectedPackageList = new ArrayList<>();
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        rv_selectPakage.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new SelectPackageAdapter(getActivity(),getContext());
        adapter.setCallback(this);

        rv_selectPakage.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BottomSheetPackageListViewModel.class);
        viewModel.setCallback(this);
    }


    @Override
    public void onPackageDataListFetched(List<PackageDetails> packageDetailsList) {
        this.packageDetailsList = packageDetailsList;
        adapter.clearListData();
        adapter.addPackageList(packageDetailsList);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onNoDataFound() {

    }


    @Override
    public void onItemsSelected(List<String> selectedItems) {
        this.selectedPackageList = selectedItems;

    }
}
