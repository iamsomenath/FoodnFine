package snd.orgn.foodnfine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;
import snd.orgn.foodnfine.util.LoadingDialog;

public class wallet_home extends Fragment {

    private static final String TAG = "wallet_home";
    LinearLayout btn_back_home;
    TextView btn_add_money_wallet, wallet_balance;
    SessionManager sessionManager;
    String uid;
    LoadingDialog loadingDialog;

    public wallet_home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_home, container, false);

        //getActivity().setTitle("My Wallet");

        btn_back_home = view.findViewById(R.id.btn_back_home);
        btn_add_money_wallet = view.findViewById(R.id.btn_add_money_payment_wallet);
        wallet_balance = view.findViewById(R.id.tv_walletHome_walletBalance);
        sessionManager = new SessionManager(getActivity());

        loadingDialog = new LoadingDialog(getContext());

        HashMap<String, String> data = sessionManager.getDetails();
        uid = data.get(SessionManager.USER_ID);

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//                getActivity().finish();
            }
        });

        btn_add_money_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.addwalletCall("wallet_home");
                sessionManager.message("add_wallet");
                Fragment fragment = new wallet_add_money();
//                getFragmentManager().beginTransaction().replace(R.id.container_wallet, fragment, "app_wallet_home")
//                        .addToBackStack("wallet_home")
//                        .commit();
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

    public void getMoneyWallet() {

//        loadingDialog.showDialog();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        final AppInterface services = retrofit.create(AppInterface.class);
//
//        final Call<List<pojo_checkWallet>> wallet_bal = services.checkWallet(uid);
//        wallet_bal.enqueue(new Callback<List<pojo_checkWallet>>() {
//            @Override
//            public void onResponse(Call<List<pojo_checkWallet>> callRequest, Response<List<pojo_checkWallet>> response) {
//                if (response.body() != null) {
//                    int wbvalue = 0;
//                    for (int i = 0; i < response.body().size(); i++) {
//                        String WBalance = response.body().get(i).getWBalance();
//                        wbvalue = Integer.parseInt(WBalance);
//                        sessionManager.walletBalance(WBalance);
//                    }
//                    wallet_balance.setText("\u20B9 " + wbvalue);
//                    wallet_balance.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "ProximaNovaLight.otf"));
//                    loadingDialog.hideDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<pojo_checkWallet>> callRequest, Throwable t) {
//                loadingDialog.hideDialog();
//                //Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getMoneyWallet();
    }

}
