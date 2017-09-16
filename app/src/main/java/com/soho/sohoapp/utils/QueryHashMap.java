package com.soho.sohoapp.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

public class QueryHashMap extends HashMap<String, Object> {

    @Override
    public QueryHashMap put(@NonNull String key, @Nullable Object value) {
        if (value != null) {
            super.put(key, value);
        }
        return this;
    }
}
