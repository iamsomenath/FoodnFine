package snd.orgn.foodnfine.adapter.fragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.callbacks.CallbackGroceryListItemSelectAdapter;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.model.data_item.AllGroceryProduct;

import static snd.orgn.foodnfine.constant.AppConstants.RUPREES_SYMBOL;

public class GroceryFragAdpter extends RecyclerView.Adapter<GroceryFragAdpter.ViewHolder> {
    private Activity activity;
    private Context context;
    private List<AllGroceryProduct> allGroceryProductList;
    HashMap<String, String> data = new HashMap<>();
    private SessionManager sessionManager;
    private Menu menu;
    private String uid;
    private String dev_key;
    RequestOptions options;
    CallbackGroceryListItemSelectAdapter callback;

    public GroceryFragAdpter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        allGroceryProductList = new ArrayList<>();
        options = new RequestOptions()
                .placeholder(R.drawable.basket)
                .error(R.drawable.basket);
    }

    @Override
    public GroceryFragAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modular_item_layout_grocery, parent, false);
        GroceryFragAdpter.ViewHolder vh = new GroceryFragAdpter.ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(GroceryFragAdpter.ViewHolder holder, final int position) {
        holder.itemName.setText(allGroceryProductList.get(position).getProductName());
        holder.itemPrice.setText(RUPREES_SYMBOL+ allGroceryProductList.get(position).getPrice());
        holder.item_description.setText(allGroceryProductList.get(position).getProductDesc());
        holder.groceryquantity.setText(allGroceryProductList.get(position).getWeight()+" " + allGroceryProductList.get(position).getUnit());
        String image_url = allGroceryProductList.get(position).getImage();
        Glide.with(context)
                .load(image_url)
                .apply(options)
                .into(holder.productImage);


        holder.itemAddMinusBtn.setVisibility(View.VISIBLE);
        holder.itemAddBtn.setVisibility(View.VISIBLE);
        holder.itemAddBtn.setOnClickListener(v->{
            callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),allGroceryProductList.get(position).getProductId(),allGroceryProductList.get(position).getPrice());

        });

        holder.minus.setOnClickListener(v1->{
            if (holder.quantity.getText().toString().equalsIgnoreCase("1")) {
                Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                        "Quantity cannot be less than one", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.parseColor("#EFD33A"));
                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.parseColor("#00585e"));

                snackbar.show();
            } else {
                holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString()) - 1));
                Double price = Double.parseDouble(allGroceryProductList.get(position).getPrice());
                Double itemQuantity =Double.parseDouble(String.valueOf(holder.quantity.getText()));
                Double totalPrice = price* itemQuantity;
               // callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),String.valueOf(totalPrice),"");
            }
        });
        holder.add.setOnClickListener(v2->{
            holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString()) + 1));
            Double price = Double.parseDouble(allGroceryProductList.get(position).getPrice());
            Double itemQuantity =Double.parseDouble(String.valueOf(holder.quantity.getText()));
            Double totalPrice = price* itemQuantity;
            //callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),String.valueOf(totalPrice),"");
        });

    }

    @Override
    public int getItemCount() {
        return allGroceryProductList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// init the item view's
        TextView item_description;
        TextView itemPrice;
        LinearLayout itemAddBtn;
        LinearLayout itemAddMinusBtn;
        TextView quantity;
        TextView groceryquantity;
        ImageView add;
        ImageView minus;
        RoundedImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            item_description = (TextView) itemView.findViewById(R.id.tv_grocery_item_description);
            itemName = (TextView) itemView.findViewById(R.id.tv_grocery_itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_grocery_itemPrice);
            itemAddBtn = (LinearLayout) itemView.findViewById(R.id.layout_AddBtn);
            itemAddMinusBtn = (LinearLayout) itemView.findViewById(R.id.layout_add_minusBtn);
            quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            groceryquantity = (TextView) itemView.findViewById(R.id.tv_grocery_quantity);
            add = (ImageView) itemView.findViewById(R.id.ivBtn_add);
            minus = (ImageView) itemView.findViewById(R.id.ivBtn_substract);
            productImage = (RoundedImageView) itemView.findViewById(R.id.rv_groceryProductImage);
        }
    }

    public void addAllGroceryProduct(List<AllGroceryProduct> allGroceryProducts) {
        this.allGroceryProductList.addAll(allGroceryProducts);
    }

    public void clearProductList() {
        if (allGroceryProductList != null) {
            this.allGroceryProductList.clear();
        }
    }

    public void setCallback(CallbackGroceryListItemSelectAdapter callback){
        this.callback=callback;
    }
}
