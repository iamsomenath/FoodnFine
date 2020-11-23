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
import snd.orgn.foodnfine.activity.NewGroceryDetailsActivity;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.rest.response.AllGrocery;
import snd.orgn.foodnfine.util.NetworkChangeReceiver;

//OLD
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.MyViewHolder> {
    private Activity activity;
    private List<AllGrocery> allRestaurantsList;
    private Context mContext;
    RequestOptions options;
    private NetworkChangeReceiver networkChangeReceiver;
    private Boolean network;

    public GroceryListAdapter(Activity activity) {
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
    public GroceryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modular_item_grocery_list, parent, false);

        return new GroceryListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GroceryListAdapter.MyViewHolder holder, int position) {
        holder.groceryName.setText(allRestaurantsList.get(position).getName());
        holder.groceryAddress.setText(allRestaurantsList.get(position).getAddress());
        //holder.time.setText(allRestaurantsList.get(position).getTime() + " delivery");

        String image_url = allRestaurantsList.get(position).getImage();
        Glide.with(mContext)
                .load(image_url)
                .apply(options)
                .into(holder.groceryImage);
    }

    @Override
    public int getItemCount() {
        return allRestaurantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView groceryImage;
        TextView groceryName;
        TextView groceryTypeFood;
        TextView groceryAddress;
        //TextView time;

        public MyViewHolder(View itemview) {
            super(itemview);
            groceryImage = (RoundedImageView) itemview.findViewById(R.id.rv_groceryImage);
            groceryName = (TextView) itemview.findViewById(R.id.tv_groceryName);
            groceryTypeFood = (TextView) itemview.findViewById(R.id.tv_groceryFoodType);
            groceryAddress = (TextView) itemview.findViewById(R.id.tv_groceryAddress);
            //time = (TextView) itemview.findViewById(R.id.tv_time);

            itemView.setOnClickListener(v -> {
                network = networkChangeReceiver.isNetworkAvailable();
                if (network) {
                    //Log.d("LAT-LON", DeliveryEverything.getAppSharedPreference().getLatitude() + " " + DeliveryEverything.getAppSharedPreference().getLongitude());
                    Location startPoint = new Location("locationA");
                    startPoint.setLatitude(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLatitude())));
                    startPoint.setLongitude(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLongitude())));
                    Location endPoint = new Location("locationA");
                    endPoint.setLatitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()));
                    endPoint.setLongitude(Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()));
                    //double distance = startPoint.distanceTo(endPoint) / 1000;
                    double distance = CalculateDistance(Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLatitude())),
                            Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLongitude())),
                            Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()),
                                    Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()));
                    //double distance = CalculateDistance(22.6519, 88.3786, 22.5303, 88.3436);
                    //Log.d("DISTANCE", Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLatitude())) + "");
                    //Log.d("DISTANCE", Double.parseDouble(Objects.requireNonNull(allRestaurantsList.get(getAdapterPosition()).getLongitude())) + "");
                    //Log.d("DISTANCE", Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLatitude()) + "");
                    //Log.d("DISTANCE", Double.parseDouble(DeliveryEverything.getAppSharedPreference().getLongitude()) + "");

                    /*Toast.makeText(activity, DeliveryEverything.getAppSharedPreference().getCost1(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, DeliveryEverything.getAppSharedPreference().getCost2(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, DeliveryEverything.getAppSharedPreference().getCost3(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, DeliveryEverything.getAppSharedPreference().getCost4(), Toast.LENGTH_SHORT).show();*/

                    /*if (distance <= 1.0)
                        //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInOneKm));
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost1());
                    else if (distance < 5.0)
                        //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInFiveKm));
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost2());
                    else if (distance < 10.0)
                        //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInTenKm));
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost3());
                    else //if(distance>10)
                        //DeliveryEverything.getAppSharedPreference().saveDeliveryCost(String.valueOf(GroceryListActivity.chargesInDoubleInGrater10Km));
                        DeliveryEverything.getAppSharedPreference().saveDeliveryCost(DeliveryEverything.getAppSharedPreference().getCost4());*/

                    DeliveryEverything.getAppSharedPreference().saveDeliveryCost(
                            String.valueOf(distance * Double.parseDouble(DeliveryEverything.getAppSharedPreference().getPerKm())));

                    //Toast.makeText(activity, "Distance : " + distance, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(activity, Double.parseDouble(DeliveryEverything.getAppSharedPreference().getPerKm()) + "", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity, NewGroceryDetailsActivity.class);
                    intent.putExtra("grocery_id", allRestaurantsList.get(getAdapterPosition()).getGroceryId());
                    new SessionManager(activity).setShopId(allRestaurantsList.get(getAdapterPosition()).getGroceryId());
                    intent.putExtra("rest_name", allRestaurantsList.get(getAdapterPosition()).getName());
                    intent.putExtra("rest_address", allRestaurantsList.get(getAdapterPosition()).getAddress());
                    activity.startActivity(intent);
                }else{
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                            "It seems your device don't have or no internet connection to view details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }

    public void addGroceryList(List<AllGrocery> orderList) {
        this.allRestaurantsList.addAll(orderList);
    }

    public void clearGroceryList() {
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
