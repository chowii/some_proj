package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.editproperty.connections.EditConnectionFragment;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.overview.EditOverviewFragment;

class EditPropertyTabsAdapter extends FragmentPagerAdapter {
    private static final int ITEM_ONE = 0;
    private static final int ITEM_TWO = 1;
    private static final int ITEM_THREE = 2;
    private static final int TABS_COUNT = 3;

    private final Context context;
    private final Property property;

    public EditPropertyTabsAdapter(@NonNull Context context, @NonNull FragmentManager fm, @NonNull Property property) {
        super(fm);
        this.context = context;
        this.property = property;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ITEM_ONE:
                return EditOverviewFragment.newInstance(property);
            case ITEM_TWO:
                return EditConnectionFragment.newInstance();
            case ITEM_THREE:
                return EditConnectionFragment.newInstance();
            default:
                return null;
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
                return context.getString(R.string.edit_property_overview);
            case ITEM_TWO:
                return context.getString(R.string.edit_property_connection);
            case ITEM_THREE:
                return context.getString(R.string.edit_property_files);
            default:
                return null;

        }
    }
}