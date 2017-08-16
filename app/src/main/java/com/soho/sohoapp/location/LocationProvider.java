package com.soho.sohoapp.location;

import com.soho.sohoapp.data.PropertyAddress;

import rx.Observable;

public interface LocationProvider {
    Observable<PropertyAddress> getLocationAddress(String placeId, String fullAddress);
}
