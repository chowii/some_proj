package com.soho.sohoapp.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.enums.VerificationStatus;
import com.soho.sohoapp.data.models.Verification;

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
    public static int getPublicPropertyStateColor(List<Verification> verifications) {
        int color = R.color.propertyStateVerified;

        if (hasState(verifications, VerificationStatus.NOT_VERIFIED)) {
            color = R.color.propertyStateNotVerified;
        } else if (hasState(verifications, VerificationStatus.NEW)
                || hasState(verifications, VerificationStatus.PENDING)) {
            color = R.color.propertyStateNotCompleted;
        }

        return color;
    }

    private static boolean hasState(@NonNull List<Verification> verifications, @NonNull String state) {
        for (Verification verification : verifications) {
            if (state.equals(verification.getState())) {
                return true;
            }
        }
        return false;
    }
}
