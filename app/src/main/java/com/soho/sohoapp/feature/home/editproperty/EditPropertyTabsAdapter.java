package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.comingsoon.ComingSoonFragment;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.editproperty.files.EditPropertyFilesFragment;
import com.soho.sohoapp.feature.home.editproperty.overview.EditOverviewFragment;

import java.util.ArrayList;

class EditPropertyTabsAdapter extends FragmentPagerAdapter {
    private static final int ITEM_ONE = 0;
    private static final int ITEM_TWO = 1;
    private static final int ITEM_THREE = 2;
    private static final int TABS_COUNT = 3;

    private final Context context;
    private final Property property;
    private final ArrayList<PropertyType> propertyTypes;

    public EditPropertyTabsAdapter(@NonNull Context context,
                                   @NonNull FragmentManager fm,
                                   @NonNull Property property,
                                   @NonNull ArrayList<PropertyType> propertyTypes) {
        super(fm);
        this.context = context;
        this.property = property;
        this.propertyTypes = propertyTypes;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ITEM_ONE:
                return EditOverviewFragment.newInstance(property, propertyTypes);
            case ITEM_TWO:
                return ComingSoonFragment.newInstance();
            case ITEM_THREE:
                return EditPropertyFilesFragment.Companion.newInstance(property);
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