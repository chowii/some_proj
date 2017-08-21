package com.soho.sohoapp.utils;

import android.location.Address;
import android.support.annotation.NonNull;

import com.soho.sohoapp.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.home.addproperty.data.PropertyType;
import com.soho.sohoapp.network.Keys;
import com.soho.sohoapp.network.results.PropertyTypesResult;
import com.soho.sohoapp.network.results.PropertyUserRolesResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Converter {

    private Converter() {
        //utility class
    }

    @NonNull
    public static Map<String, Object> toMap(@NonNull PropertyAddress propertyAddress,
                                            @NonNull PropertyRole role,
                                            @NonNull PropertyType propertyType,
                                            boolean isInvestment, int bedrooms, int bathrooms, int carspots) {
        Map<String, Object> map = new HashMap<>();

        map.put(Keys.Property.RELATION, role.getKey());
        map.put(Keys.Property.BEDROOMS, bedrooms);
        map.put(Keys.Property.BATHROOMS, bathrooms);
        map.put(Keys.Property.CARSPOTS, carspots);
        map.put(Keys.Property.IS_INVESTMENT, isInvestment);
        map.put(Keys.Property.TYPE_OF_PROPERTY, propertyType.getKey());

        map.put(Keys.Property.SUBURB, propertyAddress.getSuburb());
        map.put(Keys.Property.STATE, propertyAddress.getState());
        map.put(Keys.Property.POSTCODE, propertyAddress.getPostcode());
        map.put(Keys.Property.COUNTRY, propertyAddress.getCountry());
        map.put(Keys.Property.LATITUDE, propertyAddress.getLat());
        map.put(Keys.Property.LONGITUDE, propertyAddress.getLng());
        map.put(Keys.Property.ADDRESS1, propertyAddress.getAddressLine1());
        map.put(Keys.Property.ADDRESS2, propertyAddress.getAddressLine2());

        return map;
    }

    @NonNull
    public static PropertyAddress toPropertyAddress(Address address, String fullAddress) {
        PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setFullAddress(fullAddress);
        if (address != null) {
            propertyAddress.setLat(address.getLatitude());
            propertyAddress.setLng(address.getLongitude());
            propertyAddress.setCountry(address.getCountryName());
            propertyAddress.setPostcode(address.getPostalCode());
            propertyAddress.setState(address.getAdminArea());
            propertyAddress.setSuburb(address.getLocality());

            propertyAddress.setAddressLine1(AddressUtils.getAddress1(fullAddress, address));
            propertyAddress.setAddressLine2(AddressUtils.getAddress2(address));
        }
        return propertyAddress;
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

    @NonNull
    private static PropertyRole toPropertyRole(PropertyUserRolesResult result) {
        PropertyRole propertyRole = new PropertyRole();
        propertyRole.setKey(result.key);
        propertyRole.setLabel(result.label);
        return propertyRole;
    }
}
