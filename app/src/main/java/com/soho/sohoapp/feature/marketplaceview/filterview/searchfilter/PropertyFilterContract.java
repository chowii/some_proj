package com.soho.sohoapp.feature.marketplaceview.filterview.searchfilter;

import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

import java.util.List;

/**
 * Created by chowii on 22/8/17.
 */

public interface PropertyFilterContract {

    interface ViewPresentable {
        void startPresenting();

        void retrieveFilterFromApi();

        void stopPresenting();
    }

    interface ViewInteractable extends BaseViewInteractable {
        void toggleLoadingIndicator(boolean loading);
        void populateForm(MarketplaceFilterWithSuburbs filter, List<PropertyType> propertyTypes);
        void finishApplyingFilters();
    }
}
