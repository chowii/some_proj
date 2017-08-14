package com.soho.sohoapp.utils;

import android.support.annotation.Nullable;

public final class Preconditions {
    private Preconditions() {
        //utility class
    }

    public static void checkIfNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkIfNull(@Nullable Object object) {
        checkIfNull(object, "Object must not be null.");

    }
}
