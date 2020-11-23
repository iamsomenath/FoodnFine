// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class RestrurentDetailsActivity_ViewBinding implements Unbinder {
  private RestrurentDetailsActivity target;

  @UiThread
  public RestrurentDetailsActivity_ViewBinding(RestrurentDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RestrurentDetailsActivity_ViewBinding(RestrurentDetailsActivity target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.sliding_tabs_restaurantDetails, "field 'tabLayout'", TabLayout.class);
    target.iv_restaurentDetails_back = Utils.findRequiredViewAsType(source, R.id.iv_restaurentDetails_back, "field 'iv_restaurentDetails_back'", ImageView.class);
    target.iv_restaurentDetails_addItem = Utils.findRequiredViewAsType(source, R.id.iv_restaurentDetails_addItem, "field 'iv_restaurentDetails_addItem'", ImageView.class);
    target.content_frame = Utils.findRequiredViewAsType(source, R.id.content_frame, "field 'content_frame'", FrameLayout.class);
    target.tv_restaurantName = Utils.findRequiredViewAsType(source, R.id.tv_restaurantName, "field 'tv_restaurantName'", TextView.class);
    target.tv_restaurantAddress = Utils.findRequiredViewAsType(source, R.id.tv_restaurantAddress, "field 'tv_restaurantAddress'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RestrurentDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
    target.iv_restaurentDetails_back = null;
    target.iv_restaurentDetails_addItem = null;
    target.content_frame = null;
    target.tv_restaurantName = null;
    target.tv_restaurantAddress = null;
  }
}
