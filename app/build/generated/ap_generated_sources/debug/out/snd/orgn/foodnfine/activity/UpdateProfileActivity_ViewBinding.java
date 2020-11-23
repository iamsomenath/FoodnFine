// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import snd.orgn.foodnfine.R;

public class UpdateProfileActivity_ViewBinding implements Unbinder {
  private UpdateProfileActivity target;

  @UiThread
  public UpdateProfileActivity_ViewBinding(UpdateProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdateProfileActivity_ViewBinding(UpdateProfileActivity target, View source) {
    this.target = target;

    target.tvBtn_updateProfile = Utils.findRequiredViewAsType(source, R.id.tvBtn_updateProfile, "field 'tvBtn_updateProfile'", CardView.class);
    target.et_input_email = Utils.findRequiredViewAsType(source, R.id.et_input_email, "field 'et_input_email'", TextInputEditText.class);
    target.et_input_name = Utils.findRequiredViewAsType(source, R.id.et_input_name, "field 'et_input_name'", TextInputEditText.class);
    target.iv_updateProfile_back = Utils.findRequiredViewAsType(source, R.id.iv_updateProfile_back, "field 'iv_updateProfile_back'", ImageView.class);
    target.layout_inputName = Utils.findRequiredViewAsType(source, R.id.layout_input_name, "field 'layout_inputName'", TextInputLayout.class);
    target.layout_email = Utils.findRequiredViewAsType(source, R.id.layout_text_input_email, "field 'layout_email'", TextInputLayout.class);
    target.et_input_mobile = Utils.findRequiredViewAsType(source, R.id.et_input_mobile, "field 'et_input_mobile'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvBtn_updateProfile = null;
    target.et_input_email = null;
    target.et_input_name = null;
    target.iv_updateProfile_back = null;
    target.layout_inputName = null;
    target.layout_email = null;
    target.et_input_mobile = null;
  }
}
