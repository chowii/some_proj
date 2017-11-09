package com.soho.sohoapp.data.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        PropertyUserRole.OWNER,
        PropertyUserRole.AGENT,
        PropertyUserRole.GUEST,

})

@Retention(RetentionPolicy.SOURCE)
public @interface PropertyUserRole {
    String OWNER = "owner";
    String AGENT = "agent";
    String GUEST = "guest";
}
