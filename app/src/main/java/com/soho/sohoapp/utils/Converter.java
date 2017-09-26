package com.soho.sohoapp.utils;

import android.location.Address;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.dtos.BasicPropertyResult;
import com.soho.sohoapp.data.dtos.BasicUserResult;
import com.soho.sohoapp.data.dtos.ImageResult;
import com.soho.sohoapp.data.dtos.InspectionTimeResult;
import com.soho.sohoapp.data.dtos.LocationResult;
import com.soho.sohoapp.data.dtos.PhotoResult;
import com.soho.sohoapp.data.dtos.PropertyFinanceResult;
import com.soho.sohoapp.data.dtos.PropertyListingResult;
import com.soho.sohoapp.data.dtos.PropertyResult;
import com.soho.sohoapp.data.dtos.PropertyUserResult;
import com.soho.sohoapp.data.dtos.UserResult;
import com.soho.sohoapp.data.dtos.VerificationResult;
import com.soho.sohoapp.data.models.BasicProperty;
import com.soho.sohoapp.data.models.BasicUser;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.InspectionTime;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Photo;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.data.models.PropertyUser;
import com.soho.sohoapp.data.models.User;
import com.soho.sohoapp.data.models.Verification;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.extensions.StringExtKt;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.network.Keys;
import com.soho.sohoapp.network.results.PortfolioCategoryResult;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;
import com.soho.sohoapp.network.results.PropertyTypesResult;
import com.soho.sohoapp.network.results.PropertyUserRolesResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.text.TextUtils.isEmpty;

public final class Converter {

    private static final String IMAGE_TYPE_JPEG = "image/jpeg";

    private Converter() {
        //utility class
    }

    // MARK: - ================== Property ==================

