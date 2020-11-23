package snd.orgn.foodnfine.adapter.fragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.callbacks.CallbackSelectedPackages;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;

public class SelectPackageAdapter extends RecyclerView.Adapter<SelectPackageAdapter.ViewHolder> {
    private Activity activity;
    private Context mContest;
    private CallbackSelectedPackages callback;
    private List<PackageDetails> packageDetailsList;
    private List<String> seletedItemArray;


    public SelectPackageAdapter(Activity activity, Context ctx) {
        this.activity = activity;
        this.mContest = ctx;
        packageDetailsList = new ArrayList<>();
        seletedItemArray = new ArrayList<>();
    }

    @Override
    public SelectPackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_package_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectPackageAdapter.ViewHolder holder, final int position) {
        holder.itemName.setText(packageDetailsList.get(position).getName());
        holder.selectItem.setOnCheckedChangeListener(null);
        holder.selectItem.setChecked(packageDetailsList.get(position).isSelected());
        holder.selectItem.setTag(position);
        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.selectItem.getTag();
            //    Toast.makeText(mContest, packageDetailsList.get(pos).getName() + " clicked!", Toast.LENGTH_SHORT).show();

                if ( packageDetailsList.get(position).isSelected()) {
                    packageDetailsList.get(position).setSelected(false);
                    seletedItemArray.remove(packageDetailsList.get(position).getName());

                } else {
                    packageDetailsList.get(position).setSelected(true);
                    seletedItemArray.add(packageDetailsList.get(position).getName());
                }
                callback.onItemsSelected(seletedItemArray);
            }
        });


//        holder.selectItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    packageDetailsList.get(position).setSelected(true);
//                    seletedItemArray.add(packageDetailsList.get(position).getName());
//                } else {
//                    packageDetailsList.get(position).setSelected(false);
//                    seletedItemArray.remove(packageDetailsList.get(position).getName());
//                }
//            }
//
//        });
//        holder.selectItem.setChecked(packageDetailsList.get(position).isSelected());
//        callback.onItemsSelected(seletedItemArray);


    }

    @Override
    public int getItemCount() {
        return packageDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// init the item view's
        CheckBox selectItem;


        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.tv_package_content_item);
            selectItem = (CheckBox) itemView.findViewById(R.id.cb_select_package);
        }
    }


    public void setCallback(CallbackSelectedPackages callback) {
        this.callback = callback;
    }

    public void addPackageList(List<PackageDetails> packageDetails) {
        this.packageDetailsList.addAll(packageDetails);
    }

    public void clearListData() {
        this.packageDetailsList.clear();
    }


}




