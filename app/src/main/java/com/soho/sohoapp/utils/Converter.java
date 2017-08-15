package com.soho.sohoapp.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.soho.sohoapp.data.PropertyAddress;

import java.util.ArrayList;
import java.util.List;

public final class Converter {

    private Converter() {
        //utility class
    }

    @NonNull
    public static List<PropertyAddress> toAddressList(@NonNull List<AutocompletePrediction> predictionList) {
        List<PropertyAddress> addressList = new ArrayList<>();
        PropertyAddress address;

        for (AutocompletePrediction prediction : predictionList) {
            address = new PropertyAddress();
            address.setFullAddress(prediction.getFullText(null).toString());
            addressList.add(address);
        }

        return addressList;
    }
}
