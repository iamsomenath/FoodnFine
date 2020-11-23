// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class GroceryFragments_ViewBinding implements Unbinder {
  private GroceryFragments target;

  @UiThread
  public GroceryFragments_ViewBinding(GroceryFragments target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_desserts, "field 'recyclerView'", RecyclerView.class);
    target.layout_fetchingData = Utils.findRequiredViewAsType(source, R.id.layout_foodList_fetchingData, "field 'layout_fetchingData'", RelativeLayout.class);
    target.layout_dataNotAvailable = Utils.findRequiredViewAsType(source, R.id.layout_groceryList_notFound, "field 'layout_dataNotAvailable'", ConstraintLayout.class);
    target.layout_buttom_sheet_item = Utils.findRequiredViewAsType(source, R.id.layout_buttom_sheet_item, "field 'layout_buttom_sheet_item'", CardView.class);
    target.tv_item_count = Utils.findRequiredViewAsType(source, R.id.tv_item_count, "field 'tv_item_count'", TextView.class);
    target.tv_item_price = Utils.findRequiredViewAsType(source, R.id.tv_item_price, "field 'tv_item_price'", TextView.class);
    target.tvBtn_buttomSheetBtnContinue = Utils.findRequiredViewAsType(source, R.id.tvBtn_buttomSheetBtnContinue, "field 'tvBtn_buttomSheetBtnContinue'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroceryFragments target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.layout_fetchingData = null;
    target.layout_dataNotAvailable = null;
    target.layout_buttom_sheet_item = null;
    target.tv_item_count = null;
    target.tv_item_price = null;
    target.tvBtn_buttomSheetBtnContinue = null;
  }
}