    @NonNull
    public static BasicProperty toBasicProperty(@NonNull BasicPropertyResult result) {
        BasicProperty basicProperty = new BasicProperty();
        basicProperty.setId(result.getId());
        basicProperty.setState(result.getState());
        basicProperty.setTitle(result.getTitle());
        basicProperty.setDescription(result.getDescription());
        basicProperty.setType(result.getType());
        basicProperty.setInvestment(result.isInvestment());
        basicProperty.setFavourite(result.isFavourite());
        basicProperty.setRentPrice(result.getRentPrice());
        basicProperty.setSalePrice(result.getSalePrice());
        basicProperty.setBedrooms(result.getBedrooms());
        basicProperty.setCarspots(result.getCarspots());
        basicProperty.setBathrooms(result.getBathrooms());
        basicProperty.setLocation(toLocation(result.getLocation()));
        basicProperty.setPhotos(toPhotos(result.getPhotos(), result));
        basicProperty.setAgentLogo(toImage(result.getAgentLogo(), null));
        basicProperty.setPropertyUsers(toPropertyUsers(result.getPropertyUsers()));
        if (!isEmpty(result.getUpdatedAt())) {
            basicProperty.setUpdatedAt(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getUpdatedAt()));
        }
        return basicProperty;
    }

    @NonNull
    public static List<BasicProperty> toBasicProperties(@Nullable List<BasicPropertyResult> results) {
        List<BasicProperty> basicProperties = new ArrayList<>();
        if (results != null) {
            for (BasicPropertyResult result : results) {
                basicProperties.add(toBasicProperty(result));
            }
        }
        return basicProperties;
    }

    @Nullable
    public static Property toProperty(@Nullable PropertyResult result) {
        if (result == null) {
            return null;
        }
        Property property = new Property();
        //Basic Details
        property.setId(result.getId());
        property.setState(result.getState());
        property.setTitle(result.getTitle());
        property.setDescription(result.getDescription());
        property.setType(result.getType());
        property.setInvestment(result.isInvestment());
        property.setFavourite(result.isFavourite());
        property.setRentPrice(result.getRentPrice());
        property.setSalePrice(result.getSalePrice());
        property.setBedrooms(result.getBedrooms());
        property.setCarspots(result.getCarspots());
        property.setBathrooms(result.getBathrooms());
        property.setLocation(toLocation(result.getLocation()));
        property.setPhotos(toPhotos(result.getPhotos(), result));
        property.setAgentLogo(toImage(result.getAgentLogo(), null));
        property.setPropertyUsers(toPropertyUsers(result.getPropertyUsers()));
        if (!isEmpty(result.getUpdatedAt())) {
            property.setUpdatedAt(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getUpdatedAt()));
        }
        //Property Details
        property.setLandSize(result.getLandSize());
        property.setLandSizeMeasurement(result.getLandSizeMeasurement());
        property.setRennovationDetails(result.getRennovationDetails());
        property.setAgentLicenseNumber(result.getAgentLicenseNumber());
        property.setAgentMobileNumber(result.getAgentMobileNumber());
        property.setPropertyListing(toPropertyListing(result.getPropertyListing()));
        property.setPropertyFinance(toPropertyFinance(result.getPropertyFinance()));
        property.setVerifications(toVerifications(result.getVerifications()));
        if (!isEmpty(result.getAuctionDate())) {
            property.setAuctionDate(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getAuctionDate()));
        }
        return property;
    }

    @Nullable
    public static PropertyListing toPropertyListing(@Nullable PropertyListingResult result) {
        if (result == null) {
            return null;
        }
        PropertyListing propertyListing = new PropertyListing();
        propertyListing.setId(result.getId());
        propertyListing.setState(result.getState());
        propertyListing.setCanReceiveRentOffers(result.getCanReceiveRentOffers());
        propertyListing.setCanReceiveSalesOffers(result.getCanReceiveSalesOffers());
        propertyListing.setSaleTitle(result.getSaleTitle());
        propertyListing.setRentTitle(result.getRentTitle());
        propertyListing.setAuctionTitle(result.getAuctionTitle());
        propertyListing.setDiscoverableTitle(result.getDiscoverableTitle());
        propertyListing.setOnSiteAuction(result.isOnSiteAuction());
        propertyListing.setRentPaymentFrequency(result.getRentPaymentFrequency());
        propertyListing.setAppointmentOnly(result.isAppointmentOnly());
        propertyListing.setOffSiteLocation(toLocation(result.getOffSiteLocation()));
        propertyListing.setInspectionTimes(toInspectionTimes(result.getInspectionTimes()));
        if (!isEmpty(result.getAvailableFrom())) {
            propertyListing.setAvailableFrom(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getAvailableFrom()));
        }
        if (!isEmpty(result.getAuctionTime())) {
            propertyListing.setAuctionTime(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getAuctionTime()));
        }
        return propertyListing;
    }

    @Nullable
    private static PropertyFinance toPropertyFinance(@Nullable PropertyFinanceResult result) {
        if (result == null) {
            return null;
        }
        PropertyFinance finance = new PropertyFinance();
        finance.setId(result.getId());
        finance.setPurchasePrice(result.getPurchasePrice());
        finance.setLoanAmount(result.getLoanAmount());
        finance.setEstimatedValue(result.getEstimatedValue());
        finance.setRented(result.isRented());
        finance.setActualRent(result.getActualRent());
        finance.setEstimatedRent(result.getEstimatedRent());
        if (!isEmpty(result.getLeasedTo())) {
            finance.setLeasedToDate(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getLeasedTo()));
        }
        return finance;
    }

    @Nullable
    private static Verification toVerification(@Nullable VerificationResult result) {
        if (result == null) {
            return null;
        }
        Verification verification = new Verification();
        verification.setId(result.getId());
        verification.setType(result.getType());
        verification.setText(result.getText());
        verification.setState(result.getState());
        return verification;
    }

    @NonNull
    public static List<Verification> toVerifications(@Nullable List<VerificationResult> results) {
        List<Verification> verifications = new ArrayList<>();
        if (results != null) {
            for (VerificationResult result : results) {
                verifications.add(toVerification(result));
            }
        }
        return verifications;
    }

    // MARK: - ================== Inspection Times ==================
    @Nullable
    public static InspectionTime toInspectionTime(@Nullable InspectionTimeResult result) {
        if (result == null) {
            return null;
        }
        InspectionTime inspectionTime = new InspectionTime();
        inspectionTime.setId(result.getId());
        if (!isEmpty(result.getStartTime())) {
            inspectionTime.setStartTime(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getStartTime()));
        }
        if (!isEmpty(result.getEndTime())) {
            inspectionTime.setEndTime(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getEndTime()));
        }
        return inspectionTime;
    }

    @NonNull
    public static List<InspectionTime> toInspectionTimes(@Nullable List<InspectionTimeResult> results) {
        List<InspectionTime> inspectionTimes = new ArrayList<>();
        if (results != null) {
            for (InspectionTimeResult result : results) {
                inspectionTimes.add(toInspectionTime(result));
            }
        }
        return inspectionTimes;
    }

    // MARK: - ================== Users ==================

    @Nullable
    public static PropertyUser toPropertyUser(@Nullable PropertyUserResult result) {
        if (result == null) {
            return null;
        }
        PropertyUser propertyUser = new PropertyUser();
        propertyUser.setId(result.getId());
        propertyUser.setRole(result.getRole());
        propertyUser.setLastMessage(result.getLastMessage());
        propertyUser.setUserDetails(toBasicUser(result.getUserDetails()));
        if (!isEmpty(result.getLastMessageAt())) {
            propertyUser.setLastMessageAt(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getLastMessageAt()));
        }
        return propertyUser;
    }

    @NonNull
    public static List<PropertyUser> toPropertyUsers(@Nullable List<PropertyUserResult> results) {
        List<PropertyUser> propertyUsers = new ArrayList<>();
        if (results != null) {
            for (PropertyUserResult result : results) {
                propertyUsers.add(toPropertyUser(result));
            }
        }
        return propertyUsers;
    }

    @Nullable
    public static BasicUser toBasicUser(@Nullable BasicUserResult result) {
        if (result == null) {
            return null;
        }
        BasicUser basicUser = new BasicUser();
        basicUser.setFirstName(result.getFirstName());
        basicUser.setLastName(result.getLastName());
        basicUser.setEmail(result.getEmail());
        basicUser.setAvatar(toImage(result.getAvatar(), null));
        if (!isEmpty(result.getDateOfBirth())) {
            basicUser.setDateOfBirth(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getDateOfBirth()));
        }
        return basicUser;
    }

    @Nullable
    public static User toUser(@Nullable UserResult result) {
        if (result == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(result.getFirstName());
        user.setLastName(result.getLastName());
        user.setEmail(result.getEmail());
        user.setAvatar(toImage(result.getAvatar(), null));
        user.setAuthenticationToken(result.getAuthenticationToken());
        user.setCountry(result.getCountry());
        user.setVerifications(toVerifications(result.getVerifications()));
        if (!isEmpty(result.getDateOfBirth())) {
            user.setDateOfBirth(StringExtKt.toDateLongWithIso8601DateTimeFormat(result.getDateOfBirth()));
        }
        return user;
    }

    // MARK: - ================== Photo ==================

    @Nullable
    private static Photo toPhoto(@NonNull PhotoResult photoResult, @Nullable BasicPropertyResult basicPropertyResult) {
        if (photoResult == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setImage(toImage(photoResult.getImage(), basicPropertyResult));
        return photo;
    }

    @Nullable
    private static Image toImage(@Nullable ImageResult imageResult, @Nullable BasicPropertyResult basicPropertyResult) {
        if (imageResult == null) {
            return null;
        }
        Image image = new Image();
        image.setImageUrl(imageResult.getUrl());
        if (basicPropertyResult != null) {
            image.setHolder(PropertyType.getDefaultImage(basicPropertyResult.getType()));
        }
        return image;
    }

    @NonNull
    public static List<Photo> toPhotos(@Nullable List<PhotoResult> photoResults, @Nullable BasicPropertyResult basicPropertyResult) {
        List<Photo> photos = new ArrayList<>();
        if (photoResults != null) {
            for (PhotoResult photoResult : photoResults) {
                photos.add(toPhoto(photoResult, basicPropertyResult));
            }
        }
        return photos;
    }

    // MARK: - ================== Location ==================

    @Nullable
    private static Location toLocation(@Nullable LocationResult result) {
        if (result == null) {
            return null;
        }
        Location location = new Location();
        location.setSuburb(result.getSuburb());
        location.setState(result.getState());
        location.setPostcode(result.getPostcode());
        location.setCountry(result.getCountry());
        location.setLatitude(result.getLatitude());
        location.setLongitude(result.getLongitude());
        location.setFullAddress(result.getFullAddress());
        location.setAddressLine1(result.getAddress_1());
        location.setAddressLine2(result.getAddress_2());
        location.setMaskAddress(result.getMaskAddress());
        return location;
    }

    // MARK: - ================== Params/Body related ==================

    public static Observable<RequestBody> toImageRequestBody(@NonNull FileHelper fileHelper, @NonNull Image image) {
        return Observable.fromCallable(() -> {
            Uri uri;
            if (image.getFilePath() != null) {
                uri = Uri.fromFile(new File(image.getFilePath()));
            } else {
                uri = image.getUri();
            }
            MultipartBody.Builder builder = new MultipartBody.Builder();
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse(IMAGE_TYPE_JPEG), fileHelper.compressPhoto(uri));
            File file = new File(uri.getPath());
            builder.addFormDataPart(Keys.Property.IMAGE, file.getName(), imageRequestBody);
            return builder.build();
        });
    }

    @NonNull
    public static QueryHashMap toMap(@Nullable Location location,
                                     @NonNull PropertyRole role,
                                     @NonNull PropertyType propertyType,
                                     boolean isInvestment, int bedrooms, int bathrooms, int carspots) {
        QueryHashMap map = new QueryHashMap()
                .put(Keys.Property.RELATION, role.getKey())
                .put(Keys.Property.BEDROOMS, bedrooms)
                .put(Keys.Property.BATHROOMS, bathrooms)
                .put(Keys.Property.CARSPOTS, carspots)
                .put(Keys.Property.IS_INVESTMENT, isInvestment)
                .put(Keys.Property.TYPE_OF_PROPERTY, propertyType.getKey());
        putLocation(map, location);
        return map;
    }

    @NonNull
    public static QueryHashMap toMap(@NonNull PropertyListing propertyListing) {
        QueryHashMap map = new QueryHashMap()
                .put(Keys.PropertyListing.ID, propertyListing.getId())
                .put(Keys.PropertyListing.STATE, propertyListing.getState())
                .put(Keys.PropertyListing.SALE_OFFERS, propertyListing.getCanReceiveSalesOffers())
                .put(Keys.PropertyListing.RENT_OFFERS, propertyListing.getCanReceiveRentOffers())
                .put(Keys.PropertyListing.SALE_TITLE, propertyListing.getSaleTitle())
                .put(Keys.PropertyListing.RENT_TITLE, propertyListing.getRentTitle())
                .put(Keys.PropertyListing.AUCTION_TITLE, propertyListing.getAuctionTitle())
                .put(Keys.PropertyListing.DISCOVERABLE_TITLE, propertyListing.getDiscoverableTitle())
                .put(Keys.PropertyListing.ON_SITE_AUCTION, propertyListing.isOnSiteAuction())
                .put(Keys.PropertyListing.APPOINTMENT_ONLY, propertyListing.isAppointmentOnly())
                .put(Keys.PropertyListing.RENT_PAYMENT_FREQUENCY, propertyListing.getRentPaymentFrequency())
                .put(Keys.PropertyListing.AUCTION_DATE, LongExtKt.toDateLongWithIso8601DateTimeFormat(propertyListing.getAuctionTime()))
                .put(Keys.PropertyListing.AUCTION_TIME, LongExtKt.toDateLongWithIso8601DateTimeFormat(propertyListing.getAuctionTime()))
                .put(Keys.PropertyListing.AVAILABLE_FROM, LongExtKt.toDateLongWithIso8601DateTimeFormat(propertyListing.getAvailableFrom()));
        putLocation(map, propertyListing.getOffSiteLocation());
        return map;
    }

    @NonNull
    public static QueryHashMap toMap(@NonNull Property property) {
        QueryHashMap map = new QueryHashMap();
        map.put(Keys.Property.DESCRIPTION, property.getDescription());
        putPropertyFinance(map, property.getPropertyFinance());
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
    public static Location toPropertyAddress(Address address, String fullAddress) {
        Location location = new Location();
        location.setFullAddress(fullAddress);
        if (address != null) {
            location.setLatitude(address.getLatitude());
            location.setLongitude(address.getLongitude());
            location.setCountry(address.getCountryName());
            location.setPostcode(address.getPostalCode());
            location.setState(address.getAdminArea());
            location.setSuburb(address.getLocality());
            location.setAddressLine1(AddressUtils.getAddress1(fullAddress, address));
            location.setAddressLine2(AddressUtils.getAddress2(address));
        }
        return location;
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
        property.setLocation(toLocation(result.location));
        property.setPropertyFinance(toPropertyFinance(result.finance));
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

    @NonNull
    private static void putLocation(@NonNull QueryHashMap map, @Nullable Location location) {
        if (location != null) {
            map.put(Keys.Location.SUBURB, location.getSuburb())
                    .put(Keys.Location.STATE, location.getState())
                    .put(Keys.Location.POSTCODE, location.getPostcode())
                    .put(Keys.Location.COUNTRY, location.getCountry())
                    .put(Keys.Location.LATITUDE, location.getLatitude())
                    .put(Keys.Location.LONGITUDE, location.getLongitude())
                    .put(Keys.Location.FULL_ADDRESS, location.getFullAddress())
                    .put(Keys.Location.ADDRESS1, location.getAddressLine1())
                    .put(Keys.Location.ADDRESS2, location.getAddressLine2());
        }
    }

    @NonNull
    private static void putPropertyFinance(@NonNull QueryHashMap map, @Nullable PropertyFinance finance) {
        if (finance != null) {
            map.put(Keys.PropertyFinance.PURCHASE_PRICE, finance.getPurchasePrice())
                    .put(Keys.PropertyFinance.LOAN_AMOUNT, finance.getLoanAmount())
                    .put(Keys.PropertyFinance.ESTIMATED_VALUE, finance.getEstimatedValue())
                    .put(Keys.PropertyFinance.IS_RANTED, finance.isRented())
                    .put(Keys.PropertyFinance.ESTIMATED_RENT, finance.getEstimatedRent())
                    .put(Keys.PropertyFinance.ACTUAL_RENT, finance.getActualRent())
                    .put(Keys.PropertyFinance.LEASED_TO, finance.getLeasedToDate());
        }
    }
}
