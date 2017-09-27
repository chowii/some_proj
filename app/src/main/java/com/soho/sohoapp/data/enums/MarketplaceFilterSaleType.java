package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        MarketplaceFilterSaleType.SALE,
        MarketplaceFilterSaleType.RENT
})

@Retention(RetentionPolicy.SOURCE)
public @interface MarketplaceFilterSaleType {
    String SALE = "sale/auction";
    String RENT = "rent";
}