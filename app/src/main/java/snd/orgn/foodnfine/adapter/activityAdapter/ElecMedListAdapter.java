package snd.orgn.foodnfine.adapter.activityAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.SwadesiProductActivity;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.model.data_item.AllElectMedPojo;
import snd.orgn.foodnfine.util.NetworkChangeReceiver;

public class ElecMedListAdapter extends RecyclerView.Adapter<ElecMedListAdapter.MyViewHolder> {

    private Activity activity;
    private List<AllElectMedPojo> allList;
    Context mContext;
    RequestOptions options;
    private NetworkChangeReceiver networkChangeReceiver;
    private Boolean network;

    public ElecMedListAdapter(Activity activity) {
        this.activity = activity;
        this.mContext = activity.getApplicationContext();
        allList = new ArrayList<>();
        options = new RequestOptions()
                .placeholder(R.drawable.logo_restaurant)
                .error(R.drawable.logo_restaurant);

        networkChangeReceiver = new NetworkChangeReceiver(activity);
        network = networkChangeReceiver.isNetworkAvailable();
    }

    @NonNull
    @Override
    public ElecMedListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modular_item_elect_list, parent, false);

        return new ElecMedListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ElecMedListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(allList.get(position).getShop_name());
        holder.address.setText(allList.get(position).getShop_shop_loc());
        holder.time.setText(allList.get(position).getDelivery_time() + " delivery");

        String image_url = allList.get(position).getShop_img();
        Glide.with(mContext)
                .load(image_url)
                .apply(options)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return allList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image;
        TextView name;
        TextView type;
        TextView address;
        TextView time;

        public MyViewHolder(View itemview) {
            super(itemview);
            image = (RoundedImageView) itemview.findViewById(R.id.rv_Image);
            name = (TextView) itemview.findViewById(R.id.tv_Name);
            type = (TextView) itemview.findViewById(R.id.tv_groceryFoodType);
            address = (TextView) itemview.findViewById(R.id.tv_Address);
            time = (TextView) itemview.findViewById(R.id.tv_time);

            itemView.setOnClickListener(v -> {
                network = networkChangeReceiver.isNetworkAvailable();
                if (network) {

                    Location startPoint = new Location("locationA");
                    startPoint.setLatitude(Double.parseDouble(Objects.requireNonNull(allList.get(getAdapterPosition()).getLatitude())));
                    startPoint.setLongitude(Double.parseDouble(Objects.requireNonNull(allList.get(getAdapterPosition()).getLongitude())));
                    Location endPoint = new Location("locationB");
                    endPoint.setLatitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLatitude()));
                    endPoint.setLongitude(Double.parseDouble(FoodnFine.getAppSharedPreference().getLongitude()));
                    //double distance = startPoint.distanceTo(endPoint) / 1000;
                    double distance = CalculateDistance(Double.parseDouble(Objects.requireNonNull(allList.get(getAdapterPosition()).getLatitude())),
                            Double.parseDouble(Objects.requireNonNull(allList.get(getAdapterPosition()).getLongitude())),
                            Double.parseDouble(FoodnFine.getAppSharedPreference().getLatitude()),
                            Double.parseDouble(FoodnFine.getAppSharedPreference().getLongitude()));

                    /*if (distance <= 1.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost1());
                    else if (distance < 5.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost2());
                    else if (distance < 10.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost3());
                    else //if(distance>10)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost4());*/

                    FoodnFine.getAppSharedPreference().saveDeliveryCost(
                            String.valueOf(distance * Double.parseDouble(FoodnFine.getAppSharedPreference().getPerKm())));

                    //Toast.makeText(activity, distance + "", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity, SwadesiProductActivity.class);
                    intent.putExtra("shop_id", allList.get(getAdapterPosition()).getShop_id());
                    new SessionManager(activity).setShopId(allList.get(getAdapterPosition()).getShop_id());
                    intent.putExtra("shop_name", allList.get(getAdapterPosition()).getShop_name());
                    intent.putExtra("type", "ELE_MED");
                    activity.startActivity(intent);
                }else{
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                            "It seems your device don't have or no internet connection to view details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }

    public void addList(List<AllElectMedPojo> allList) {
        this.allList.addAll(allList);
    }

    public void clearList() {
        if (allList != null) {
            this.allList.clear();
        }
    }

    private double CalculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
