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

public class GroceryDetailsActivity_ViewBinding implements Unbinder {
  private GroceryDetailsActivity target;

  @UiThread
  public GroceryDetailsActivity_ViewBinding(GroceryDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroceryDetailsActivity_ViewBinding(GroceryDetailsActivity target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.sliding_tabs_groceryDetails, "field 'tabLayout'", TabLayout.class);
    target.iv_groceryDetails_back = Utils.findRequiredViewAsType(source, R.id.iv_groceryDetails_back, "field 'iv_groceryDetails_back'", ImageView.class);
    target.content_frame = Utils.findRequiredViewAsType(source, R.id.content_frame, "field 'content_frame'", FrameLayout.class);
    target.tv_groceryName = Utils.findRequiredViewAsType(source, R.id.tv_groceryName, "field 'tv_groceryName'", TextView.class);
    target.tv_groceryAddress = Utils.findRequiredViewAsType(source, R.id.tv_groceryAddress, "field 'tv_groceryAddress'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroceryDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
    target.iv_groceryDetails_back = null;
    target.content_frame = null;
    target.tv_groceryName = null;
    target.tv_groceryAddress = null;
  }
}
