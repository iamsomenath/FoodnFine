// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.card.MaterialCardView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class SelectLocationMapActivity_ViewBinding implements Unbinder {
  private SelectLocationMapActivity target;

  @UiThread
  public SelectLocationMapActivity_ViewBinding(SelectLocationMapActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SelectLocationMapActivity_ViewBinding(SelectLocationMapActivity target, View source) {
    this.target = target;

    target.materialSearchBar = Utils.findRequiredViewAsType(source, R.id.searchBar_selectLocationMapActivity, "field 'materialSearchBar'", MaterialSearchBar.class);
    target.cardBtn_selectLocation = Utils.findRequiredViewAsType(source, R.id.cardBtn_selectLocationMapActivity_selectLocation, "field 'cardBtn_selectLocation'", MaterialCardView.class);
    target.iv_selectLocation_back = Utils.findRequiredViewAsType(source, R.id.iv_selectLocation_back, "field 'iv_selectLocation_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectLocationMapActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.materialSearchBar = null;
    target.cardBtn_selectLocation = null;
    target.iv_selectLocation_back = null;
  }
}
