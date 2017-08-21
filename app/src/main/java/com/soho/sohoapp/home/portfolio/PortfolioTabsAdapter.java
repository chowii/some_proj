package com.soho.sohoapp.home.portfolio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.portfolio.owner.PortfolioOwnerFragment;

public class PortfolioTabsAdapter extends FragmentPagerAdapter {

    private static final int ITEM_ONE = 0;
    private static final int ITEM_TWO = 1;
    private static final int ITEM_THREE = 2;
    private static final int TABS_COUNT = 3;

    private final Context context;

    public PortfolioTabsAdapter(@NonNull Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ITEM_ONE:
                return PortfolioOwnerFragment.newInstance();
            case ITEM_TWO:
                return PortfolioOwnerFragment.newInstance();
            case ITEM_THREE:
                return PortfolioOwnerFragment.newInstance();
            default:
                return PortfolioOwnerFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ITEM_ONE:
                return context.getString(R.string.portfolio_owner);
            case ITEM_TWO:
                return context.getString(R.string.portfolio_manager);
            case ITEM_THREE:
                return context.getString(R.string.portfolio_requests);
            default:
                return null;

        }
    }
}
