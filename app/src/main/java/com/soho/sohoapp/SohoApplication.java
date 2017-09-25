package com.soho.sohoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.soho.sohoapp.data.models.User;
import com.soho.sohoapp.preferences.Prefs;
import com.soho.sohoapp.utils.Converter;
import com.zendesk.sdk.network.impl.ZendeskConfig;

import org.jetbrains.annotations.NotNull;

import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static android.text.TextUtils.isEmpty;
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

        ZendeskConfig.INSTANCE.init(this
                , getString(R.string.key_zendesk_app_url)
                , getString(R.string.key_zendesk_application_id)
                , getString(R.string.key_zendesk_mobile_sdk_client));

        DEPENDENCIES.init(this);

        Prefs prefs = DEPENDENCIES.getPreferences();
        if (!prefs.getHasInstalled()) {
            prefs.setHasInstalled(true);
            createShortCut();
        }

        getUserProfile();
    }

    public static SohoApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public void createShortCut() {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutIntent);
    }

    public static String getStringFromResource(@StringRes int stringRes) {
        return getContext().getString(stringRes);
    }

    @NotNull
    public static void getUserProfile() {
        if (!isEmpty(DEPENDENCIES.getPreferences().getAuthToken())) {
            DEPENDENCIES.getSohoService().getProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userResult ->
                            {
                                User user = Converter.toUser(userResult);
                                DEPENDENCIES.getPreferences().setMUser(user);
                            }, throwable -> DEPENDENCIES.getLogger().e("Error when getting user profile", throwable)
                    );
        }
    }
}

