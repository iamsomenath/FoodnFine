package snd.orgn.foodnfine.helper.dailog;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;

import static snd.orgn.foodnfine.constant.AppConstants.KEY_BUNDLE_LOADING_TYPE;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_FETCHING_DATA;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_LOGGING_IN;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_MESSAGE_FETCHING_DATA;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_MESSAGE_LOGGING_IN;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_MESSAGE_PROCESSING_REQUEST;
import static snd.orgn.foodnfine.constant.AppConstants.LOADING_DIALOG_PROCESSING_REQUEST;


public class LoadingDialogHelper extends DialogFragment {

    @BindView(R.id.tv_loadingDialog_mainContent)
    TextView mainContent;
    @BindView(R.id.iv_loadingDialog_image)
    RoundedImageView iv_loaderImage;

    public LoadingDialogHelper() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.layout_loading_dialog, container, false);
        ButterKnife.bind(this, parentView);
        initFields();
        return parentView;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void initFields(){
        setType(getArguments().getString(KEY_BUNDLE_LOADING_TYPE));
    }

    private void setImage(Drawable image) {
        iv_loaderImage.setImageDrawable(image);
    }

    private void setMessage(String message) {
        mainContent.setText(message);
    }

    private void setType(String type) {
        switch (type){
            case LOADING_DIALOG_FETCHING_DATA:
                setImage(getResources().getDrawable(R.drawable.ic_processing));
                setMessage(LOADING_DIALOG_MESSAGE_FETCHING_DATA);
                break;

            case LOADING_DIALOG_PROCESSING_REQUEST:
                setImage(getResources().getDrawable(R.drawable.ic_processing));
                setMessage(LOADING_DIALOG_MESSAGE_PROCESSING_REQUEST);
                break;

            case LOADING_DIALOG_LOGGING_IN:
                setImage(getResources().getDrawable(R.drawable.loading));
                setMessage(LOADING_DIALOG_MESSAGE_LOGGING_IN);
                break;
        }
    }
}
