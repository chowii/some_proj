package com.soho.sohoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.soho.sohoapp.preferences.Prefs;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 25/7/17.
 */

public class SohoApplication extends MultiDexApplication {

    private static SohoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        Constants.Companion.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gibson-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        DEPENDENCIES.init(this);
        Prefs prefs = DEPENDENCIES.getPreferences();
        if (!prefs.getHasInstalled()) {
            prefs.setHasInstalled(true);
            createShortCut();
        }
    }

    public static SohoApplication getInstance(){ return instance; }

    public static Context getContext(){ return instance.getApplicationContext(); }

    public void createShortCut() {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutIntent);
    }

    public static String getStringFromResource(@StringRes int stringRes) { return getContext().getString(stringRes); }
}

