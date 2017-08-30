package com.soho.sohoapp.permission.eventbus;

public class PermissionEvent {

    private final int requestCode;
    private final boolean granted;

    public PermissionEvent(int requestCode, boolean granted) {
        this.requestCode = requestCode;
        this.granted = granted;
    }

    public boolean isPermissionGranted() {
        return granted;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
