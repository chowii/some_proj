package com.soho.sohoapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.soho.sohoapp.imageloader.ImageLoader;
import com.soho.sohoapp.logger.AndroidLogger;
import com.soho.sohoapp.logger.Logger;
import com.soho.sohoapp.preferences.AndroidPreferences;
import com.soho.sohoapp.preferences.Preferences;

public enum Dependencies {
    INSTANCE;

    private Preferences preferences;
    private ImageLoader imageLoader;
    private Logger logger;

    void init(Context context) {
        preferences = new AndroidPreferences(context);
        imageLoader = new ImageLoader(context);
        logger = new AndroidLogger();
    }

    @NonNull
    public Preferences getPreferences() {
        return preferences;
    }

    @NonNull
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @NonNull
    public Logger getLogger() {
        return logger;
    }
}
