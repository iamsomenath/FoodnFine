package snd.orgn.foodnfine.adapter.activityAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsList
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsPojo
import java.util.*

class SwadesiMainAdapter(private val activity: Activity, internal var mainArrayList: ArrayList<HouseholdEssentialsItemsList>,
                         private val menu: Menu) : RecyclerView.Adapter<SwadesiMainAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_swadesi_main_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.restaurantCategory.text = mainArrayList[position].subcategory_name
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerViewItemLayout.layoutManager = layoutManager
        holder.recyclerViewItemLayout.setHasFixedSize(true)
        val itemArrayList = ArrayList<HouseholdEssentialsItemsPojo>()
        itemArrayList.addAll(mainArrayList[position].productlist!!)

        val swadesiItemAdapter = SwadesiItemAdapter(activity, activity, itemArrayList)
        holder.recyclerViewItemLayout.adapter = swadesiItemAdapter
        swadesiItemAdapter.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mainArrayList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        internal var recyclerViewItemLayout: RecyclerView
        internal var restaurantCategory: TextView

        init {
            recyclerViewItemLayout = itemview.findViewById<View>(R.id.rv_categoryItemList) as RecyclerView
            restaurantCategory = itemview.findViewById<View>(R.id.categoryName) as TextView
        }
    }
}