package snd.orgn.foodnfine.adapter.activityAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.activity.NewSwadesiProductActivity
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsList

class SwadesiHeadesRecyclerView(private val activity: Activity, private var itemArrayList: MutableList<HouseholdEssentialsItemsList>,
                                private val selectPos: Int, private val btnSelect: NewSwadesiProductActivity.BtnSelect? = null) :
        RecyclerView.Adapter<SwadesiHeadesRecyclerView.MyViewHolder>() {

    private var row_index = selectPos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_item_layout, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_header_name.text = itemArrayList[position].subcategory_name
        holder.header_layout.tag = position

        holder.header_layout.setOnClickListener {
            if (btnSelect != null) {
                btnSelect.onBtnSelectValue(holder.header_layout.tag as Int)
                row_index = position
                notifyDataSetChanged()
            }
        }

        if (row_index == position) {
            holder.tv_header_name.setTextColor(Color.parseColor("#FB8203"));
        } else {
            holder.tv_header_name.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    override fun getItemCount(): Int {
        return itemArrayList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        internal var tv_header_name: TextView = itemview.findViewById<View>(R.id.tv_header_name) as TextView
        internal var header_layout: LinearLayout = itemview.findViewById<View>(R.id.header_layout) as LinearLayout
    }
}
