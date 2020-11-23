// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
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

public class DailyBookingActivity_ViewBinding implements Unbinder {
  private DailyBookingActivity target;

  @UiThread
  public DailyBookingActivity_ViewBinding(DailyBookingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DailyBookingActivity_ViewBinding(DailyBookingActivity target, View source) {
    this.target = target;

    target.tvBtn_dailyBookingNext = Utils.findRequiredViewAsType(source, R.id.tvBtn_dailyBookingNext, "field 'tvBtn_dailyBookingNext'", CardView.class);
    target.iv_daily_back = Utils.findRequiredViewAsType(source, R.id.iv_daily_back, "field 'iv_daily_back'", ImageView.class);
    target.layout_date = Utils.findRequiredViewAsType(source, R.id.layout_date, "field 'layout_date'", LinearLayout.class);
    target.layout_time = Utils.findRequiredViewAsType(source, R.id.layout_time, "field 'layout_time'", LinearLayout.class);
    target.tv_date = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tv_date'", TextView.class);
    target.tv_time = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tv_time'", TextView.class);
    target.rg_daily_booking_service = Utils.findRequiredViewAsType(source, R.id.rg_daily_booking_service, "field 'rg_daily_booking_service'", RadioGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DailyBookingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvBtn_dailyBookingNext = null;
    target.iv_daily_back = null;
    target.layout_date = null;
    target.layout_time = null;
    target.tv_date = null;
    target.tv_time = null;
    target.rg_daily_booking_service = null;
  }
}
