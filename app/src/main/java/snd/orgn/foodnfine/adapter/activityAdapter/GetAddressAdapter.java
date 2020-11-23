package snd.orgn.foodnfine.adapter.activityAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;


public class GetAddressAdapter extends RecyclerView.Adapter<GetAddressAdapter.MyViewHolder> {

    private Activity activity;
    List<AddressDetails> addressDetailsList;
    Context mContext;
    int selectedPosition = -1;
    CardView iv_go;

    public GetAddressAdapter(Activity activity, CardView iv_go) {
        this.activity = activity;
        addressDetailsList = new ArrayList<>();
        this.mContext = activity.getApplicationContext();
        this.iv_go = iv_go;
    }

    @NonNull
    @Override
    public GetAddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_saved_address_item2, parent, false);

        return new GetAddressAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GetAddressAdapter.MyViewHolder holder, int position) {

        String locationType = addressDetailsList.get(position).getLocationType();
        holder.addressType.setText(locationType);
        if (locationType.equals("Home")) {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_home);
        } else if (locationType.equals("Work")) {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_office);
        } else {
            holder.addressTypeImage.setBackgroundResource(R.drawable.ic_addres_other);
        }
        holder.addressLine1.setText(addressDetailsList.get(position).getHouse() + ", " + addressDetailsList.get(position).getBuilding());
        holder.getAddressLine2.setText(addressDetailsList.get(position).getLandmark());
        holder.getAddressLine3.setText(addressDetailsList.get(position).getContactPerson() + " " + addressDetailsList.get(position).getContactNumber());
        holder.getAddressLine4.setText(addressDetailsList.get(position).getInstruction());

        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(v -> {
            iv_go.setVisibility(View.VISIBLE);
            selectedPosition = position;
            notifyDataSetChanged();
        });

        iv_go.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", addressDetailsList.get(selectedPosition));
            if (activity.getIntent().getStringExtra("VAL").equalsIgnoreCase("3"))
                activity.setResult(3, intent);
            else
                activity.setResult(4, intent);
            activity.finish();
        });
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

        public MyViewHolder(View itemview) {
            super(itemview);
            addressTypeImage = (ImageView) itemview.findViewById(R.id.iv_addressTypeImage);
            addressType = (TextView) itemview.findViewById(R.id.tv_typeOfAddress);
            addressLine1 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line1);
            getAddressLine4 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line2);
            getAddressLine2 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line3);
            getAddressLine3 = (TextView) itemview.findViewById(R.id.tv_saveAddress_line4);
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
}
