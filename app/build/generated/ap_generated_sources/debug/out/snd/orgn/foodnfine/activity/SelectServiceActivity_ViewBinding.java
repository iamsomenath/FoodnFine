// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class SelectServiceActivity_ViewBinding implements Unbinder {
  private SelectServiceActivity target;

  @UiThread
  public SelectServiceActivity_ViewBinding(SelectServiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SelectServiceActivity_ViewBinding(SelectServiceActivity target, View source) {
    this.target = target;

    target.cvBtn_subscribe = Utils.findRequiredViewAsType(source, R.id.cvBtn_subscribe, "field 'cvBtn_subscribe'", CardView.class);
    target.tvBtn_dailyBooking = Utils.findRequiredViewAsType(source, R.id.tvBtn_dailyBooking, "field 'tvBtn_dailyBooking'", CardView.class);
    target.iv_selectService_back = Utils.findRequiredViewAsType(source, R.id.iv_selectService_back, "field 'iv_selectService_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectServiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cvBtn_subscribe = null;
    target.tvBtn_dailyBooking = null;
    target.iv_selectService_back = null;
  }
}
