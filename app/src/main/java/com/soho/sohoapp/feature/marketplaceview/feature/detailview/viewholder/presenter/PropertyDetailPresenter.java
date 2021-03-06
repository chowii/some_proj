package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.InspectionTime;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.User;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.EnquireActivity;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailDescriptionItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailHeaderItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyLocationImageItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract.PropertyDetailContract;
import com.soho.sohoapp.feature.marketplaceview.feature.filters.fitlermodel.HeaderItem;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.preferences.UserPrefs;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailPresenter implements PropertyDetailContract.ViewPresentable {

    private final PropertyDetailContract.ViewInteractable interactable;
    private final Activity context;
    private Disposable mDisposable;

    public Property getProperty() {
        return property;
    }

    private Property property;

    public PropertyDetailPresenter(PropertyDetailContract.ViewInteractable interactable, Activity context) {
        this.interactable = interactable;
        this.context = context;
    }

    @Override
    public void startPresenting() {
    }

    @Override
    public void retrieveProperty(int id) {
        interactable.setRefreshing(true);
        mDisposable = ApiClient.getService().getProperty(id)
                .map(Converter::toProperty)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(property -> {
                    this.property = property;
                    List<BaseModel> descriptionList = new ArrayList<>();
                    descriptionList.add(addPropertyDetailHeaderItem(property));
                    descriptionList.addAll(
                            addPropertyDescription(
                                    new PropertyDetailDescriptionItem(property.getDisplayDescription())
                            ));
                    descriptionList.add(new HeaderItem<>(getString(
                            R.string.property_detail_header_inspection_times),
                            R.layout.item_header));

                    if (!property.getPropertyListing().isAppointmentOnly())
                        if (!property.getPropertyListing().getInspectionTimes().isEmpty())
                            descriptionList.addAll(
                                    addInspectionItem(
                                            property.getPropertyListing().getInspectionTimes(),
                                            property.getLocation(),
                                            property.getPropertyListing().isAppointmentOnly()
                                    )
                            );
                        else {
                            descriptionList.add(new PropertyHostTimeItem(
                                    null,
                                    property.getLocation(),
                                    getString(R.string.property_state_inspection),
                                    true));
                        }

                    descriptionList.addAll(addPropertyIfAuctioned(property));
                    descriptionList.addAll(addPropertyLocationImage(property.getLocation()));
                    descriptionList.add(new HeaderItem<>(String.format("Last Updated: %s",
                            LongExtKt.toStringWithDisplayFormat(property.getUpdatedAt())), R.layout.item_header));
                    interactable.configureAdapter(descriptionList);
                    interactable.populateView(property);
                    configureEnquireButton(property);
                    interactable.setRefreshing(false);
                }, throwable -> {
                    DEPENDENCIES.getLogger().e("throwable: " + throwable.toString(), throwable);
                    interactable.setRefreshing(false);
                    interactable.showError(throwable);
                });

    }

    private void configureEnquireButton(Property property) {
        UserPrefs userPrefs = DEPENDENCIES.getUserPrefs();
        User user = userPrefs.getUser();
        int ownerId = 0;
        int agentId = 0;
        if (property.getOwner() != null) property.getOwner().getId();
        if (property.getAgent() != null) property.getAgent().getId();

        boolean isUserProperty = user.getId() == ownerId;
        boolean isAgentProperty = user.getId() != agentId;

        if (userPrefs.isUserLoggedIn() && isUserProperty && isAgentProperty)
            interactable.hideEnquireButton();
        else
            interactable.showEnquireButton();
    }

    @Override
    public void onEnquiry() {
        Intent intent = new Intent(context, EnquireActivity.class);
        intent.putExtra(EnquireActivity.PROPERTY_ENQUIRY_INTENT_EXTRA, property);
        context.startActivity(intent);
    }

    @NonNull
    private PropertyDetailHeaderItem addPropertyDetailHeaderItem(Property property) {
        PropertyDetailHeaderItem headerItem = new PropertyDetailHeaderItem();
        headerItem.setHeader(property.getDisplayTitle());
        headerItem.setBedroom(property.getBedrooms());
        headerItem.setBathroom(property.getBathrooms());
        headerItem.setCarspot(property.getCarspots());
        headerItem.setPropertySize(property.getLandSize());
        headerItem.setLandSizeMeasurament(property.getLandSizeMeasurement());
        headerItem.applyPropertyTypeChange(property.getType());
        headerItem.setPropertyState(property.getState());
        headerItem.setRepresentingUser(property.getRepresentingUser());
        return headerItem;
    }

    private List<BaseModel> addPropertyDescription(PropertyDetailDescriptionItem propertyDetailDescriptionItem) {
        List<BaseModel> descriptionList = new ArrayList<>();
        descriptionList.add(new HeaderItem<>(getString(R.string.property_detail_header_description), R.layout.item_header));
        descriptionList.add(new PropertyDetailDescriptionItem(propertyDetailDescriptionItem.getDescription()));
        return descriptionList;
    }

    private List<BaseModel> addPropertyIfAuctioned(Property property) {
        List<BaseModel> descriptionList = new ArrayList<>();
        if (property.getState().equalsIgnoreCase(getString(R.string.property_state_auction))) {
            descriptionList.add(new HeaderItem<>(getString(R.string.property_state_auction), R.layout.item_header));
            descriptionList.add(new PropertyHostTimeItem(
                            property.getPropertyListing().getAuctionTime(),
                            property.getLocation(),
                            property.getPropertyListing().isOnSiteAuction(),
                            property.getState()
                    )
            );
        }
        return descriptionList;
    }

    private List<BaseModel> addPropertyLocationImage(Location location) {
        List<BaseModel> locationImageList = new ArrayList<>();
        LatLng latLng = new LatLng(location.getLatitude(),
                location.getLongitude());

        locationImageList.add(new HeaderItem<>(getString(R.string.property_detail_header_location), R.layout.item_header));
        locationImageList.add(new PropertyLocationImageItem(latLng, location.getMaskAddress()));
        return locationImageList;
    }

    private List<BaseModel> addInspectionItem(List<InspectionTime> inspectionTimeList, Location location, boolean isAppoinmentOnly) {
        List<BaseModel> modelList = new ArrayList<>();
        for (InspectionTime inspectionTime : inspectionTimeList)
            modelList.add(new PropertyHostTimeItem(inspectionTime, location, getString(R.string.property_state_inspection), isAppoinmentOnly));
        return modelList;
    }


    private String getString(@StringRes int stringRes) {
        return SohoApplication.getContext().getString(stringRes);
    }

    @Override
    public void onHeaderPhotoClicked(List<Image> images, int currentItem) {
        NavigatorImpl.newInstance(context).openGallery(images, currentItem);
    }

    @Override
    public void stopPresenting() {
        mDisposable.dispose();
    }
}
