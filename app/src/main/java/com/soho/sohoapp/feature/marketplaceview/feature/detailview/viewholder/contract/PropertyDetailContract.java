package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;
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

    interface ViewInteractable extends BaseViewInteractable {
        void configureAdapter(List<BaseModel> model);
        void populateView(Property property);
        void setRefreshing(boolean isRefreshing);
    }

}
