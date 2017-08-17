package com.soho.sohoapp.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.data.PropertyType;
import com.soho.sohoapp.network.results.PropertyTypesResult;
import com.soho.sohoapp.network.results.PropertyUserRolesResult;

import java.util.ArrayList;
import java.util.List;

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

    @NonNull
    public static List<PropertyRole> toPropertyRoleList(List<PropertyUserRolesResult> results) {
        List<PropertyRole> propertyRoleList = new ArrayList<>();
        for (PropertyUserRolesResult result : results) {
            propertyRoleList.add(toPropertyRole(result));
        }
        return propertyRoleList;
    }

    @NonNull
    private static PropertyRole toPropertyRole(PropertyUserRolesResult result) {
        PropertyRole propertyRole = new PropertyRole();
        propertyRole.setKey(result.key);
        propertyRole.setLabel(result.label);
        return propertyRole;
    }

    @NonNull
    public static List<PropertyType> toPropertyTypeList(List<PropertyTypesResult> results) {
        List<PropertyType> propertyTypeList = new ArrayList<>();
        for (PropertyTypesResult result : results) {
            propertyTypeList.add(toPropertyType(result));
        }
        return propertyTypeList;
    }

    @NonNull
    private static PropertyType toPropertyType(PropertyTypesResult result) {
        PropertyType propertyType = new PropertyType();
        propertyType.setKey(result.key);
        propertyType.setLabel(result.label);
        return propertyType;
    }
}
