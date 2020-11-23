package snd.orgn.foodnfine.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import snd.orgn.foodnfine.helper.dailog.LoadingDialogHelper;

import static snd.orgn.foodnfine.constant.AppConstants.KEY_BUNDLE_LOADING_TYPE;

public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialogHelper loadingDialog;
    private Bundle loadingType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initFields();

    public abstract void setupOnClick();

    public static void hideSoftKeyboard(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if(focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public void setupUI(View view, AppCompatActivity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof TextInputEditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,activity);
            }
        }
    }

    public LoadingDialogHelper getLoadingDialog(String lt) {
        loadingDialog=new LoadingDialogHelper();
        this.loadingType=new Bundle();
        this.loadingType.putString(KEY_BUNDLE_LOADING_TYPE,lt);
        loadingDialog.setArguments(loadingType);

        return loadingDialog;
    }
}
