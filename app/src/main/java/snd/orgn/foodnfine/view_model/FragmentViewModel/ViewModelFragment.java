package snd.orgn.foodnfine.view_model.FragmentViewModel;


import android.os.Bundle;


import androidx.annotation.Nullable;
import snd.orgn.foodnfine.base.BaseFragment;
import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;

import static snd.orgn.foodnfine.constant.AppConstants.KEY_BUNDLE_LOADING_TYPE;


public  abstract class ViewModelFragment extends BaseFragment {

    private static final String EXTRA_VIEW_MODEL_STATE = "viewModelState";

    private ViewModel viewModel;
    private LoadingDialogHelper loadingDialogHelper;
    private Bundle loadingType;

    @Nullable
    protected abstract ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel.State savedViewModelState = null;
        if (savedInstanceState != null) {
            savedViewModelState = savedInstanceState.getParcelable(EXTRA_VIEW_MODEL_STATE);
        }
        viewModel = createViewModel(savedViewModelState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.onStart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            outState.putParcelable(EXTRA_VIEW_MODEL_STATE, viewModel.getInstanceState());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModel != null) {
            viewModel.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewModel != null) {
            viewModel.onPause();
        }
    }

    public LoadingDialogHelper getLoadingDialog(String lt) {
        loadingDialogHelper=new LoadingDialogHelper() ;
        this.loadingType=new Bundle();
        this.loadingType.putString(KEY_BUNDLE_LOADING_TYPE,lt);
        loadingDialogHelper.setArguments(loadingType);

        return loadingDialogHelper;

    }
}
