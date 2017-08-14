package com.soho.sohoapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class AndroidPreferences implements Preferences {
    private static final String SHARED_PREFS_NAME = "Soho-prefs";

    private final SharedPreferences preferences;

    public AndroidPreferences(@NonNull Context context) {
        preferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void putObject(String key, Object value) {
        String json = null;
        if (value != null) {
            Gson gson = new Gson();
            json = gson.toJson(value);
        }

        putString(key, json);
    }

    private <T> T getObject(String key, Class<T> clazz) {
        T object = null;
        String json = preferences.getString(key, null);
        if (json != null) {
            Gson gson = new Gson();
            object = gson.fromJson(json, clazz);
        }

        return object;
    }

    private void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

}
