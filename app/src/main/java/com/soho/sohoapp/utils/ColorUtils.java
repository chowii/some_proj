package com.soho.sohoapp.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyVerification;
import com.soho.sohoapp.feature.home.editproperty.data.VerificationType;

import java.util.List;

public final class ColorUtils {

    public ColorUtils() {
        //utility class
    }

    @ColorRes
    public static int getPrivatePropertyStateColor() {
        return R.color.propertyStateVerified;
    }

    @ColorRes
    public static int getPublicPropertyStateColor(List<PropertyVerification> propertyVerifications) {
        int color = R.color.propertyStateVerified;

        if (hasState(propertyVerifications, VerificationType.NOT_VERIFIED)) {
            color = R.color.propertyStateNotVerified;
        } else if (hasState(propertyVerifications, VerificationType.NEW)
                || hasState(propertyVerifications, VerificationType.PENDING)) {
            color = R.color.propertyStateNotCompleted;
        }

        return color;
    }

    private static boolean hasState(@NonNull List<PropertyVerification> verifications, @NonNull String state) {
        for (PropertyVerification verification : verifications) {
            if (state.equals(verification.getState())) {
                return true;
            }
        }
        return false;
    }
}
