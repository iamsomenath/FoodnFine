// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.goodiebag.pinview.Pinview;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class OtpCheckActivity_ViewBinding implements Unbinder {
  private OtpCheckActivity target;

  @UiThread
  public OtpCheckActivity_ViewBinding(OtpCheckActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OtpCheckActivity_ViewBinding(OtpCheckActivity target, View source) {
    this.target = target;

    target.cvBtn_resend = Utils.findRequiredViewAsType(source, R.id.tvBtn_verify_continue, "field 'cvBtn_resend'", CardView.class);
    target.mobileNumber = Utils.findRequiredViewAsType(source, R.id.tv_mobileNumber, "field 'mobileNumber'", TextView.class);
    target.pinview = Utils.findRequiredViewAsType(source, R.id.pinview_checkOtp, "field 'pinview'", Pinview.class);
    target.countDownTimer = Utils.findRequiredViewAsType(source, R.id.countdownTimer_otpVerification, "field 'countDownTimer'", TextView.class);
    target.layoutCounter = Utils.findRequiredViewAsType(source, R.id.layout_countdownTimer, "field 'layoutCounter'", LinearLayout.class);
    target.tv_btn_change = Utils.findRequiredViewAsType(source, R.id.tv_btn_change, "field 'tv_btn_change'", TextView.class);
    target.et_checkOtp = Utils.findRequiredViewAsType(source, R.id.et_checkOtp, "field 'et_checkOtp'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OtpCheckActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cvBtn_resend = null;
    target.mobileNumber = null;
    target.pinview = null;
    target.countDownTimer = null;
    target.layoutCounter = null;
    target.tv_btn_change = null;
    target.et_checkOtp = null;
  }
}
