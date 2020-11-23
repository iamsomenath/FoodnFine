// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class GroceryListActivity_ViewBinding implements Unbinder {
  private GroceryListActivity target;

  @UiThread
  public GroceryListActivity_ViewBinding(GroceryListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroceryListActivity_ViewBinding(GroceryListActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_recyclerViewGrocery, "field 'recyclerView'", RecyclerView.class);
    target.iv_restaurant_back = Utils.findRequiredViewAsType(source, R.id.iv_restaurant_back, "field 'iv_restaurant_back'", ImageView.class);
    target.layout_fetchingData = Utils.findRequiredViewAsType(source, R.id.layout_grocery_fetchingData, "field 'layout_fetchingData'", RelativeLayout.class);
    target.layout_dataAvailable = Utils.findRequiredViewAsType(source, R.id.layout_dataAvailable, "field 'layout_dataAvailable'", LinearLayout.class);
    target.layout_dataNotAvailable = Utils.findRequiredViewAsType(source, R.id.layout_restaurent_notFound, "field 'layout_dataNotAvailable'", ConstraintLayout.class);
    target.tv_numberOfStore = Utils.findRequiredViewAsType(source, R.id.tv_numberOfStore, "field 'tv_numberOfStore'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroceryListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.iv_restaurant_back = null;
    target.layout_fetchingData = null;
    target.layout_dataAvailable = null;
    target.layout_dataNotAvailable = null;
    target.tv_numberOfStore = null;
  }
}
