package com.soho.sohoapp.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.soho.sohoapp.Dependencies;
import com.soho.sohoapp.permission.eventbus.PermissionEvent;

import rx.Observable;

public class PermissionActivity extends AppCompatActivity {

    private String permission;
    private int requestCode;

    private static final class Extras {
        public static final String PERMISSION = "PERMISSION";
        public static final String REQUEST_CODE = "REQUEST_CODE";
    }

    @NonNull
    public static Observable<PermissionEvent> start(@NonNull Context context, @Permission String permission, int requestCode) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(Extras.PERMISSION, permission);
        intent.putExtra(Extras.REQUEST_CODE, requestCode);
        context.startActivity(intent);
        return Dependencies.INSTANCE.getEventBus().observe(PermissionEvent.class)
                .filter(permissionEvent -> permissionEvent.getRequestCode() == requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permission = getIntent().getStringExtra(Extras.PERMISSION);
        requestCode = getIntent().getIntExtra(Extras.REQUEST_CODE, 0);
        showRequestPermissionDialog();
    }

    @Override
    public void onRequestPermissionsResult(int currentRequestCode, @NonNull String permissions[], @NonNull int... grantResults) {
        if (requestCode == currentRequestCode && permissionGranted(grantResults)) {
            sendPermissionEvent(true);
        } else {
            sendPermissionEvent(false);
        }
    }

    private void showRequestPermissionDialog() {
        if (hasPermission(getApplicationContext(), permission)) {
            sendPermissionEvent(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    private boolean permissionGranted(@NonNull int... grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    private void sendPermissionEvent(boolean granted) {
        Dependencies.INSTANCE.getEventBus().post(new PermissionEvent(this.requestCode, granted));
        finish();
    }

    public static boolean hasPermission(@NonNull Context context, @NonNull String permission) {
        return !isAtLeastMarshmallow() || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAtLeastMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
