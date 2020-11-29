package snd.orgn.foodnfine.adapter.activityAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.AddAddressActivity;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.util.LoadingDialog;
import snd.orgn.foodnfine.view_model.ActivityViewModel.SavedAddressViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_ADDRESS_ID;

public class SaveAddressAdapter extends RecyclerView.Adapter<SaveAddressAdapter.MyViewHolder> {


    private Activity activity;
    List<AddressDetails> addressDetailsList;
    Context mContext;
    SavedAddressViewModel viewModel;

    public SaveAddressAdapter(Activity activity, SavedAddressViewModel viewModel) {
        this.activity = activity;
        addressDetailsList = new ArrayList<>();
        this.mContext = activity.getApplicationContext();
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public SaveAddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_saved_address_item, parent, false);

        return new SaveAddressAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveAddressAdapter.MyViewHolder holder, int position) {

        String locationType = addressDetailsList.get(position).getLocationType();
        holder.addressType.setText(locationType);
        if (locationType.equals("Home")) {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_home);
        } else if (locationType.equals("Work")) {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_office);
        } else {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_other);
        }
        holder.addressLine1.setText(addressDetailsList.get(position).getHouse() +", "+ addressDetailsList.get(position).getBuilding());
        holder.getAddressLine2.setText(addressDetailsList.get(position).getLandmark());
        holder.getAddressLine3.setText(addressDetailsList.get(position).getContactPerson() + " " + addressDetailsList.get(position).getContactNumber());
        holder.getAddressLine4.setText(addressDetailsList.get(position).getInstruction());

        holder.editAddressbtn.setOnClickListener(v -> {
            goToEditDeatilsPage(addressDetailsList.get(position).getUserAddId());
        });

        holder.deleteAddressBtn.setOnClickListener(v ->
                deleteAddressDetails(FoodnFine.getAppSharedPreference().getUserId(),
                        addressDetailsList.get(position).getUserAddId(), position));
    }

    @Override
    public int getItemCount() {
        return addressDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView addressTypeImage;
        TextView addressType;
        TextView addressLine1;
        TextView getAddressLine2;
        TextView getAddressLine3;
        TextView getAddressLine4;
        CardView editAddressbtn;
        CardView deleteAddressBtn;

        public MyViewHolder(View itemview) {
            super(itemview);
            addressTypeImage = (ImageView) itemview.findViewById(R.id.iv_addressTypeImage);
            addressType = (TextView) itemview.findViewById(R.id.tv_typeOfAddress);
            addressLine1 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line1);
            getAddressLine4 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line2);
            getAddressLine2 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line3);
            getAddressLine3 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line4);
            editAddressbtn = (CardView) itemview.findViewById(R.id.cv_editBtn);
            deleteAddressBtn = (CardView) itemview.findViewById(R.id.cv_deleteBtn);
        }
    }

    public void addAddressDeatilsList(List<AddressDetails> addressDetails) {
        this.addressDetailsList.addAll(addressDetails);

    }

    public void clearList() {
        if (addressDetailsList != null) {
            this.addressDetailsList.clear();
        }
    }

    private void goToEditDeatilsPage(String addressId){
        Intent intent = new Intent(mContext, AddAddressActivity.class);
        intent.putExtra(INTENT_STRING_ADDRESS_ID,  addressId);
        intent.putExtra("category4","");
        mContext.startActivity(intent);
    }

    public void removeAt(int position) {
        this.addressDetailsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.addressDetailsList.size());
    }

    public void deleteAddressDetails(String userId, String addressId, int position) {
        LoadingDialog loadingDialog =  new LoadingDialog(activity);
        loadingDialog.showDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<ResponseBody> call = service.deleteUserAddress(userId, addressId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.body() != null) {
                    loadingDialog.hideDialog();
                    try {
                        JSONObject jsonObject =  new JSONObject(response.body().string());
                        if(jsonObject.getString("success").equalsIgnoreCase("SUCCESS")){
                            removeAt(position);
                        }else{
                            Toast.makeText(activity, "Unable to delete address.....", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingDialog.hideDialog();
                Log.d(TAG, "onFailure: " + t.getMessage());
                // loadingDialog.hideDialog();
            }
        });
    }
}