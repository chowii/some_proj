package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        VerificationType.LICENCE,
        VerificationType.AGENT_LICENCE,
        VerificationType.MOBILE_NUMBER,
        VerificationType.PROPERTY
})

@Retention(RetentionPolicy.SOURCE)
public @interface VerificationType {
    String LICENCE = "LicenceVerification";
    String AGENT_LICENCE = "AgentLicenceVerification";
    String MOBILE_NUMBER = "MobileVerification";
    String PROPERTY = "PropertyVerification";
}
