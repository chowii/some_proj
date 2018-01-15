package com.soho.sohoapp.permission;

import android.content.Context;

import com.soho.sohoapp.permission.eventbus.PermissionEvent;

import io.reactivex.Observable;


public class PermissionManagerImpl implements PermissionManagerInterface {

    private final Context context;

    private PermissionManagerImpl(Context context) {
        this.context = context;
    }

    public static PermissionManagerImpl newInstance(Context context) {
        return new PermissionManagerImpl(context);
    }

    @Override
    public boolean hasStoragePermission() {
        return PermissionActivity.hasPermission(context, Permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    public Observable<PermissionEvent> requestStoragePermission(int requestCode) {
        return PermissionActivity.start(context, Permission.WRITE_EXTERNAL_STORAGE, requestCode);
    }

    @Override
    public boolean hasCameraPermission() {
        return PermissionActivity.hasPermission(context, Permission.CAMERA);
    }

    @Override
    public Observable<PermissionEvent> requestCameraPermission(int requestCode) {
        return PermissionActivity.start(context, Permission.CAMERA, requestCode);
    }
}