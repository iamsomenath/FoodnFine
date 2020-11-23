// Generated code from Butter Knife. Do not modify!
package snd.orgn.foodnfine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class AddAddressActivity_ViewBinding implements Unbinder {
  private AddAddressActivity target;

  @UiThread
  public AddAddressActivity_ViewBinding(AddAddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddAddressActivity_ViewBinding(AddAddressActivity target, View source) {
    this.target = target;

    target.containerLayout_addAddress = Utils.findRequiredViewAsType(source, R.id.containerLayout_addAddress, "field 'containerLayout_addAddress'", LinearLayout.class);
    target.layout_currentLocation = Utils.findRequiredViewAsType(source, R.id.layout_currentLocation, "field 'layout_currentLocation'", LinearLayout.class);
    target.layout_saveAs = Utils.findRequiredViewAsType(source, R.id.layout_saveAs, "field 'layout_saveAs'", LinearLayout.class);
    target.iv_addAddress_back = Utils.findRequiredViewAsType(source, R.id.iv_addAddress_back, "field 'iv_addAddress_back'", ImageView.class);
    target.cv_search_location = Utils.findRequiredViewAsType(source, R.id.cv_search_location, "field 'cv_search_location'", CardView.class);
    target.et_addAddress_apartmentName = Utils.findRequiredViewAsType(source, R.id.et_addAddress_apartmentName, "field 'et_addAddress_apartmentName'", TextInputEditText.class);
    target.et_addAddress_otherLocation = Utils.findRequiredViewAsType(source, R.id.et_addAddress_otherLocation, "field 'et_addAddress_otherLocation'", TextInputEditText.class);
    target.et_addAddress_contactPerson = Utils.findRequiredViewAsType(source, R.id.et_addAddress_contactPerson, "field 'et_addAddress_contactPerson'", TextInputEditText.class);
    target.et_addAddress_intructionToReachLocation = Utils.findRequiredViewAsType(source, R.id.et_addAddress_intructionToReachLocation, "field 'et_addAddress_intructionToReachLocation'", TextInputEditText.class);
    target.et_addAddress_landMark = Utils.findRequiredViewAsType(source, R.id.et_addAddress_landMark, "field 'et_addAddress_landMark'", TextInputEditText.class);
    target.et_addAddress_house_flat_no = Utils.findRequiredViewAsType(source, R.id.et_addAddress_house_flat_no, "field 'et_addAddress_house_flat_no'", TextInputEditText.class);
    target.et_addAddress_location = Utils.findRequiredViewAsType(source, R.id.et_addAddress_location, "field 'et_addAddress_location'", TextInputEditText.class);
    target.et_addAddress_contactNumber = Utils.findRequiredViewAsType(source, R.id.et_addAddress_contactNumber, "field 'et_addAddress_contactNumber'", TextInputEditText.class);
    target.text_contact_number = Utils.findRequiredViewAsType(source, R.id.text_contact_number, "field 'text_contact_number'", TextInputLayout.class);
    target.text_input_location = Utils.findRequiredViewAsType(source, R.id.text_input_location, "field 'text_input_location'", TextInputLayout.class);
    target.text_input_house_flat_no = Utils.findRequiredViewAsType(source, R.id.text_input_house_flat_no, "field 'text_input_house_flat_no'", TextInputLayout.class);
    target.text_input_buliding_or_apatment_name = Utils.findRequiredViewAsType(source, R.id.text_input_buliding_or_apatment_name, "field 'text_input_buliding_or_apatment_name'", TextInputLayout.class);
    target.text_input_landmark = Utils.findRequiredViewAsType(source, R.id.text_input_landmark, "field 'text_input_landmark'", TextInputLayout.class);
    target.text_input_instruction_to_reach_location = Utils.findRequiredViewAsType(source, R.id.text_input_instruction_to_reach_location, "field 'text_input_instruction_to_reach_location'", TextInputLayout.class);
    target.text_contact_person = Utils.findRequiredViewAsType(source, R.id.text_contact_person, "field 'text_contact_person'", TextInputLayout.class);
    target.text_input_otherLocation = Utils.findRequiredViewAsType(source, R.id.text_input_otherLocation, "field 'text_input_otherLocation'", TextInputLayout.class);
    target.tv_addAddress_home = Utils.findRequiredViewAsType(source, R.id.tv_addAddress_home, "field 'tv_addAddress_home'", TextView.class);
    target.tv_addAddress_office = Utils.findRequiredViewAsType(source, R.id.tv_addAddress_office, "field 'tv_addAddress_office'", TextView.class);
    target.tv_addAddress_other = Utils.findRequiredViewAsType(source, R.id.tv_addAddress_other, "field 'tv_addAddress_other'", TextView.class);
    target.tv_saveAs = Utils.findRequiredViewAsType(source, R.id.tv_saveAs, "field 'tv_saveAs'", TextView.class);
    target.tv_toolbar_title = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tv_toolbar_title'", TextView.class);
    target.tvBtn_text = Utils.findRequiredViewAsType(source, R.id.tvBtn_text, "field 'tvBtn_text'", TextView.class);
    target.cv_addAddressBtn = Utils.findRequiredViewAsType(source, R.id.cv_addAddressBtn, "field 'cv_addAddressBtn'", CardView.class);
    target.layout_location = Utils.findRequiredViewAsType(source, R.id.layout_location, "field 'layout_location'", LinearLayout.class);
    target.layout_saveAddress = Utils.findRequiredViewAsType(source, R.id.layout_saveAddress, "field 'layout_saveAddress'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddAddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.containerLayout_addAddress = null;
    target.layout_currentLocation = null;
    target.layout_saveAs = null;
    target.iv_addAddress_back = null;
    target.cv_search_location = null;
    target.et_addAddress_apartmentName = null;
    target.et_addAddress_otherLocation = null;
    target.et_addAddress_contactPerson = null;
    target.et_addAddress_intructionToReachLocation = null;
    target.et_addAddress_landMark = null;
    target.et_addAddress_house_flat_no = null;
    target.et_addAddress_location = null;
    target.et_addAddress_contactNumber = null;
    target.text_contact_number = null;
    target.text_input_location = null;
    target.text_input_house_flat_no = null;
    target.text_input_buliding_or_apatment_name = null;
    target.text_input_landmark = null;
    target.text_input_instruction_to_reach_location = null;
    target.text_contact_person = null;
    target.text_input_otherLocation = null;
    target.tv_addAddress_home = null;
    target.tv_addAddress_office = null;
    target.tv_addAddress_other = null;
    target.tv_saveAs = null;
    target.tv_toolbar_title = null;
    target.tvBtn_text = null;
    target.cv_addAddressBtn = null;
    target.layout_location = null;
    target.layout_saveAddress = null;
  }
}
