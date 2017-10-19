package com.soho.sohoapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.extensions.IntExtKt;

public final class StringUtils {

    private StringUtils() {
        //utility class
    }

    public static String capitalize(String string) {
        String capitalizedString = string;
        if (capitalizedString.length() > 0) {
            capitalizedString = capitalizedString.substring(0, 1).toUpperCase() + capitalizedString.substring(1).toLowerCase();
        }
        return capitalizedString;
    }

    //todo this will be used only when adding expenses
    public static String formatPrice(@NonNull Context context, double price) {
        String result;
        if (price >= 1000000) {
            result = context.getString(R.string.portfolio_estimated_value_m, price / 1000000);
        } else if (price >= 1000) {
            result = context.getString(R.string.portfolio_estimated_value_k, price / 1000);
        } else {
            result = context.getString(R.string.portfolio_estimated_value, price);
        }
        return result;
    }

    public static String shortFormatYield(@NonNull Context context, double yield) {
        String result;
        if (yield == 0) {
            result = context.getString(R.string.edit_property_no_value);
        } else {
            result = context.getString(R.string.edit_property_yield_value, yield);
        }
        return result;
    }

    public static String longFormatYield(@NonNull Context context, double yield) {
        String result;
        if (yield == 0) {
            result = context.getString(R.string.portfolio_yield_null);
        } else {
            result = context.getString(R.string.portfolio_yield, yield);
        }
        return result;
    }

    public static String shortFormatLvr(@NonNull Context context, double lvr) {
        String result;
        if (lvr == 0) {
            result = context.getString(R.string.edit_property_no_value);
        } else {
            result = context.getString(R.string.edit_property_lvr_value, lvr);
        }
        return result;
    }

    public static String longFormatLvr(@NonNull Context context, double lvr) {
        String result;
        if (lvr == 0) {
            result = context.getString(R.string.portfolio_lvr_null);
        } else {
            result = context.getString(R.string.portfolio_lvr, lvr);
        }
        return result;
    }

    public static String formatChangedValue(@NonNull Context context, double changedValue) {
        boolean isNegative = changedValue < 0;
        if (isNegative) {
            changedValue = changedValue * -1;
        }
        String formattedChangedValue = IntExtKt.toShortHand((int) changedValue);
        String result;
        if (changedValue == 0) {
            result = context.getString(R.string.portfolio_changed_value_null);
        } else if (isNegative) {
            result = context.getString(R.string.portfolio_changed_value_minus, formattedChangedValue);
        } else {
            result = context.getString(R.string.portfolio_changed_value_plus, formattedChangedValue);
        }
        return result;
    }
}
