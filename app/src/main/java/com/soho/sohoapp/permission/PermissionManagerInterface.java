package com.soho.sohoapp.permission;

import com.soho.sohoapp.permission.eventbus.PermissionEvent;

import io.reactivex.Observable;

public interface PermissionManagerInterface {
    boolean hasStoragePermission();

    Observable<PermissionEvent> requestStoragePermission(int requestCode);

    boolean hasCameraPermission();

    Observable<PermissionEvent> requestCameraPermission(int requestCode);
}
