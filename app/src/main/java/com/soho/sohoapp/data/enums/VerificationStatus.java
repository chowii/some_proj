package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        VerificationStatus.NEW,
        VerificationStatus.NOT_VERIFIED,
        VerificationStatus.PENDING,
        VerificationStatus.VERIFIED
})

@Retention(RetentionPolicy.SOURCE)
public @interface VerificationStatus {
    String NEW = "new";
    String NOT_VERIFIED = "not_verified";
    String PENDING = "pending";
    String VERIFIED = "verified";
}