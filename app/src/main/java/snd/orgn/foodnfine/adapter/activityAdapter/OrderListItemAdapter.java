package snd.orgn.foodnfine.adapter.activityAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.model.data_item.ItemDetailsResponse;

public class OrderListItemAdapter extends RecyclerView.Adapter<OrderListItemAdapter.MyViewHolder> {

    private Activity activity;
    private Context mContext;
    List<ItemDetailsResponse> itemArrayList;

    public OrderListItemAdapter(Activity activity,Context context){
        this.activity = activity;
        this.mContext = context;
        itemArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderListItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modular_order_list_item_layout, parent, false);

        return new OrderListItemAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderListItemAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(itemArrayList.get(position).getProductName());
        holder.itemQuantity.setText(itemArrayList.get(position).getQty());
        holder.totalPrice.setText("₹" + itemArrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemQuantity;
        TextView totalPrice;

        public MyViewHolder(View itemview) {
            super(itemview);

            itemName = (TextView) itemview.findViewById(R.id.tv_orderlist_name);
            itemQuantity = (TextView) itemview.findViewById(R.id.tv_orderlist_quantity);
            totalPrice = (TextView) itemview.findViewById(R.id.tv_orderlist_price);
        }
    }

    public void addOrderDetailsList(List<ItemDetailsResponse> itemDetailsResponseList) {
        this.itemArrayList.addAll(itemDetailsResponseList);
    }

    public void clearOrderDetailsList() {
        if (itemArrayList != null) {
            this.itemArrayList.clear();
        }
    }
}
