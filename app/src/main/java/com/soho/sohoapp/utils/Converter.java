package com.soho.sohoapp.utils;

import android.location.Address;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyListing;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyVerification;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.feature.home.portfolio.data.PropertyFinance;
import com.soho.sohoapp.network.Keys;
import com.soho.sohoapp.network.results.PortfolioCategoryResult;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;
import com.soho.sohoapp.network.results.PropertyFinanceResult;
import com.soho.sohoapp.network.results.PropertyListingResult;
import com.soho.sohoapp.network.results.PropertyResult;
import com.soho.sohoapp.network.results.PropertyTypesResult;
import com.soho.sohoapp.network.results.PropertyUserRolesResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public final class Converter {
    private static final String IMAGE_TYPE_JPEG = "image/jpeg";

    private Converter() {
        //utility class
    }

    @NonNull
    public static Property toProperty(@NonNull PropertyResult result) {
        Property property = new Property();
        property.setId(result.id);
        property.setType(result.type);
        property.setBedrooms(result.bedrooms);
        property.setBathrooms(result.bathrooms);
        property.setCarspots(result.carspots);
        property.setAddress(toPropertyAddress(result));
        property.setPropertyListing(toPropertyListing(result.propertyListing));

        List<PropertyImage> propertyImageList = new ArrayList<>();
        for (PropertyResult.Photo photo : result.photos) {
            propertyImageList.add(toPropertyImage(property, photo));
        }
        property.setPropertyImageList(propertyImageList);

        List<PropertyVerification> propertyVerificationList = new ArrayList<>();
        for (PropertyResult.Verification verification : result.verifications) {
            propertyVerificationList.add(toPropertyVerification(verification));
        }
        property.setPropertyVerificationList(propertyVerificationList);

        property.setPropertyFinance(toPropertyFinance(result.propertyFinance));
        return property;
    }

    public static Observable<RequestBody> toImageRequestBody(@NonNull FileHelper fileHelper, @NonNull PropertyImage propertyImage) {
        return Observable.fromCallable(() -> {
            Uri uri;
            if (propertyImage.getFilePath() != null) {
                uri = Uri.fromFile(new File(propertyImage.getFilePath()));
            } else {
                uri = propertyImage.getUri();
            }
            MultipartBody.Builder builder = new MultipartBody.Builder();
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse(IMAGE_TYPE_JPEG), fileHelper.compressPhoto(uri));
            File file = new File(uri.getPath());
            builder.addFormDataPart(Keys.Property.IMAGE, file.getName(), imageRequestBody);
            return builder.build();
        });
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
    public static Map<String, Object> toMap(@NonNull PropertyListing propertyListing) {
        Map<String, Object> map = new HashMap<>();

        map.put(Keys.PropertyListing.ID, propertyListing.getId());
        map.put(Keys.PropertyListing.STATE, propertyListing.getState());

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
    public static PropertyListing toPropertyListing(@NonNull PropertyListingResult result) {
        PropertyListing propertyListing = new PropertyListing();
        propertyListing.setId(result.id);
        propertyListing.setState(result.state);
        return propertyListing;
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

        property.setPropertyFinance(toPropertyFinance(result.finance));
        return property;
    }

    @NonNull
    private static PropertyFinance toPropertyFinance(PropertyFinanceResult result) {
        PropertyFinance finance = new PropertyFinance();
        if (result != null) {
            finance.setId(result.id);
            finance.setPurchasePrice(result.purchasePrice);
            finance.setLoanAmount(result.loanAmount);
            finance.setEstimatedValue(result.estimatedValue);
            finance.setRented(result.isRented);
            finance.setActualRent(result.actualRent);
            finance.setEstimatedRent(result.estimatedRent);
            finance.setLeasedToDate(DateUtils.iso8601TimeToLong(result.leasedTo));
        }
        return finance;
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

    @NonNull
    private static PropertyVerification toPropertyVerification(PropertyResult.Verification verification) {
        PropertyVerification propertyVerification = new PropertyVerification();
        propertyVerification.setId(verification.id);
        propertyVerification.setType(verification.type);
        propertyVerification.setText(verification.text);
        propertyVerification.setState(verification.state);
        return propertyVerification;
    }

    @NonNull
    private static PropertyImage toPropertyImage(Property property, PropertyResult.Photo photo) {
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setImageUrl(photo.image.url);
        propertyImage.setHolder(PropertyType.getDefaultImage(property.getType()));
        return propertyImage;
    }

    @NonNull
    private static PropertyAddress toPropertyAddress(@NonNull PropertyResult result) {
        PropertyAddress address = new PropertyAddress();
        address.setAddressLine1(result.location.address_1);
        address.setAddressLine2(result.location.address_2);
        return address;
    }
}
