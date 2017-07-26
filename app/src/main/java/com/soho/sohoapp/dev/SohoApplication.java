package com.soho.sohoapp.dev;
import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;

import com.soho.sohoapp.dev.Constants;
import com.soho.sohoapp.dev.R;
import com.soho.sohoapp.dev.SplashActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by chowii on 25/7/17.
 */

public class SohoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Constants.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gibson-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public void createShortCut(){
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutintent);
    }
}

