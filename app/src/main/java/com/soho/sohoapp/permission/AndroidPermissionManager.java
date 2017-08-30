package com.soho.sohoapp.permission;

import android.content.Context;

import com.soho.sohoapp.permission.eventbus.PermissionEvent;

import rx.Observable;

public class AndroidPermissionManager implements PermissionManager {

    private final Context context;

    private AndroidPermissionManager(Context context) {
        this.context = context;
    }

    public static AndroidPermissionManager newInstance(Context context) {
        return new AndroidPermissionManager(context);
    }

    @Override
    public boolean hasStoragePermission() {
        return PermissionActivity.hasPermission(context, Permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    public Observable<PermissionEvent> requestStoragePermission(int requestCode) {
        return PermissionActivity.start(context, Permission.WRITE_EXTERNAL_STORAGE, requestCode);
    }
}
