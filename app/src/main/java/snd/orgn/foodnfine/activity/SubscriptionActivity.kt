package snd.orgn.foodnfine.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_subscription.*
import snd.orgn.foodnfine.R

class SubscriptionActivity : AppCompatActivity() {

    private var isSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        normal_layout.setOnClickListener {
            isSelected = 1
            // for normal_layout
            is_unchecked1.visibility = View.GONE
            is_checked1.visibility = View.VISIBLE
            // for platinum_layout
            is_unchecked2.visibility = View.VISIBLE
            is_checked2.visibility = View.GONE
        }
        platinum_layout.setOnClickListener {
            isSelected = 2
            // for normal_layout
            is_unchecked1.visibility = View.VISIBLE
            is_checked1.visibility = View.GONE
            // for platinum_layout
            is_unchecked2.visibility = View.GONE
            is_checked2.visibility = View.VISIBLE
        }
        proceed.setOnClickListener {
            if (isSelected == 0) {
                val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Please select your membership choice", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "You selected choice $isSelected", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
        iv_accountDetails_back.setOnClickListener { v -> super.onBackPressed() }
        terms.setOnClickListener {
            val snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Terms & Conditions", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}
