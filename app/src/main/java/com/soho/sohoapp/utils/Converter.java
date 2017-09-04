package com.soho.sohoapp.utils;

import android.location.Address;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioFinance;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.network.Keys;
import com.soho.sohoapp.network.results.PortfolioCategoryResult;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;
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
    public static List<PortfolioCategory> toPortfolioCategoryList(@NonNull List<PortfolioCategoryResult> results) {
        List<PortfolioCategory> portfolioCategoryList = new ArrayList<>();
        for (PortfolioCategoryResult result : results) {
            portfolioCategoryList.add(toPortfolioCategory(result));
        }
        return portfolioCategoryList;
    }

    @NonNull
    public static List<PortfolioManagerCategory> toPortfolioManagerCategoryList(@NonNull List<PortfolioCategoryResult> results) {
        List<PortfolioManagerCategory> portfolioCategoryList = new ArrayList<>();
        for (PortfolioCategoryResult result : results) {
            portfolioCategoryList.add(toPortfolioManagerCategory(result));
        }
        return portfolioCategoryList;
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
    public static List<PropertyRole> toPropertyRoleList(@NonNull List<PropertyUserRolesResult> results) {
        List<PropertyRole> propertyRoleList = new ArrayList<>();
        for (PropertyUserRolesResult result : results) {
            propertyRoleList.add(toPropertyRole(result));
        }
        return propertyRoleList;
    }

    @NonNull
    public static List<PropertyType> toPropertyTypeList(@NonNull List<PropertyTypesResult> results) {
        List<PropertyType> propertyTypeList = new ArrayList<>();
        for (PropertyTypesResult result : results) {
            propertyTypeList.add(toPropertyType(result));
        }
        return propertyTypeList;
    }

    @NonNull
    public static List<PortfolioProperty> toPortfolioPropertyList(@NonNull List<PortfolioPropertyResult> results, boolean isManagerPortfolio) {
        List<PortfolioProperty> propertyList = new ArrayList<>();
        for (PortfolioPropertyResult result : results) {
            propertyList.add(toPortfolioProperty(result, isManagerPortfolio));
        }
        return propertyList;
    }

    @NonNull
    private static PortfolioProperty toPortfolioProperty(@NonNull PortfolioPropertyResult result, boolean isManagerPortfolio) {
        PortfolioProperty property = new PortfolioProperty();

        if (isManagerPortfolio) {
            property.setItemViewType(R.layout.item_manager_portfolio_details);
        } else {
            property.setItemViewType(R.layout.item_owner_portfolio_details);
        }

        property.setId(result.id);
        property.setState(result.state);

        PropertyAddress address = new PropertyAddress();
        if (result.location != null) {
            address.setAddressLine1(result.location.address1);
        }
        property.setPropertyAddress(address);

        PortfolioFinance finance = new PortfolioFinance();
        if (result.finance != null) {
            finance.setId(result.finance.id);
            finance.setPurchasePrice(result.finance.purchasePrice);
            finance.setLoanAmount(result.finance.loanAmount);
            finance.setEstimatedValue(result.finance.estimatedValue);
            finance.setRented(result.finance.isRented);
            finance.setActualRent(result.finance.actualRent);
            finance.setEstimatedRent(result.finance.estimatedRent);
        }
        property.setPortfolioFinance(finance);

        return property;
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

    @NonNull
    private static PortfolioCategory toPortfolioCategory(PortfolioCategoryResult result) {
        PortfolioCategory portfolioCategory = new PortfolioCategory();
        portfolioCategory.setName(result.name);
        portfolioCategory.setUserId(result.userId);
        portfolioCategory.setPropertyCount(result.propertyCount);
        portfolioCategory.setEstimatedValue(result.estimatedValue);
        portfolioCategory.setFilterForPortfolio(result.filterForPortfolio);
        return portfolioCategory;
    }

    @NonNull
    private static PortfolioManagerCategory toPortfolioManagerCategory(PortfolioCategoryResult result) {
        PortfolioManagerCategory portfolioCategory = new PortfolioManagerCategory();
        portfolioCategory.setName(result.name);
        portfolioCategory.setUserId(result.userId);
        portfolioCategory.setPropertyCount(result.propertyCount);
        portfolioCategory.setEstimatedValue(result.estimatedValue);
        portfolioCategory.setPublicPropertiesCount(result.publicPropertiesCount);
        portfolioCategory.setFilterForPortfolio(result.filterForPortfolio);
        return portfolioCategory;
    }

}
