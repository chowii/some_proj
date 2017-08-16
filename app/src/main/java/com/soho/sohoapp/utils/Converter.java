package com.soho.sohoapp.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.data.PropertyAddress;

public final class Converter {

    private Converter() {
        //utility class
    }

    @NonNull
    public static PropertyAddress toPropertyAddress(LatLng latLng, String fullAddress) {
        PropertyAddress address = new PropertyAddress();
        address.setFullAddress(fullAddress);
        if (latLng != null) {
            address.setLat(latLng.latitude);
            address.setLng(latLng.longitude);
        }
        return address;
    }
}
