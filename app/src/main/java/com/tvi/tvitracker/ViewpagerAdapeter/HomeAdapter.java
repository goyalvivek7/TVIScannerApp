package com.tvi.tvitracker.ViewpagerAdapeter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tvi.tvitracker.Fragment.HomeFragment;
import com.tvi.tvitracker.Fragment.ProfileFragment;
import com.tvi.tvitracker.Fragment.SettingFragment;

public class HomeAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public HomeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
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
            case 0:
                return new ProfileFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new SettingFragment();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
