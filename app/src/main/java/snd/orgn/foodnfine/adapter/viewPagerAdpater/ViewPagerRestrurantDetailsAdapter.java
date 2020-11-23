package snd.orgn.foodnfine.adapter.viewPagerAdpater;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import snd.orgn.foodnfine.fragment.RestourantFragments;
import snd.orgn.foodnfine.fragment.GroceryFragments;
import snd.orgn.foodnfine.fragment.MealsFragment;
import snd.orgn.foodnfine.fragment.Quickbites_Fragment;

import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_BREAK_FAST;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_DESSERTS;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_MEALS;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_QUICK_BITES;

public class ViewPagerRestrurantDetailsAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 4;
    private Context context;

    public ViewPagerRestrurantDetailsAdapter(FragmentManager fragmentManager, Context context) {
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
                return RestourantFragments.newInstance("0",PAGE_TYPE_BREAK_FAST);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return Quickbites_Fragment.newInstance("1", PAGE_TYPE_QUICK_BITES);
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return MealsFragment.newInstance("2", PAGE_TYPE_MEALS);
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return GroceryFragments.newInstance("3", PAGE_TYPE_DESSERTS);

            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = PAGE_TYPE_BREAK_FAST;
        }
        else if (position == 1) {
            title = PAGE_TYPE_QUICK_BITES;
        }

        else if (position == 2) {
            title = PAGE_TYPE_MEALS;
        }
        else if (position == 3) {
            title = PAGE_TYPE_DESSERTS;
        }

        return title;
    }


}
