package com.soho.sohoapp;

import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;

import com.crashlytics.android.Crashlytics;
import com.soho.sohoapp.helper.SharedPrefsHelper;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by chowii on 25/7/17.
 */

public class SohoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Constants.init(this);
        SharedPrefsHelper.Companion.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gibson-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Dependencies.INSTANCE.init(this);
    }

    public void createShortCut() {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutintent);
    }
}

