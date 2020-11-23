// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.bottomSheetFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class BottomSheetSelectItemFragment_ViewBinding implements Unbinder {
  private BottomSheetSelectItemFragment target;

  @UiThread
  public BottomSheetSelectItemFragment_ViewBinding(BottomSheetSelectItemFragment target,
      View source) {
    this.target = target;

    target.tv_cart_clear = Utils.findRequiredViewAsType(source, R.id.tv_cart_clear, "field 'tv_cart_clear'", TextView.class);
    target.tvBtn_buttomSheetBtnStay = Utils.findRequiredViewAsType(source, R.id.tvBtn_buttomSheetBtnStay, "field 'tvBtn_buttomSheetBtnStay'", CardView.class);
    target.iv_cross = Utils.findRequiredViewAsType(source, R.id.iv_cross, "field 'iv_cross'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BottomSheetSelectItemFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_cart_clear = null;
    target.tvBtn_buttomSheetBtnStay = null;
    target.iv_cross = null;
  }
}
