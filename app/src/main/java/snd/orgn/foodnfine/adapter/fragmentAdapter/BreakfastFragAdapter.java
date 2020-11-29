package snd.orgn.foodnfine.adapter.fragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.callbacks.CallbackGroceryListItemSelectAdapter;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.helper.other.BadgeDrawable;
import snd.orgn.foodnfine.model.data_item.RestFood;

import static snd.orgn.foodnfine.constant.AppConstants.RUPREES_SYMBOL;

public class BreakfastFragAdapter extends RecyclerView.Adapter<BreakfastFragAdapter.ViewHolder> {
    private Activity activity;
    private Context context;
    private List<RestFood> foodLists;
    HashMap<String, String> data = new HashMap<>();
    private SessionManager sessionManager;
    private Menu menu;
    private String uid;
    private String dev_key;
    CallbackGroceryListItemSelectAdapter callback;

    public BreakfastFragAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        foodLists = new ArrayList<>();

        sessionManager = new SessionManager(context);
        //Retriving Data
        data = sessionManager.getDetails();
        uid = FoodnFine.getAppSharedPreference().getUserId();
        dev_key= FoodnFine.getAppSharedPreference().getDevKey();
    }

    @Override
    public BreakfastFragAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modular_item_layout_breakfast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BreakfastFragAdapter.ViewHolder holder, final int position) {
        holder.itemName.setText(foodLists.get(position).getFoodName());
        holder.itemPrice.setText(RUPREES_SYMBOL + foodLists.get(position).getPrice());
        holder.item_description.setText(foodLists.get(position).getSubmenuDesc());
        holder.layout_add_minusBtn.setVisibility(View.VISIBLE);
        holder.itemAddBtn.setVisibility(View.VISIBLE);
        holder.itemAddBtn.setOnClickListener(v->{
            callback.onAddBttomPres(String.valueOf(holder.quantity.getText()),foodLists.get(position).getFoodId(),foodLists.get(position).getPrice());

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
            }
        });
        holder.add.setOnClickListener(v2->{
            holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString()) + 1));
        });

//
//        holder.itemAddBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String DhabaId = uid;
//                String FoodId = foodLists.get(position).getFoodId();
//                String name = foodLists.get(position).getFoodName();
//                String foodname = foodLists.get(position).getFoodName();
//                String address = "";
//                String quantity = String.valueOf(holder.quantity.getText());
//                Toast.makeText(activity,foodname,Toast.LENGTH_LONG).show();
//                Add_To_Cart(DhabaId,FoodId,quantity,name,address);
//
////                Add_To_Cart(itemArrayList.get(position).getDhabaId(), itemArrayList.get(position).getFoodId(), holder.quantity.getText().toString(),
////                        itemArrayList.get(position).getName(), itemArrayList.get(position).getAddress());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return foodLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// init the item view's
        TextView item_description;
        TextView itemPrice;
        TextView quantity;
        ImageView add;
        ImageView minus;
        LinearLayout itemAddBtn;
        LinearLayout layout_add_minusBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            item_description = (TextView) itemView.findViewById(R.id.tv_breakFast_item_description);
            itemName = (TextView) itemView.findViewById(R.id.tv_breakFast_itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_breakFast_itemPrice);
            quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            add = (ImageView) itemView.findViewById(R.id.ivBtn_add);
            minus = (ImageView) itemView.findViewById(R.id.ivBtn_substract);
           itemAddBtn = (LinearLayout) itemView.findViewById(R.id.layout_AddBtn);
            layout_add_minusBtn = (LinearLayout) itemView.findViewById(R.id.layout_add_minusBtn);



        }
    }


    public void addFoodList(List<RestFood> foodList) {
        this.foodLists.addAll(foodList);
    }

    public void clearFoodList() {
        if (foodLists != null) {
            this.foodLists.clear();
        }
    }



    private void Insert_Update_Cart(String count) {

        sessionManager.setCartCount(count);
//
//        MenuItem item1 = this.menu.findItem(R.id.menu_main2_shopping_cart);
//        LayerDrawable icon1 = (LayerDrawable) item1.getIcon();
//        setBadgeCount(icon1, count);
    }

    private void setBadgeCount(LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public void setCallback(CallbackGroceryListItemSelectAdapter callback){
        this.callback=callback;
    }



}
