// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class LocationOnActivity_ViewBinding implements Unbinder {
  private LocationOnActivity target;

  @UiThread
  public LocationOnActivity_ViewBinding(LocationOnActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LocationOnActivity_ViewBinding(LocationOnActivity target, View source) {
    this.target = target;

    target.cv_current_location = Utils.findRequiredViewAsType(source, R.id.cv_current_location, "field 'cv_current_location'", CardView.class);
    target.cv_someOther_location = Utils.findRequiredViewAsType(source, R.id.cv_someOther_location, "field 'cv_someOther_location'", CardView.class);
    target.layout_backIcon = Utils.findRequiredViewAsType(source, R.id.layout_backIcon, "field 'layout_backIcon'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LocationOnActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cv_current_location = null;
    target.cv_someOther_location = null;
    target.layout_backIcon = null;
  }
}
