package snd.orgn.foodnfine.adapter.fragmentAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import snd.orgn.foodnfine.R;

public class MealsFragAdpater extends RecyclerView.Adapter<MealsFragAdpater.ViewHolder> {
    private Activity activity;


    public MealsFragAdpater(Activity activity) {
        this.activity = activity;

    }

    @Override
    public MealsFragAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modular_item_layout_meals, parent, false);
        MealsFragAdpater.ViewHolder vh = new MealsFragAdpater.ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MealsFragAdpater.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// init the item view's
        TextView item_description;
        TextView itemPrice;
        LinearLayout itemAddBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            item_description = (TextView) itemView.findViewById(R.id.tv_meals_item_description);
            itemName = (TextView) itemView.findViewById(R.id.tv_meals_itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_meals_itemPrice);
            itemAddBtn = (LinearLayout) itemView.findViewById(R.id.layout_meals_addBtn);



        }
    }




}