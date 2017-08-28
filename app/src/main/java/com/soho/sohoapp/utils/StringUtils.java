package com.soho.sohoapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;

public final class StringUtils {

    private StringUtils() {
        //utility class
    }

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

    public static String formatYield(@NonNull Context context, double yield) {
        String result;
        if (yield == 0) {
            result = context.getString(R.string.portfolio_yield_null);
        } else {
            result = context.getString(R.string.portfolio_yield, yield);
        }
        return result;
    }

    public static String formatLvr(@NonNull Context context, double lvr) {
        String result;
        if (lvr == 0) {
            result = context.getString(R.string.portfolio_lvr_null);
        } else {
            result = context.getString(R.string.portfolio_lvr, lvr);
        }
        return result;
    }

    public static String formatChangedValue(@NonNull Context context, double changedValue) {
        String formattedChangedValue = formatPrice(context, Math.abs(changedValue));
        String result;
        if (changedValue == 0) {
            result = context.getString(R.string.portfolio_changed_value_null);
        } else if (changedValue < 0) {
            result = context.getString(R.string.portfolio_changed_value_minus, formattedChangedValue);
        } else {
            result = context.getString(R.string.portfolio_changed_value_plus, formattedChangedValue);
        }
        return result;
    }
}