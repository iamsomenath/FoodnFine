package snd.orgn.foodnfine.adapter.fragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import snd.orgn.foodnfine.R;

public class QuickBiteFragAdapater  extends RecyclerView.Adapter<QuickBiteFragAdapater.ViewHolder> {
    private Activity activity;
    Context mContext;


    public QuickBiteFragAdapater(Activity activity) {
        this.activity = activity;
        this.mContext = activity.getApplicationContext();

    }

    @Override
    public QuickBiteFragAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modular_item_layout_quick_bites, parent, false);
        QuickBiteFragAdapater.ViewHolder vh = new QuickBiteFragAdapater.ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(QuickBiteFragAdapater.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// init the item view's
        TextView item_description;
        TextView itemPrice;
        LinearLayout itemAddBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            item_description = (TextView) itemView.findViewById(R.id.tv_quickbite_item_description);
            itemName = (TextView) itemView.findViewById(R.id.tv_quickbite_itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_quickbite_itemPrice);
            itemAddBtn = (LinearLayout) itemView.findViewById(R.id.layout_quickbite_addBtn);



        }
    }
}




