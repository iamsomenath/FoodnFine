package snd.orgn.foodnfine.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.payumoney.core.entity.Wallet;

import java.util.HashMap;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.OnlinePaymentActivity;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;

import static snd.orgn.foodnfine.constant.AppConstants.INTENT_STRING_TOTAL_PRICE;

public class payment_home extends Fragment {

    TextView btn_add_money, wallet_balance;
    CardView btn_submit_payment_home;
    private RadioGroup rg_payment_option;
    String payment = "null";
    SessionManager sessionManager;
    String w_balance;
    String grand_total, redirect, pay_total, vehicleNo;
    HashMap<String, String> data;
    RadioButton myRadioButton;

    public payment_home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_payment_home, container, false);

        getActivity().setTitle("Payment");
        sessionManager = new SessionManager(getActivity().getApplication());

        btn_submit_payment_home = view.findViewById(R.id.btn_submit_payment_home);
        btn_add_money = view.findViewById(R.id.btn_add_money_payment_home);
        rg_payment_option = view.findViewById(R.id.rg_payment_home);
        wallet_balance = view.findViewById(R.id.tv_payment_home_walletbalance);

        data = sessionManager.getDetails();
        w_balance = data.get(SessionManager.WALLET_BALANCE);
        grand_total = data.get(SessionManager.CART_VALUE);
        pay_total = data.get(SessionManager.PAYABLE_AMOUNT);
        redirect = data.get(SessionManager.REDIRECT);
        vehicleNo = data.get(SessionManager.VEHICLE_NO);

        wallet_balance.setText(w_balance);
        myRadioButton = (RadioButton) view.findViewById(R.id.rb_payment_option_cod);
        /*if (redirect.equals("subscribe") || data.get(SessionManager.REDIRECT_ADD).equalsIgnoreCase("subscribe")) {
            RadioButton myRadioButton = (RadioButton) view.findViewById(R.id.rb_payment_option_cod);
            myRadioButton.setVisibility(View.GONE);
        }*/

        btn_submit_payment_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId_meal = rg_payment_option.getCheckedRadioButtonId();
                switch (selectedId_meal) {
                    case R.id.rb_payment_option_wallet:
                        payment = "wallet";
                        break;
                    case R.id.rb_payment_option_op:
                        payment = "op";
                        break;
                    case R.id.rb_payment_option_cod:
                        payment = "cod";
                        break;
                    default:
                        payment = "null";
                        break;
                }

                if (payment.equals("cod")) {
                    sessionManager.successCall("payment_cod");
                    Fragment fragment = new payment_cod();
                    getFragmentManager().beginTransaction().replace(R.id.container_payment, fragment, "fragment_home")
                            .addToBackStack("cod_home")
                            .commit();
                    sessionManager.setPayType("COD");

                } else if (payment.equals("op")) {
                    sessionManager.successCall("payment_online");
                    Intent intent = new Intent(getActivity(), OnlinePaymentActivity.class);
                    intent.putExtra("amount", getActivity().getIntent().getStringExtra(INTENT_STRING_TOTAL_PRICE));
                    intent.putExtra("pay_amount", getActivity().getIntent().getStringExtra(INTENT_STRING_TOTAL_PRICE));
                    sessionManager.setPayType("Online");
                    startActivity(intent);
                }
            }
        });

        btn_add_money.setOnClickListener(v -> {

            sessionManager.addwalletCall("payment_home");
            sessionManager.message("add_wallet");
            sessionManager.redirectAdd(data.get(SessionManager.REDIRECT));
            Intent intent = new Intent(getActivity(), Wallet.class);
            intent.putExtra("source", "payment_home");
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            startActivity(intent);// we have to add start activiityfor result here to get the result
        });

        return view;
    }

    //font changing method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        FontChanger fontChanger = new FontChanger(getActivity().getAssets(), "ProximaNovaLight.otf");
//        fontChanger.replaceFonts((ViewGroup) this.getView());
    }
}
