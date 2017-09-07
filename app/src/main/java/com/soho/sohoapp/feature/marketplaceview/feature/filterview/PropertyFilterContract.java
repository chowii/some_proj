package com.soho.sohoapp.feature.marketplaceview.feature.filterview;


import com.soho.sohoapp.feature.home.BaseFormModel;

import java.util.List;

/**
 * Created by chowii on 22/8/17.
 */

public interface PropertyFilterContract {

    interface ViewPresentable{
        void startPresenting();
        void retrieveFilterFromApi();
        void stopPresenting();
    }

    interface ViewInteractable{
        void configureAdapter(List<? extends BaseFormModel> formModelList);
    }
}
