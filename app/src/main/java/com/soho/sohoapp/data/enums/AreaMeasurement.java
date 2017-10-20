package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        AreaMeasurement.SQM,
        AreaMeasurement.SQFT
})

@Retention(RetentionPolicy.SOURCE)
public @interface AreaMeasurement {
    String SQM = "sqm";
    String SQFT = "sqft";
}
