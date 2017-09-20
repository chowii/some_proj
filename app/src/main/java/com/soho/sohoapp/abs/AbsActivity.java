package com.soho.sohoapp.abs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.soho.sohoapp.feature.network.ThrowableHandler;
import com.soho.sohoapp.logger.Logger;
import com.soho.sohoapp.preferences.Prefs;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public abstract class AbsActivity extends AppCompatActivity {
    protected Logger logger;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = DEPENDENCIES.getLogger();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected boolean isUserSignedIn() {
        Prefs prefs = DEPENDENCIES.getPreferences();
        return !prefs.getAuthToken().isEmpty();
    }

    protected void handleError(Throwable throwable) {
        ThrowableHandler.showError(throwable, true, null
                , findViewById(android.R.id.content), this);
    }

}
