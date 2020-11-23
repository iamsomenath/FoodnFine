// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class AccountDetailsActivity_ViewBinding implements Unbinder {
  private AccountDetailsActivity target;

  @UiThread
  public AccountDetailsActivity_ViewBinding(AccountDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountDetailsActivity_ViewBinding(AccountDetailsActivity target, View source) {
    this.target = target;

    target.iv_accountDetails_back = Utils.findRequiredViewAsType(source, R.id.iv_accountDetails_back, "field 'iv_accountDetails_back'", ImageView.class);
    target.layout_gotoCompleteSetup = Utils.findRequiredViewAsType(source, R.id.layout_gotoCompleteSetup, "field 'layout_gotoCompleteSetup'", LinearLayout.class);
    target.layout_saveAddress = Utils.findRequiredViewAsType(source, R.id.layout_saveAddress, "field 'layout_saveAddress'", ConstraintLayout.class);
    target.tv_userMobileNo = Utils.findRequiredViewAsType(source, R.id.tv_userMobileNo, "field 'tv_userMobileNo'", TextView.class);
    target.layout_logout = Utils.findRequiredViewAsType(source, R.id.layout_logout, "field 'layout_logout'", LinearLayout.class);
    target.layout_subs = Utils.findRequiredViewAsType(source, R.id.layout_subs, "field 'layout_subs'", LinearLayout.class);
    target.accountDetails_about = Utils.findRequiredViewAsType(source, R.id.accountDetails_about, "field 'accountDetails_about'", ConstraintLayout.class);
    target.orders_layout = Utils.findRequiredViewAsType(source, R.id.orders_layout, "field 'orders_layout'", ConstraintLayout.class);
    target.layout_notification = Utils.findRequiredViewAsType(source, R.id.layout_notification, "field 'layout_notification'", LinearLayout.class);
    target.layout_contactus = Utils.findRequiredViewAsType(source, R.id.layout_contactus, "field 'layout_contactus'", ConstraintLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_accountDetails_back = null;
    target.layout_gotoCompleteSetup = null;
    target.layout_saveAddress = null;
    target.tv_userMobileNo = null;
    target.layout_logout = null;
    target.layout_subs = null;
    target.accountDetails_about = null;
    target.orders_layout = null;
    target.layout_notification = null;
    target.layout_contactus = null;
  }
}
