// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class OtherStoreActivity_ViewBinding implements Unbinder {
  private OtherStoreActivity target;

  @UiThread
  public OtherStoreActivity_ViewBinding(OtherStoreActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OtherStoreActivity_ViewBinding(OtherStoreActivity target, View source) {
    this.target = target;

    target.iv_otherStore_back = Utils.findRequiredViewAsType(source, R.id.iv_otherStore_back, "field 'iv_otherStore_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OtherStoreActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_otherStore_back = null;
  }
}
