package snd.orgn.foodnfine.adapter.activityAdapter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.model.OfferPojo


class OfferAdapter(private val menuitem: List<OfferPojo>, private val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offers_items, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val offerPojo = menuitem[position]

            holder.mytext1.text = "  " + offerPojo.description!!
            holder.mytext2.text = "  " + offerPojo.code!!.toUpperCase()
            if (offerPojo.discount_type.equals("flat"))
                holder.mytext3.text = "  Flat \u20B9" + offerPojo.discount_amt
            else
                holder.mytext3.text = " " + offerPojo.discount_amt + "%"
            holder.mytext4.text = "  \u20B9" + offerPojo.usage_limit
            holder.code.text = offerPojo.code
            holder.mytext8.text = offerPojo.shop_name
            holder.mytext9.text = offerPojo.user_coupon_count + " times"
            holder.mytext7.text = " " + offerPojo.main_category_name!!.toUpperCase()
            holder.mytext5.text = " Expires On " + offerPojo.validityEnd!!

            holder.code.setOnClickListener {
                val clipboard: ClipboardManager? = activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val myClip: ClipData = ClipData.newPlainText("text", offerPojo.code)
                clipboard!!.setPrimaryClip(myClip)
                val item: ClipData.Item = myClip.getItemAt(0)
                val text = item.text.toString()
                Toast.makeText(activity, "$text Code Copied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun getItemCount(): Int {
        return menuitem.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mytext1: TextView
        internal var mytext2: TextView
        internal var mytext3: TextView
        internal var mytext4: TextView
        internal var mytext5: TextView
        internal var mytext7: TextView
        internal var mytext8: TextView
        internal var mytext9: TextView
        internal var code: TextView
        internal var cardView: CardView

        init {
            mytext1 = itemView.findViewById<View>(R.id.mytext1) as TextView
            mytext2 = itemView.findViewById<View>(R.id.mytext2) as TextView
            mytext3 = itemView.findViewById<View>(R.id.mytext3) as TextView
            mytext4 = itemView.findViewById<View>(R.id.mytext4) as TextView
            mytext5 = itemView.findViewById<View>(R.id.mytext5) as TextView
            mytext7 = itemView.findViewById<View>(R.id.mytext7) as TextView
            mytext8 = itemView.findViewById<View>(R.id.mytext8) as TextView
            mytext9 = itemView.findViewById<View>(R.id.mytext9) as TextView
            code = itemView.findViewById<View>(R.id.code) as TextView
            cardView = itemView.findViewById<View>(R.id.card_view) as CardView
        }
    }
}


