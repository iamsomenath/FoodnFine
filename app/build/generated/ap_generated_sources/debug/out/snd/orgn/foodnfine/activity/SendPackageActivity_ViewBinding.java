// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class SendPackageActivity_ViewBinding implements Unbinder {
  private SendPackageActivity target;

  @UiThread
  public SendPackageActivity_ViewBinding(SendPackageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SendPackageActivity_ViewBinding(SendPackageActivity target, View source) {
    this.target = target;

    target.iv_sendPackage_back = Utils.findRequiredViewAsType(source, R.id.iv_sendPackage_back, "field 'iv_sendPackage_back'", ImageView.class);
    target.iv_right_tick = Utils.findRequiredViewAsType(source, R.id.iv_right_tick, "field 'iv_right_tick'", ImageView.class);
    target.iv_right_tick_delivery = Utils.findRequiredViewAsType(source, R.id.iv_right_tick_delivery, "field 'iv_right_tick_delivery'", ImageView.class);
    target.iv_right_tick_package_contents = Utils.findRequiredViewAsType(source, R.id.iv_right_tick_package_contents, "field 'iv_right_tick_package_contents'", ImageView.class);
    target.tv_pickup_address = Utils.findRequiredViewAsType(source, R.id.tv_pickup_address, "field 'tv_pickup_address'", EditText.class);
    target.tv_delivery_address = Utils.findRequiredViewAsType(source, R.id.tv_delivery_address, "field 'tv_delivery_address'", EditText.class);
    target.tv_package_content = Utils.findRequiredViewAsType(source, R.id.tv_package_content, "field 'tv_package_content'", EditText.class);
    target.et_estimated_value_of_contents = Utils.findRequiredViewAsType(source, R.id.et_estimated_value_of_contents, "field 'et_estimated_value_of_contents'", EditText.class);
    target.et_any_instruction = Utils.findRequiredViewAsType(source, R.id.et_any_instruction, "field 'et_any_instruction'", EditText.class);
    target.cv_payNowBtn = Utils.findRequiredViewAsType(source, R.id.cv_payNowBtn, "field 'cv_payNowBtn'", CardView.class);
    target.tv_term_and_condition = Utils.findRequiredViewAsType(source, R.id.tv_term_and_condition, "field 'tv_term_and_condition'", TextView.class);
    target.tvBtn_pay_now = Utils.findRequiredViewAsType(source, R.id.tvBtn_pay_now, "field 'tvBtn_pay_now'", TextView.class);
    target.tv_grandTotalItem = Utils.findRequiredViewAsType(source, R.id.tv_grandTotalItem, "field 'tv_grandTotalItem'", TextView.class);
    target.tv_charges_amount = Utils.findRequiredViewAsType(source, R.id.tv_charges_amount, "field 'tv_charges_amount'", TextView.class);
    target.tv_discount_price = Utils.findRequiredViewAsType(source, R.id.tv_discount_price, "field 'tv_discount_price'", TextView.class);
    target.tv_actual_price = Utils.findRequiredViewAsType(source, R.id.tv_actual_price, "field 'tv_actual_price'", TextView.class);
    target.layout_invoice = Utils.findRequiredViewAsType(source, R.id.layout_invoice, "field 'layout_invoice'", LinearLayout.class);
    target.layout_price = Utils.findRequiredViewAsType(source, R.id.layout_price, "field 'layout_price'", LinearLayout.class);
    target.rg_payment_option = Utils.findRequiredViewAsType(source, R.id.rg_payment_home, "field 'rg_payment_option'", RadioGroup.class);
    target.layout_pickup_address = Utils.findRequiredViewAsType(source, R.id.layout_pickup_address, "field 'layout_pickup_address'", LinearLayout.class);
    target.layout_delivery_address = Utils.findRequiredViewAsType(source, R.id.layout_delivery_address, "field 'layout_delivery_address'", LinearLayout.class);
    target.layout_package_contents = Utils.findRequiredViewAsType(source, R.id.layout_package_contents, "field 'layout_package_contents'", LinearLayout.class);
    target.getDeliveryAddress = Utils.findRequiredViewAsType(source, R.id.getDeliveryAddress, "field 'getDeliveryAddress'", ImageView.class);
    target.getPickUpAddress = Utils.findRequiredViewAsType(source, R.id.getPickUpAddress, "field 'getPickUpAddress'", ImageView.class);
    target.details_delivery_address = Utils.findRequiredViewAsType(source, R.id.details_delivery_address, "field 'details_delivery_address'", EditText.class);
    target.details_pickup_address = Utils.findRequiredViewAsType(source, R.id.details_pickup_address, "field 'details_pickup_address'", EditText.class);
    target.delivery_layout = Utils.findRequiredViewAsType(source, R.id.delivery_layout, "field 'delivery_layout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SendPackageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_sendPackage_back = null;
    target.iv_right_tick = null;
    target.iv_right_tick_delivery = null;
    target.iv_right_tick_package_contents = null;
    target.tv_pickup_address = null;
    target.tv_delivery_address = null;
    target.tv_package_content = null;
    target.et_estimated_value_of_contents = null;
    target.et_any_instruction = null;
    target.cv_payNowBtn = null;
    target.tv_term_and_condition = null;
    target.tvBtn_pay_now = null;
    target.tv_grandTotalItem = null;
    target.tv_charges_amount = null;
    target.tv_discount_price = null;
    target.tv_actual_price = null;
    target.layout_invoice = null;
    target.layout_price = null;
    target.rg_payment_option = null;
    target.layout_pickup_address = null;
    target.layout_delivery_address = null;
    target.layout_package_contents = null;
    target.getDeliveryAddress = null;
    target.getPickUpAddress = null;
    target.details_delivery_address = null;
    target.details_pickup_address = null;
    target.delivery_layout = null;
  }
}
