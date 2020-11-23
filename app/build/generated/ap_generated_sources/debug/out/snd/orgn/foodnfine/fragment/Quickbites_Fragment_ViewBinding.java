// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class Quickbites_Fragment_ViewBinding implements Unbinder {
  private Quickbites_Fragment target;

  @UiThread
  public Quickbites_Fragment_ViewBinding(Quickbites_Fragment target, View source) {
    this.target = target;

    target.rv_recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_recyclerView, "field 'rv_recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Quickbites_Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_recyclerView = null;
  }
}
