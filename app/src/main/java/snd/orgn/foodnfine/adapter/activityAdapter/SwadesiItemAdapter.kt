package snd.orgn.foodnfine.adapter.activityAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.activity.SwadesiDetailsActivity
import snd.orgn.foodnfine.application.FoodnFine
import snd.orgn.foodnfine.data.shared_presferences.SessionManager
import snd.orgn.foodnfine.helper.other.BadgeDrawable
import snd.orgn.foodnfine.model.data_item.Cart_Details
import snd.orgn.foodnfine.model.data_item.HouseholdEssentialsItemsPojo
import java.util.*


class SwadesiItemAdapter(private val context: Context, private val activity: Activity,
                         internal var itemArrayList: ArrayList<HouseholdEssentialsItemsPojo>)
    : RecyclerView.Adapter<SwadesiItemAdapter.MyViewHolder>() {

    private val sessionManager: SessionManager
    private val uid: String?
    internal var data = HashMap<String, String>()


    init {
        sessionManager = SessionManager(context)
        //Retriving Data
        //data = sessionManager.details
        uid = FoodnFine.appSharedPreference!!.userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_swadesi_item_category_list, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = " " + itemArrayList[position].product_name!!
        holder.brand.text = " " + itemArrayList[position].company_name!!
        holder.price.text = "₹" + itemArrayList[position].price!!
        holder.price2.text = "₹" + itemArrayList[position].offer_price!!

        /*if (!album.getRegular_price().equalsIgnoreCase(album.getSell_price())) {
            holder.sprice.setText("₹" + album.getSell_price());
            holder.rprice.setPaintFlags(holder.rprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.sprice.setVisibility(View.GONE);
            holder.rprice.setTextColor(Color.parseColor("#8BC34A"));
        }*/
        holder.price.paintFlags = holder.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        Picasso.get().load(itemArrayList[position].product_image)
                .placeholder(R.drawable.new_logo)
                //.transform(new CircleTransform())
                .into(holder.itemImage)

        holder.grid_card.setOnClickListener {
            val intent = Intent(activity, SwadesiDetailsActivity::class.java)
            intent.putExtra("product_id", itemArrayList[position].product_id)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemArrayList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        internal var itemImage: RoundedImageView
        internal var name: TextView
        internal var brand: TextView
        internal var price: TextView
        internal var price2: TextView
        internal var add_to_cart: TextView
        internal var ivbtn_plus: ImageView
        internal var ivbtn_minus: ImageView
        internal val quantity: TextView
        internal val grid_card : CardView
        internal var layout_detail: LinearLayout
        internal var img_unavail: ImageView

        init {
            itemImage = itemview.findViewById<View>(R.id.icon) as RoundedImageView
            name = itemview.findViewById<View>(R.id.name) as TextView
            brand = itemview.findViewById<View>(R.id.brand) as TextView
            price = itemview.findViewById<View>(R.id.price) as TextView
            price2 = itemview.findViewById<View>(R.id.price2) as TextView
            add_to_cart = itemview.findViewById<View>(R.id.add_to_cart) as TextView
            ivbtn_plus = itemview.findViewById<View>(R.id.plus) as ImageView
            ivbtn_minus = itemview.findViewById<View>(R.id.minus) as ImageView
            img_unavail = itemview.findViewById<View>(R.id.img_unavail) as ImageView
            quantity = itemview.findViewById<View>(R.id.quantity) as TextView
            grid_card = itemview.findViewById<View>(R.id.grid_card) as CardView

            val display = activity.windowManager.defaultDisplay
            layout_detail = itemview.findViewById(R.id.layout_detail) as LinearLayout
            val layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.height = dpToPx(250)
            layoutParams.width = display.width * 5 / 11
            layout_detail.layoutParams = layoutParams
        }
    }

    fun dpToPx(dp: Int): Int {
        val density = activity.resources
                .displayMetrics
                .density
        return Math.round(dp.toFloat() * density)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


   /* private fun Insert_Update_Cart(count: String) {

        sessionManager.setCartCount(count)

        val item1 = this.menu.findItem(R.id.menu_main2_shopping_cart)
        val icon1 = item1.icon as LayerDrawable
        setBadgeCount(icon1, count)
    }*/

    private fun setBadgeCount(icon: LayerDrawable, count: String) {

        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        if (reuse != null && reuse is BadgeDrawable) {
            badge = reuse as BadgeDrawable
        } else {
            badge = BadgeDrawable(context)
        }

        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }

    private fun ShowSnackMessage(s: String) {
        val snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), s, 5000)
                .setAction("View Cart", View.OnClickListener {
                    val intent = Intent(activity, Cart_Details::class.java)
                    //intent.putExtra("source", "DetailsViewItem");
                    sessionManager.addCall("Restaurant")
                    activity.startActivity(intent)
                    activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                })
        val snackbarView = snackbar.getView()
        snackbarView.setBackgroundColor(Color.WHITE)
        snackbar.show()
    }
}
