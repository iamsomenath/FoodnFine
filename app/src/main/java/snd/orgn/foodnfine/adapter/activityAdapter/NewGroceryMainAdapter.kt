package snd.orgn.foodnfine.adapter.activityAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikartm.support.ImageBadgeView
import snd.orgn.foodnfine.model.GroceryItemList
import snd.orgn.foodnfine.model.GroceryItemPojo
import snd.orgn.foodnfine.R
import java.util.*

class NewGroceryMainAdapter(private val activity: Activity, internal var mainArrayList: ArrayList<GroceryItemList>,
                            private val imageView: ImageBadgeView, private val emptyCart: FrameLayout):
        RecyclerView.Adapter<NewGroceryMainAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_swadesi_main_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.restaurantCategory.text = mainArrayList[position].category_name
        val layoutManager = LinearLayoutManager(activity)
        holder.recyclerViewItemLayout.layoutManager = layoutManager
        holder.recyclerViewItemLayout.setHasFixedSize(true)
        val itemArrayList = ArrayList<GroceryItemPojo>()
        itemArrayList.addAll(mainArrayList[position].grocery_menu_list!!)

        val myItemAdapter = NewGroceryItemAdapter(activity, activity, itemArrayList, imageView, emptyCart)
        holder.recyclerViewItemLayout.adapter = myItemAdapter
        myItemAdapter.notifyDataSetChanged()
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
