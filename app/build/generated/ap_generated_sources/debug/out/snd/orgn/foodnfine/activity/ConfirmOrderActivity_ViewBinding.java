// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class ConfirmOrderActivity_ViewBinding implements Unbinder {
  private ConfirmOrderActivity target;

  @UiThread
  public ConfirmOrderActivity_ViewBinding(ConfirmOrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ConfirmOrderActivity_ViewBinding(ConfirmOrderActivity target, View source) {
    this.target = target;

    target.rv_createOrder = Utils.findRequiredViewAsType(source, R.id.rv_createOrder, "field 'rv_createOrder'", RecyclerView.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radioGroup, "field 'radioGroup'", RadioGroup.class);
    target.couponcode = Utils.findRequiredViewAsType(source, R.id.couponcode, "field 'couponcode'", EditText.class);
    target.applyCode = Utils.findRequiredViewAsType(source, R.id.applyCode, "field 'applyCode'", Button.class);
    target.cancelCode = Utils.findRequiredViewAsType(source, R.id.cancelCode, "field 'cancelCode'", Button.class);
    target.priceDetails = Utils.findRequiredViewAsType(source, R.id.priceDetails, "field 'priceDetails'", LinearLayout.class);
    target.offers = Utils.findRequiredViewAsType(source, R.id.offers, "field 'offers'", TextView.class);
    target.iv_confirmOrder_back = Utils.findRequiredViewAsType(source, R.id.iv_confirmOrder_back, "field 'iv_confirmOrder_back'", ImageView.class);
    target.viewDetails = Utils.findRequiredViewAsType(source, R.id.viewDetails, "field 'viewDetails'", LinearLayout.class);
    target.information = Utils.findRequiredViewAsType(source, R.id.information, "field 'information'", ImageView.class);
    target.tv_confirmOrder_type = Utils.findRequiredViewAsType(source, R.id.tv_confirmOrder_type, "field 'tv_confirmOrder_type'", TextView.class);
    target.tv_confirmOrder_totalPrice = Utils.findRequiredViewAsType(source, R.id.tv_confirmOrder_totalPrice, "field 'tv_confirmOrder_totalPrice'", TextView.class);
    target.tv_cofirmOrderDiscountPrice = Utils.findRequiredViewAsType(source, R.id.tv_cofirmOrderDiscountPrice, "field 'tv_cofirmOrderDiscountPrice'", TextView.class);
    target.tv_confirmOrder_delivery_address = Utils.findRequiredViewAsType(source, R.id.tv_confirmOrder_delivery_address, "field 'tv_confirmOrder_delivery_address'", EditText.class);
    target.et_ConfirmOrder_remark = Utils.findRequiredViewAsType(source, R.id.et_ConfirmOrder_remark, "field 'et_ConfirmOrder_remark'", EditText.class);
    target.tv_confirmOrder_item_price = Utils.findRequiredViewAsType(source, R.id.tv_confirmOrder_item_price, "field 'tv_confirmOrder_item_price'", TextView.class);
    target.tv_confirmOrder_item_count = Utils.findRequiredViewAsType(source, R.id.tv_confirmOrder_item_count, "field 'tv_confirmOrder_item_count'", TextView.class);
    target.ivBtn_confirmOrderChangeAddress = Utils.findRequiredViewAsType(source, R.id.ivBtn_confirmOrderChangeAddress, "field 'ivBtn_confirmOrderChangeAddress'", ImageView.class);
    target.tvBtn_ConfirmOrder_continue = Utils.findRequiredViewAsType(source, R.id.tvBtn_ConfirmOrder_continue, "field 'tvBtn_ConfirmOrder_continue'", CardView.class);
    target.rg_payment_option = Utils.findRequiredViewAsType(source, R.id.rg_payment_home, "field 'rg_payment_option'", RadioGroup.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", ScrollView.class);
    target.getAddress = Utils.findRequiredViewAsType(source, R.id.getAddress, "field 'getAddress'", ImageView.class);
    target.details_address = Utils.findRequiredViewAsType(source, R.id.details_address, "field 'details_address'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ConfirmOrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_createOrder = null;
    target.radioGroup = null;
    target.couponcode = null;
    target.applyCode = null;
    target.cancelCode = null;
    target.priceDetails = null;
    target.offers = null;
    target.iv_confirmOrder_back = null;
    target.viewDetails = null;
    target.information = null;
    target.tv_confirmOrder_type = null;
    target.tv_confirmOrder_totalPrice = null;
    target.tv_cofirmOrderDiscountPrice = null;
    target.tv_confirmOrder_delivery_address = null;
    target.et_ConfirmOrder_remark = null;
    target.tv_confirmOrder_item_price = null;
    target.tv_confirmOrder_item_count = null;
    target.ivBtn_confirmOrderChangeAddress = null;
    target.tvBtn_ConfirmOrder_continue = null;
    target.rg_payment_option = null;
    target.scrollView = null;
    target.getAddress = null;
    target.details_address = null;
  }
}
