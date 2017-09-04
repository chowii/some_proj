package com.soho.sohoapp.location;

import com.soho.sohoapp.feature.home.addproperty.data.LatLng;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;

import rx.Observable;

public interface LocationProvider {

    Observable<LatLng> getLatLng(String placeId);

    Observable<PropertyAddress> getAddress(LatLng latLng, String fullAddress);
}
