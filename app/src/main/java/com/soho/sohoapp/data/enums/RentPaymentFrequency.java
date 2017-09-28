package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        RentPaymentFrequency.WEEKLY,
        RentPaymentFrequency.FORTNIGHTLY,
        RentPaymentFrequency.MONTHLY
})

@Retention(RetentionPolicy.SOURCE)
public @interface RentPaymentFrequency {
    String WEEKLY = "weekly";
    String FORTNIGHTLY = "fortnightly";
    String MONTHLY = "monthly";
}
