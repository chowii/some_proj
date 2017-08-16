package com.soho.sohoapp.navigator;

import android.app.Activity;
import android.support.annotation.NonNull;

public class AndroidNavigator implements Navigator {
    private Activity activity;

    private AndroidNavigator(@NonNull Activity activity) {
        this.activity = activity;
    }

    public static AndroidNavigator newInstance(@NonNull Activity activity) {
        return new AndroidNavigator(activity);
    }

    @Override
    public void exitCurrentScreen() {
        activity.finish();
    }
}
