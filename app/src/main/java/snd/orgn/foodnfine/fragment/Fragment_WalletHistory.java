package snd.orgn.foodnfine.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseFragment;

import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET_HISTORY;

/**
 * create an instance of this fragment.
 */
public class Fragment_WalletHistory extends BaseFragment {
    View parentView;
    private static ViewPager viewpager;
    boolean firstLoad;
    private int page;
    private String title;

    Unbinder unbinder;



    public Fragment_WalletHistory() {
        // Required empty public constructor
    }


    public static Fragment_WalletHistory newInstance(String page, String title) {
        Fragment_WalletHistory fragmentSecond = new Fragment_WalletHistory();
        Bundle args = new Bundle();
        args.putString("1", page);
        args.putString(PAGE_TYPE_WALLET_HISTORY, title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt("1", 0);
            title = getArguments().getString(PAGE_TYPE_WALLET_HISTORY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_fragment__wallet_history, container, false);


        unbinder = ButterKnife.bind(this, parentView);

        firstLoad=true;
        page = 1;
        // Inflate the layout for this fragment
        return parentView;
    }


}
