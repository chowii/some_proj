package com.soho.sohoapp.utils;

import android.location.Address;

final class AddressUtils {

    private AddressUtils() {
        //utility class
    }

    static String getAddress1(String fullText, Address address) {
        return fullText.replace(",", "")
                .replace(address.getLocality(), "")
                .replace(address.getCountryName(), "");
    }

    static String getAddress2(Address address) {
        return address.getAddressLine(1) == null ? "" : address.getAddressLine(1);
    }
}
