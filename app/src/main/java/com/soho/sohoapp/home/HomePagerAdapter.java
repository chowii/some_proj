package com.soho.sohoapp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soho.sohoapp.home.more.MoreFragment;

/**
 * Created by Jovan on 11/8/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        //TODO: Return correct fragment for position @Sabbib
        return MoreFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}