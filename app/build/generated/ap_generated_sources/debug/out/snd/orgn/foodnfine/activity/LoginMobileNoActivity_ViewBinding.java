// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class LoginMobileNoActivity_ViewBinding implements Unbinder {
  private LoginMobileNoActivity target;

  @UiThread
  public LoginMobileNoActivity_ViewBinding(LoginMobileNoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginMobileNoActivity_ViewBinding(LoginMobileNoActivity target, View source) {
    this.target = target;

    target.tvBtn_continue = Utils.findRequiredViewAsType(source, R.id.cv_loginBtn, "field 'tvBtn_continue'", CardView.class);
    target.et_login_mobileNo = Utils.findRequiredViewAsType(source, R.id.et_login_mobileNo, "field 'et_login_mobileNo'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginMobileNoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvBtn_continue = null;
    target.et_login_mobileNo = null;
  }
}
