package com.soho.sohoapp.feature.home.editproperty.data;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        PropertyStatus.PRIVATE,
        PropertyStatus.PUBLIC
})

@Retention(RetentionPolicy.SOURCE)
public @interface PropertyStatus {
    String PRIVATE = "private";
    String PUBLIC = "public";
}