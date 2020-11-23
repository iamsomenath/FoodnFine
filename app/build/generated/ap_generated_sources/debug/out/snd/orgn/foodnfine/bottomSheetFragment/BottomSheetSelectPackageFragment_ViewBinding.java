// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.bottomSheetFragment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class BottomSheetSelectPackageFragment_ViewBinding implements Unbinder {
  private BottomSheetSelectPackageFragment target;

  @UiThread
  public BottomSheetSelectPackageFragment_ViewBinding(BottomSheetSelectPackageFragment target,
      View source) {
    this.target = target;

    target.rv_selectPakage = Utils.findRequiredViewAsType(source, R.id.rv_recyclerViewPackageContain, "field 'rv_selectPakage'", RecyclerView.class);
    target.tv_cancel = Utils.findRequiredViewAsType(source, R.id.tv_cancel, "field 'tv_cancel'", TextView.class);
    target.cv_done = Utils.findRequiredViewAsType(source, R.id.cv_done, "field 'cv_done'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BottomSheetSelectPackageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_selectPakage = null;
    target.tv_cancel = null;
    target.cv_done = null;
  }
}
