package com.soho.sohoapp.feature.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soho.sohoapp.feature.home.more.MoreFragment;
import com.soho.sohoapp.feature.home.portfolio.PortfolioFragment;
import com.soho.sohoapp.feature.marketplaceview.components.MarketPlaceFragment;

/**
 * Created by Jovan on 11/8/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MarketPlaceFragment.newInstance();
            case 1:
                return PortfolioFragment.newInstance();
        }
        return MoreFragment.Companion.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}