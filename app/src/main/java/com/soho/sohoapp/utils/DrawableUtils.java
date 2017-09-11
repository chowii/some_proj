package com.soho.sohoapp.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public final class DrawableUtils {

    public DrawableUtils() {
        //utility class
    }

    public static void setBackgroundColor(final int color, final Drawable background) {
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background.mutate()).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background.mutate()).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background.mutate()).setColor(color);
        } else {
            DEPENDENCIES.getLogger().w("Not a valid background type");
        }
    }
}
