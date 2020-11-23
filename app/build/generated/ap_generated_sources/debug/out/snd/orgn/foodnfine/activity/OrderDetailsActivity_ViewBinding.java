// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class OrderDetailsActivity_ViewBinding implements Unbinder {
  private OrderDetailsActivity target;

  @UiThread
  public OrderDetailsActivity_ViewBinding(OrderDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetailsActivity_ViewBinding(OrderDetailsActivity target, View source) {
    this.target = target;

    target.iv_orderDetails_back = Utils.findRequiredViewAsType(source, R.id.iv_orderDetails_back, "field 'iv_orderDetails_back'", ImageView.class);
    target.order_time = Utils.findRequiredViewAsType(source, R.id.order_time, "field 'order_time'", TextView.class);
    target.order_id = Utils.findRequiredViewAsType(source, R.id.order_id, "field 'order_id'", TextView.class);
    target.shop_name = Utils.findRequiredViewAsType(source, R.id.shop_name, "field 'shop_name'", TextView.class);
    target.shop_addr = Utils.findRequiredViewAsType(source, R.id.shop_addr, "field 'shop_addr'", TextView.class);
    target.user_name = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'user_name'", TextView.class);
    target.user_addr = Utils.findRequiredViewAsType(source, R.id.user_addr, "field 'user_addr'", TextView.class);
    target.items = Utils.findRequiredViewAsType(source, R.id.items, "field 'items'", TextView.class);
    target.fixedcharge = Utils.findRequiredViewAsType(source, R.id.fixedcharge, "field 'fixedcharge'", TextView.class);
    target.discountcharge = Utils.findRequiredViewAsType(source, R.id.discountcharge, "field 'discountcharge'", TextView.class);
    target.cancellationcharge = Utils.findRequiredViewAsType(source, R.id.cancellationcharge, "field 'cancellationcharge'", TextView.class);
    target.deliverycharge = Utils.findRequiredViewAsType(source, R.id.deliverycharge, "field 'deliverycharge'", TextView.class);
    target.foodcharge = Utils.findRequiredViewAsType(source, R.id.foodcharge, "field 'foodcharge'", TextView.class);
    target.total = Utils.findRequiredViewAsType(source, R.id.total, "field 'total'", TextView.class);
    target.tv_orderList_deliveryAddress = Utils.findRequiredViewAsType(source, R.id.tv_orderList_deliveryAddress, "field 'tv_orderList_deliveryAddress'", TextView.class);
    target.orderDetails = Utils.findRequiredViewAsType(source, R.id.orderDetails, "field 'orderDetails'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_orderDetails_back = null;
    target.order_time = null;
    target.order_id = null;
    target.shop_name = null;
    target.shop_addr = null;
    target.user_name = null;
    target.user_addr = null;
    target.items = null;
    target.fixedcharge = null;
    target.discountcharge = null;
    target.cancellationcharge = null;
    target.deliverycharge = null;
    target.foodcharge = null;
    target.total = null;
    target.tv_orderList_deliveryAddress = null;
    target.orderDetails = null;
  }
}
