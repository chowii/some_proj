package com.soho.sohoapp.feature.marketplace;

import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 14/8/17.
 */

interface MarketPlaceContract {

    interface ViewPresentable {
        void createPresentation();
        void startPresenting(boolean isBuySection);
        void stopPresenting();
        void destroyPresentation();
        void onRefresh(boolean isBuySection);
    }

    interface ViewInteractable {
        void configureTabLayout();
        void showRefreshing();
        void hideRefreshing();
        void configureAdapter(List<? extends BaseModel> model);
    }

}
