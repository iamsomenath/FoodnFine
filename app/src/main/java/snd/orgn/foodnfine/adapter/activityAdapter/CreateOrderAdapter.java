package snd.orgn.foodnfine.adapter.activityAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.callbacks.CallbackSelectedCartItemUpdate;
import snd.orgn.foodnfine.rest.response.CartDatum;

import static snd.orgn.foodnfine.constant.AppConstants.RUPREES_SYMBOL;

public class CreateOrderAdapter extends RecyclerView.Adapter<CreateOrderAdapter.MyViewHolder> {

    private Activity activity;
    private List<CartDatum> cartData;
    private CallbackSelectedCartItemUpdate callbackSelectedCartItemUpdate;

    public CreateOrderAdapter(Activity activity){
        this.activity = activity;
        cartData = new ArrayList<>();

    }
    @NonNull
    @Override
    public CreateOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modular_confirm_order_layout_item, parent, false);

        return new CreateOrderAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CreateOrderAdapter.MyViewHolder holder, int position) {
        holder.orderItemName.setText(cartData.get(position).getProductName());
        int qty = Integer.parseInt(cartData.get(position).getQty());
        int price = (int) Double.parseDouble(cartData.get(position).getPrice());
        holder.orderItemPrice.setText(RUPREES_SYMBOL + (qty * price));
        holder.itemQuantity.setText(cartData.get(position).getQty());

        holder.btnSub.setOnClickListener(v1->{
           /* Log.d("TEST1", cartData.get(position).getCartId() + " " + holder.itemQuantity.getText().toString()
                    + " " + cartData.get(position).getPrice());*/
            if (holder.itemQuantity.getText().toString().equalsIgnoreCase("1")) {
                // do something when the button is clicked
                // do something when the button is clicked
                new AlertDialog.Builder(activity)
                        //.setIcon(R.drawable.exit)
                        .setMessage("Are you sure to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (arg0, arg1) -> {
                            arg0.dismiss();
                            callbackSelectedCartItemUpdate.onDeletedCartItem(cartData.get(position).getCartId());
                        })
                        .setNegativeButton("No", (arg0, arg1) -> arg0.dismiss()).show();
            } else {
                int quantity = Integer.parseInt(holder.itemQuantity.getText().toString()) - 1;
                int itemprice = (int) Double.parseDouble(cartData.get(position).getPrice());
                holder.orderItemPrice.setText(RUPREES_SYMBOL + (quantity * itemprice));
                holder.itemQuantity.setText(String.valueOf(Integer.parseInt(holder.itemQuantity.getText().toString()) - 1));
                callbackSelectedCartItemUpdate.onEditedCartItem(cartData.get(position).getCartId(), holder.itemQuantity.getText().toString(),
                        cartData.get(position).getPrice());
                Log.d("TEST!!", cartData.get(position).getCartId() + " " + holder.itemQuantity.getText().toString()
                        + " " + cartData.get(position).getPrice());
            }
        });

        holder.btnAdd.setOnClickListener(v2->{
            int quantity = Integer.parseInt(holder.itemQuantity.getText().toString()) + 1;
            int itemprice = (int) Double.parseDouble(cartData.get(position).getPrice());
            holder.orderItemPrice.setText(RUPREES_SYMBOL + (quantity * itemprice));
            holder.itemQuantity.setText(String.valueOf(Integer.parseInt(holder.itemQuantity.getText().toString()) + 1));
            callbackSelectedCartItemUpdate.onEditedCartItem(cartData.get(position).getCartId(), holder.itemQuantity.getText().toString(),
                    cartData.get(position).getPrice());
            Log.d("TEST!!", cartData.get(position).getCartId() + " " + holder.itemQuantity.getText().toString()
                    + " " + cartData.get(position).getPrice());
        });
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView btnAdd;
        ImageView btnSub;
        TextView orderItemName;
        TextView orderItemPrice;
        TextView itemQuantity;

        public MyViewHolder(View itemview) {
            super(itemview);
            btnAdd = (ImageView) itemview.findViewById(R.id.ivBtn_add);
            btnSub = (ImageView) itemview.findViewById(R.id.ivBtn_substract);
            orderItemName = (TextView) itemview.findViewById(R.id.tv_confirmOrder_itemName);
            orderItemPrice = (TextView) itemview.findViewById(R.id.tv_ConfirmOrder_itemPrice);
            itemQuantity = (TextView) itemview.findViewById(R.id.tv_quantity);
        }
    }

    public void addAllcartDeatils(List<CartDatum> cartDatumList) {
        this.cartData.addAll(cartDatumList);
    }

    public void clearcartDetails() {
        if (cartData != null) {
            this.cartData.clear();
        }
    }

    public void setCallback(CallbackSelectedCartItemUpdate callback){
        this.callbackSelectedCartItemUpdate=callback;
    }
}

