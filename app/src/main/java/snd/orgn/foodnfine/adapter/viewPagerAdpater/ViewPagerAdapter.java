package snd.orgn.foodnfine.adapter.viewPagerAdpater;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import snd.orgn.foodnfine.fragment.Fragment_WalletHistory;
import snd.orgn.foodnfine.fragment.Fragment_wallet;

import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_WALLET_HISTORY;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;
    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context=context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return Fragment_wallet.newInstance("0",PAGE_TYPE_WALLET);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return Fragment_WalletHistory.newInstance("1", PAGE_TYPE_WALLET_HISTORY);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "Page " + position;
//    }

//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if (position == 0)
//        {
//            fragment = new FingerprintOrderListFragment();
//        }
//        else if (position == 1)
//        {
//            fragment = new UrnListFragment();
//        }
//
//        return fragment;
//    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = PAGE_TYPE_WALLET;
        } else if (position == 1) {
            title = PAGE_TYPE_WALLET_HISTORY;
        }

        return title;
    }


}


