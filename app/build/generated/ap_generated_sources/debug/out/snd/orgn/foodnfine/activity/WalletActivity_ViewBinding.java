// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class WalletActivity_ViewBinding implements Unbinder {
  private WalletActivity target;

  @UiThread
  public WalletActivity_ViewBinding(WalletActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WalletActivity_ViewBinding(WalletActivity target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.sliding_tabs, "field 'tabLayout'", TabLayout.class);
    target.iv_wallet_back = Utils.findRequiredViewAsType(source, R.id.iv_wallet_back, "field 'iv_wallet_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WalletActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
    target.iv_wallet_back = null;
  }
}
