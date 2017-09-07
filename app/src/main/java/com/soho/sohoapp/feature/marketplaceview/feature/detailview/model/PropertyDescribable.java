package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.feature.marketplaceview.model.Propertyable;

import java.util.Date;
import java.util.List;

/**
 * Created by chowii on 1/9/17.
 */

public interface PropertyDescribable extends Propertyable {

    int retrieveLandSize();
    String retrieveLandSizeMeasurement();
    Date retrieveAuctionDate();
    String retrieveDisplayableAuctionDate();
    Object retrieveAuctionLocation();
    List<String> retrieveRenovationDetails();

}
