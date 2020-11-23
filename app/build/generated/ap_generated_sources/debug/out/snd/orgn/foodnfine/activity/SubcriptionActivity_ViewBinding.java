// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class SubcriptionActivity_ViewBinding implements Unbinder {
  private SubcriptionActivity target;

  @UiThread
  public SubcriptionActivity_ViewBinding(SubcriptionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SubcriptionActivity_ViewBinding(SubcriptionActivity target, View source) {
    this.target = target;

    target.iv_subcription_back = Utils.findRequiredViewAsType(source, R.id.iv_subcription_back, "field 'iv_subcription_back'", ImageView.class);
    target.selected_type_category = Utils.findRequiredViewAsType(source, R.id.selected_type_category, "field 'selected_type_category'", Spinner.class);
    target.tv_subcription_charges = Utils.findRequiredViewAsType(source, R.id.tv_subcription_charges, "field 'tv_subcription_charges'", TextView.class);
    target.cvBtn_subscribe = Utils.findRequiredViewAsType(source, R.id.btn_subcriptionConfirm, "field 'cvBtn_subscribe'", CardView.class);
    target.rg_payment_subcription = Utils.findRequiredViewAsType(source, R.id.rg_payment_subcription, "field 'rg_payment_subcription'", RadioGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubcriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_subcription_back = null;
    target.selected_type_category = null;
    target.tv_subcription_charges = null;
    target.cvBtn_subscribe = null;
    target.rg_payment_subcription = null;
  }
}
