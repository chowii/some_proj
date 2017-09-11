package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.feature.marketplaceview.model.Propertyable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chowii on 1/9/17.
 */

public interface PropertyDescribable extends Propertyable {

    int retrieveLandSize();
    String retrieveLandSizeMeasurement();
    Calendar retrieveAuctionDate();
    String retrieveDisplayableAuctionDate();
    Object retrieveAuctionLocation();
    List<String> retrieveRenovationDetails();

}
