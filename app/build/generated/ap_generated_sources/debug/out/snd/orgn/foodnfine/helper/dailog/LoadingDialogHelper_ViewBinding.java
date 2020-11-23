// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.helper.dailog;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class LoadingDialogHelper_ViewBinding implements Unbinder {
  private LoadingDialogHelper target;

  @UiThread
  public LoadingDialogHelper_ViewBinding(LoadingDialogHelper target, View source) {
    this.target = target;

    target.mainContent = Utils.findRequiredViewAsType(source, R.id.tv_loadingDialog_mainContent, "field 'mainContent'", TextView.class);
    target.iv_loaderImage = Utils.findRequiredViewAsType(source, R.id.iv_loadingDialog_image, "field 'iv_loaderImage'", RoundedImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoadingDialogHelper target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainContent = null;
    target.iv_loaderImage = null;
  }
}
