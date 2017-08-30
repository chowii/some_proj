package com.soho.sohoapp.permission;

import com.soho.sohoapp.permission.eventbus.PermissionEvent;

import rx.Observable;

public interface PermissionManager {
    boolean hasStoragePermission();

    Observable<PermissionEvent> requestStoragePermission(int requestCode);
}
