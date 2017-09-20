package com.soho.sohoapp.feature.marketplaceview.components;

import com.soho.sohoapp.feature.home.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 14/8/17.
 */

interface MarketPlaceContract {

    interface ViewPresentable {
        void createPresentation();

        void startPresenting(Map<String, Object> searchParams);

        void stopPresenting();

        void destroyPresentation();

        void onRefresh(Map<String, Object> searchParams);
    }

    interface ViewInteractable {
        void configureTabLayout();

        void showRefreshing();

        void hideRefreshing();

        void configureAdapter(List<? extends BaseModel> model);

    }

}
