package snd.orgn.foodnfine.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.OnlinePaymentActivitySwadesiProdct;
import snd.orgn.foodnfine.base.BackHandledFragment;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;

public class wallet_add_money extends BackHandledFragment {

    private static final String TAG = "Wallet Add";
    CardView btn_submit;
    EditText add_amount;
    String amount, grand_total, walletbalance, uid;
    SessionManager sessionManager;
    String source, call, message;
    Boolean click = true;
    String walletAddAmount;
   // NetworkChangeReceiver networkChangeReceiver;
    Boolean network = false;

    public wallet_add_money() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
//        networkChangeReceiver = new NetworkChangeReceiver(getContext());
//        network = networkChangeReceiver.isNetworkAvailable();

        View view = inflater.inflate(R.layout.fragment_wallet_add_money, container, false);

        sessionManager = new SessionManager(getActivity().getApplication());

        //getActivity().setTitle("Add Money");

        btn_submit = view.findViewById(R.id.btn_submit_wallet_add_money);
        add_amount = view.findViewById(R.id.tv_walletAdd_addAmount);

        HashMap<String, String> data = sessionManager.getDetails();
        grand_total = data.get(SessionManager.GRAND_TOTAL);
        walletbalance = data.get(SessionManager.WALLET_BALANCE);
        uid = data.get(SessionManager.USER_ID);
        walletAddAmount = data.get(SessionManager.WALLET_ADD_AMOUNT);

        TextView txt = (TextView) view.findViewById(R.id.txt);
        txt.setPaintFlags(txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkvalidation()) {
                    if (network) {
                        sessionManager.redirect("add_money");
                        Intent intentOnline = new Intent(getActivity(), OnlinePaymentActivitySwadesiProdct.class);
                        intentOnline.putExtra("source", "add_money");
                        intentOnline.putExtra("amount", add_amount.getText().toString().trim());
                        //startActivityForResult(intentOnline, 1);
                        startActivity(intentOnline);
                        getActivity().finish();

                    } else {
                     //   startActivity(new Intent(getActivity(), NetworkConnection.class));
                    }
                }
            }
        });

        return view;
    }

    //font changing method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        FontChanger fontChanger = new FontChanger(getActivity().getAssets(), "ProximaNovaLight.otf");
//        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    private boolean checkvalidation() {
        amount = add_amount.getText().toString().trim();

        if (amount.isEmpty()) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "\uD83D\uDC5B Please enter valid amount", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.WHITE);
//            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.parseColor("#00585e"));
//            textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf"));
            snackbar.show();
        } else if (Integer.parseInt(amount) < 1) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "\uD83D\uDC5B Amount should be greater than 0", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.WHITE);
//            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.parseColor("#00585e"));
//            textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf"));
            snackbar.show();
        } else {
            return true;
        }
        return false;
    }


    @Override
    public String getTagText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
