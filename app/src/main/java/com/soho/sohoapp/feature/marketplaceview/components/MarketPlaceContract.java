package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.annotation.Nullable;

import com.soho.sohoapp.data.models.BasicProperty;
import com.soho.sohoapp.data.models.PaginationInformation;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 14/8/17.
 */

interface MarketPlaceContract {

    interface ViewPresentable {
        void createPresentation();

        void startPresenting();

        void stopPresenting();

        void performFullRefresh();

        void retrieveFiltersAndPerformFullRefresh();

        void fetchNextPage(int page);

        void saleTypeChanged(String saleType);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void configureTabLayout();

        void showProgressBar();

        void hideProgressBar();

        void fullRefreshComplete(List<BasicProperty> properties, @Nullable PaginationInformation paginationInformation);

        void nextPageLoaded(List<BasicProperty> properties, @Nullable PaginationInformation paginationInformation);

        void configureViewForFilter(MarketplaceFilterWithSuburbs currentFilter);
    }
}
