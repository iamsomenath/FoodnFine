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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.smarteist.autoimageslider.SliderView;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class DasboardActivity_ViewBinding implements Unbinder {
  private DasboardActivity target;

  @UiThread
  public DasboardActivity_ViewBinding(DasboardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DasboardActivity_ViewBinding(DasboardActivity target, View source) {
    this.target = target;

    target.imageSlider = Utils.findRequiredViewAsType(source, R.id.imageSlider, "field 'imageSlider'", SliderView.class);
    target.iv_dashboard_location = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_location, "field 'iv_dashboard_location'", ImageView.class);
    target.iv_dashboard_userProfile = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_userProfile, "field 'iv_dashboard_userProfile'", ImageView.class);
    target.tv_dashboard_address = Utils.findRequiredViewAsType(source, R.id.tv_dashboard_address, "field 'tv_dashboard_address'", TextView.class);
    target.navigation = Utils.findRequiredViewAsType(source, R.id.bottomNavigation_dashboard, "field 'navigation'", BottomNavigationViewEx.class);
    target.iv_dashboard_otherRestaurant = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_otherRestaurant, "field 'iv_dashboard_otherRestaurant'", LinearLayout.class);
    target.iv_dashboard_otherStore = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_otherStore, "field 'iv_dashboard_otherStore'", LinearLayout.class);
    target.layout_btnOfficeBoyMaidService = Utils.findRequiredViewAsType(source, R.id.layout_btnOfficeBoyMaidService, "field 'layout_btnOfficeBoyMaidService'", LinearLayout.class);
    target.iv_dashboard_grocery = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_grocery, "field 'iv_dashboard_grocery'", LinearLayout.class);
    target.iv_dashboard_swadesi = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_swadesi, "field 'iv_dashboard_swadesi'", LinearLayout.class);
    target.iv_dashboard_medicine = Utils.findRequiredViewAsType(source, R.id.iv_dashboard_medicine, "field 'iv_dashboard_medicine'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DasboardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageSlider = null;
    target.iv_dashboard_location = null;
    target.iv_dashboard_userProfile = null;
    target.tv_dashboard_address = null;
    target.navigation = null;
    target.iv_dashboard_otherRestaurant = null;
    target.iv_dashboard_otherStore = null;
    target.layout_btnOfficeBoyMaidService = null;
    target.iv_dashboard_grocery = null;
    target.iv_dashboard_swadesi = null;
    target.iv_dashboard_medicine = null;
  }
}
