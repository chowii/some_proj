package com.soho.sohoapp.feature.marketplaceview.components;

import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
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
        void destroyPresentation();
        void onRefresh();
        void saleTypeChanged(String saleType);

    }

    interface ViewInteractable {

        void configureTabLayout();
        void showRefreshing();
        void hideRefreshing();
        void showError(Throwable error);
        void configureAdapter(List<? extends BaseModel> model);
        void configureViewForFilter(MarketplaceFilterWithSuburbs currentFilter);

    }

}
