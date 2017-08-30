package com.soho.sohoapp.permission;

import android.Manifest;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        Permission.WRITE_EXTERNAL_STORAGE
})

@Retention(RetentionPolicy.SOURCE)
public @interface Permission {
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}