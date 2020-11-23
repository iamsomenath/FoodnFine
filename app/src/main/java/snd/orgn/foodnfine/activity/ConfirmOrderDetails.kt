package snd.orgn.foodnfine.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_confirm_order_details.*
import snd.orgn.foodnfine.R
import snd.orgn.foodnfine.application.DeliveryEverything

class ConfirmOrderDetails : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order_details)

        foodcharge.text = "₹" + intent.getStringExtra("PRICE")
        deliverycharge.text = "₹" + Math.floor(DeliveryEverything.getAppSharedPreference().deliveryCost.toDouble())
        cancellationcharge.text = "₹" + intent.getStringExtra("CANCEL")
        discountcharge.text = "₹" + intent.getStringExtra("DIS")
        fixedcharge.text = "₹" + intent.getStringExtra("FC")
        total.text = "₹" + intent.getStringExtra("TOTAL")

        iv_back.setOnClickListener {
            finish()
        }
    }
}