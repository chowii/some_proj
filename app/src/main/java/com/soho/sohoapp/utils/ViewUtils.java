package com.soho.sohoapp.utils;

import android.content.Context;
import android.view.View;

import com.soho.sohoapp.R;

public final class ViewUtils {

    private ViewUtils() {
        //utility class
    }

    public static void disableViews(View... views) {
        for (View view : views) {
            view.setEnabled(false);
        }
    }

    public static void setDisabledBackground(Context context, View... views) {
        for (View view : views) {
            view.setBackgroundColor(context.getResources().getColor(R.color.disabledBackground));
        }
    }
}
