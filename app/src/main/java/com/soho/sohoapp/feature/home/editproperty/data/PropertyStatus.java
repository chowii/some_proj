package com.soho.sohoapp.feature.home.editproperty.data;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        PropertyStatus.PRIVATE,
        PropertyStatus.DISCOVERABLE,
        PropertyStatus.SALE,
        PropertyStatus.RENT,
        PropertyStatus.AUCTION,
        PropertyStatus.SOLD,
        PropertyStatus.ARCHIVED
})

@Retention(RetentionPolicy.SOURCE)
public @interface PropertyStatus {
    String PRIVATE = "private";
    String DISCOVERABLE = "discoverable";
    String SALE = "sale";
    String RENT = "rent";
    String AUCTION = "auction";
    String SOLD = "sold";
    String ARCHIVED = "archived";
}