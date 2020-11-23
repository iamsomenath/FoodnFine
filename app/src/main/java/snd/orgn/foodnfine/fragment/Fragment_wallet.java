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

import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET;

/**

 * create an instance of this fragment.
 */
public class Fragment_wallet extends BaseFragment {

    View parentView;
    private static ViewPager viewpager;
    private int page;
    private String title;
    Unbinder unbinder;

    boolean firstLoad;
    public Fragment_wallet() {
        // Required empty public constructor
    }

    public static Fragment_wallet newInstance(String page, String title) {
        Fragment_wallet fragmentFirst = new Fragment_wallet();
        Bundle args = new Bundle();
        args.putString("0", page);
        args.putString(PAGE_TYPE_WALLET, title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("0", 0);
        title = getArguments().getString(PAGE_TYPE_WALLET);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        parentView = inflater.inflate(R.layout.fragment_fragment_wallet, container, false);

        unbinder = ButterKnife.bind(this, parentView);

        firstLoad = true;
        return parentView;
    }


}
