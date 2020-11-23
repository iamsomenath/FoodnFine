package snd.orgn.foodnfine.adapter.activityAdapter;

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

import snd.orgn.foodnfine.activity.NewRestaurantDetailsActivity;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.util.NetworkChangeReceiver;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.rest.response.AllRestaurant;

public class RestrurentListAdapter extends RecyclerView.Adapter<RestrurentListAdapter.MyViewHolder> {
    private Activity activity;
    private List<AllRestaurant> allRestaurantsList;
    Context mContext;
    RequestOptions options;
    private NetworkChangeReceiver networkChangeReceiver;
    private Boolean network;

    public RestrurentListAdapter(Activity activity) {
        this.activity = activity;
        this.mContext = activity.getApplicationContext();
        allRestaurantsList = new ArrayList<>();
        options = new RequestOptions()
                .placeholder(R.drawable.logo_restaurant)
                .error(R.drawable.logo_restaurant);

        networkChangeReceiver = new NetworkChangeReceiver(activity);
        network = networkChangeReceiver.isNetworkAvailable();
    }

    @NonNull
    @Override
    public RestrurentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modular_item_restaurent_list, parent, false);

        return new RestrurentListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestrurentListAdapter.MyViewHolder holder, int position) {
        holder.restaurantName.setText(allRestaurantsList.get(position).getName());
        holder.restaurantAddress.setText(allRestaurantsList.get(position).getRestaurantAdd());

        String image_url = allRestaurantsList.get(position).getImage();
        Glide.with(mContext)
                .load(image_url)
                .apply(options)
                .into(holder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return allRestaurantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView restaurantImage;
        TextView restaurantName;
        TextView restaurantTypeFood;
        TextView restaurantAddress;

        public MyViewHolder(View itemview) {
            super(itemview);
            restaurantImage = (RoundedImageView) itemview.findViewById(R.id.rv_restaurantImage);
            restaurantName = (TextView) itemview.findViewById(R.id.tv_restaurantName);
            restaurantTypeFood = (TextView) itemview.findViewById(R.id.tv_restaurantFoodType);
            restaurantAddress = (TextView) itemview.findViewById(R.id.tv_restaurantAddress);

            itemView.setOnClickListener(v -> {
                network = networkChangeReceiver.isNetworkAvailable();
                if (network) {
                    Location startPoint = new Location("locationA");
                    startPoint.setLatitude(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLatitude())));
                    startPoint.setLongitude(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLongitude())));
                    Location endPoint = new Location("locationB");
                    endPoint.setLatitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()));
                    endPoint.setLongitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()));
                    //double distance = startPoint.distanceTo(endPoint) / 1000;
                    double distance = CalculateDistance(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLatitude())),
                            Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLongitude())),
                            Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()),
                            Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()));

                    /*if (distance <= 1.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost1());
                    else if (distance < 5.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost2());
                    else if (distance < 10.0)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost3());
                    else //if(distance>10)
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost4());*/

                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(
                            String.valueOf(distance * Double.parseDouble(DeliveryEverything.getAppSharedPreference().getPerKm())));

                    //Toast.makeText(activity, distance + "", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity, NewRestaurantDetailsActivity.class);
                    intent.putExtra("rest_id", allRestaurantsList.get(getAdapterPosition()).getRestaurantId());
                    new SessionManager(activity).setShopId(allRestaurantsList.get(getAdapterPosition()).getRestaurantId());
                    intent.putExtra("rest_name", allRestaurantsList.get(getAdapterPosition()).getName());
                    intent.putExtra("rest_address", allRestaurantsList.get(getAdapterPosition()).getRestaurantAdd());
                    activity.startActivity(intent);
                }else{
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                            "It seems your device don't have or no internet connection to view details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }

    public void addRestrurentList(List<AllRestaurant> orderList) {
        this.allRestaurantsList.addAll(orderList);
    }

    public void clearRestrurentList() {
        if (allRestaurantsList != null) {
            this.allRestaurantsList.clear();
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
