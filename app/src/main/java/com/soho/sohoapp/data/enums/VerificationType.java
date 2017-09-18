package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        VerificationType.NEW,
        VerificationType.NOT_VERIFIED,
        VerificationType.PENDING
})

@Retention(RetentionPolicy.SOURCE)
public @interface VerificationType {
    String NEW = "new";
    String NOT_VERIFIED = "not_verified";
    String PENDING = "pending";
}