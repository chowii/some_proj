package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract;

import com.soho.sohoapp.feature.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 1/9/17.
 */

public interface PropertyDetailContract {

    interface ViewPresentable{
        void startPresenting();
        void retrieveProperty(int id);
        void stopPresenting();
    }

    interface ViewInteractable {
        void configureAdapter(List<BaseModel> model);
    }

}
