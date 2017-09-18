package com.soho.sohoapp.location;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.addproperty.data.LatLng;

import rx.Observable;

public interface LocationProvider {

    Observable<LatLng> getLatLng(String placeId);

    Observable<Location> getAddress(LatLng latLng, String fullAddress);
}
