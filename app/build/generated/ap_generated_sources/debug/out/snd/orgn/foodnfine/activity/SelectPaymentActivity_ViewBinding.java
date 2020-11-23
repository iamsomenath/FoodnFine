// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class SelectPaymentActivity_ViewBinding implements Unbinder {
  private SelectPaymentActivity target;

  @UiThread
  public SelectPaymentActivity_ViewBinding(SelectPaymentActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SelectPaymentActivity_ViewBinding(SelectPaymentActivity target, View source) {
    this.target = target;

    target.tv_item_total = Utils.findRequiredViewAsType(source, R.id.tv_item_total, "field 'tv_item_total'", TextView.class);
    target.iv_select_payment_back = Utils.findRequiredViewAsType(source, R.id.iv_select_payment_back, "field 'iv_select_payment_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectPaymentActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_item_total = null;
    target.iv_select_payment_back = null;
  }
}
