package com.soho.sohoapp.feature.home.editproperty.overview;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.PickerItem;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.data.models.Verification;
import com.soho.sohoapp.extensions.ListExtKt;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.soho.sohoapp.navigator.RequestCode.PROPERTY_VERIFICATIONS_REQUEST_CODE;

public class EditOverviewPresenter implements AbsPresenter, EditOverviewContract.ViewPresentable {
    private final EditOverviewContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public EditOverviewPresenter(EditOverviewContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getProperty();
        initPropertyListing(property.getPropertyListing());
        view.setPropertyFinance(property.getPropertyFinance());
        view.showPropertyAddress(property.getLocationSafe().getAddressLine1());
        view.showMaskAddress(property.getLocationSafe().getMaskAddress());
        initPropertyType();
        view.showRoomsNumber(property.getBedrooms(), property.getBathrooms(), property.getCarspots());
        view.showRenovation(property.getRenovationDetails());
        showPortfolioType();
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onMarketplaceStateClicked() {
        navigator.openPropertyStatusScreen(property, RequestCode.EDIT_PROPERTY_STATUS_UPDATE);
    }

    @Override
    public void onVerificationClicked() {
        navigator.openVerificationScreen(property, PROPERTY_VERIFICATIONS_REQUEST_CODE);
    }

    @Override
    public void onPropertyStatusUpdated(Property property, boolean verificationCompleted) {
        this.property = property;
        initPropertyListing(property.getPropertyListing());
        view.setPropertyFinance(property.getPropertyFinance());
        view.showMaskAddress(property.getLocationSafe().getMaskAddress());
        if (!verificationCompleted) {
            navigator.openVerificationScreen(property, PROPERTY_VERIFICATIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onPropertyVerificationsUpdated(List<Verification> verifications) {
        property.setVerifications(verifications);
        initPropertyListing(property.getPropertyListing());
    }

    @Override
    public void onAddressClicked() {
        navigator.openAutocompleteAddressScreen(RequestCode.EDIT_PROPERTY_ADDRESS, true);
    }

    @Override
    public void onPropertyAddressChanged(Location location) {
        property.setLocation(location);
        view.showPropertyAddress(location.getAddressLine1());
        view.notifyActivityAboutAddressChanges(location);
    }

    @Override
    public void onMaskAddressChanged(boolean isChecked) {
        Location location = property.getLocationSafe();
        location.setMaskAddress(isChecked);
        view.notifyActivityAboutAddressChanges(location);
    }

    @Override
    public void onRoomsNumberChanged(double bedrooms, double bathrooms, double carspots) {
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setCarspots(carspots);
        view.notifyActivityAboutRoomsChanges(bedrooms, bathrooms, carspots);
    }

    @Override
    public void onPropertyTypeChanged(PickerItem pickerItem) {
        property.setType(pickerItem.getId());
        view.notifyActivityAboutPropertyTypeChanged(pickerItem.getId());
    }

    @Override
    public void onRenovationClicked() {
        navigator.openPropertyDescriptionScreen(property.getRenovationDetails(), true, RequestCode.EDIT_PROPERTY_RENOVATION);
    }

    @Override
    public void onRenovationChanged(String renovation) {
        view.showRenovation(renovation);
        property.setRenovationDetails(renovation);
        view.notifyActivityAboutRenovationChanged(renovation);
    }

    @Override
    public void onPortfolioClicked() {
        view.showPortfolioTypesDialog();
    }

    @Override
    public void onHomeClicked() {
        property.setInvestment(false);
        view.notifyActivityAboutInvestmentStatusChanged(false);
        view.showPortfolioTypes(R.string.edit_property_portfolio_type_home);
    }

    @Override
    public void onInvestmentClicked() {
        property.setInvestment(true);
        view.notifyActivityAboutInvestmentStatusChanged(true);
        view.showPortfolioTypes(R.string.edit_property_portfolio_type_investment);
    }

    @Override
    public void onArchiveClicked() {
        navigator.openArchiveConfirmationScreen(property.getLocationSafe().getFullAddress(), RequestCode.EDIT_PROPERTY_ARCHIVE_CONFIRMATION);
    }

    @Override
    public void onArchiveConfirmed() {
        property.getPropertyListingSafe().setState(PropertyStatus.ARCHIVED);
        view.showPortfolioTypes(R.string.edit_property_portfolio_type_archived);
        view.notifyActivityAboutPropertyStatusChanged(PropertyStatus.ARCHIVED);
    }

    @Override
    public void onPropertyFinanceChanged(PropertyFinance finance) {
        property.setPropertyFinance(finance);
        view.notifyActivityAboutPropertyFinanceChanged(finance);
    }

    private void initPropertyListing(PropertyListing propertyListing) {
        if (propertyListing.isPrivate()) {
            view.showMarketplaceState(R.string.edit_property_private);
            view.showMarketplaceStateIndicator(ColorUtils.getPrivatePropertyStateColor());
            view.hideVerificationSection();
        } else if (propertyListing.isPublic()) {
            view.showMarketplaceState(R.string.edit_property_public);
            view.showMarketplaceStateIndicator(ColorUtils.getPublicPropertyStateColor(property.getVerifications()));
            view.showVerificationSection();
        } else if (propertyListing.isArchived()) {
            //todo: show label "Archived" and make it disabled
        }
    }

    private void initPropertyType() {
        List<PropertyType> propertyTypes = new ArrayList<>();
        propertyTypes.addAll(view.getPropertyTypesFromExtras());

        int currentType = 0;
        for (int i = 0; i < propertyTypes.size(); i++) {
            if (propertyTypes.get(i).getKey().equals(property.getType())) {
                currentType = i;
                break;
            }
        }
        view.initPropertyTypes(ListExtKt.toPickerItems(propertyTypes), currentType);
    }

    private void showPortfolioType() {
        if (PropertyStatus.ARCHIVED.equals(property.getPropertyListingSafe().getState())) {
            view.showPortfolioTypes(R.string.edit_property_portfolio_type_archived);
        } else if (property.isInvestment()) {
            view.showPortfolioTypes(R.string.edit_property_portfolio_type_investment);
        } else {
            view.showPortfolioTypes(R.string.edit_property_portfolio_type_home);
        }
    }
}
