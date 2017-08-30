package com.soho.sohoapp.location;

import com.soho.sohoapp.home.addproperty.data.LatLng;
import com.soho.sohoapp.home.addproperty.data.PropertyAddress;

import rx.Observable;

public interface LocationProvider {

    Observable<LatLng> getLatLng(String placeId);

    Observable<PropertyAddress> getAddress(LatLng latLng, String fullAddress);
}
