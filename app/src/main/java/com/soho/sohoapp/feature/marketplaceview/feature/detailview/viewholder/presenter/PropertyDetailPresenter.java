package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.data.PropertyInspectionTime;
import com.soho.sohoapp.data.PropertyLocation;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDescribable;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailDescriptionItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailHeaderItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyLocationImageItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract.PropertyDetailContract;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.network.ApiClient;

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
    private Disposable mDisposable;

    public PropertyDetailPresenter(PropertyDetailContract.ViewInteractable interactable) {
        this.interactable = interactable;
    }

    @Override
    public void startPresenting() { }

    @Override
    public void retrieveProperty(int id) {
        mDisposable = ApiClient.getService().getPropertyById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(property -> {
                            List<BaseModel> descriptionList = new ArrayList<>();
                            PropertyDescribable describable = property;
                            descriptionList.add(addPropertyDetailHeaderItem(describable));
                            descriptionList.addAll(
                                    addPropertyDescription(
                                            new PropertyDetailDescriptionItem(describable.description())
                                    ));
                            descriptionList.add(new HeaderItem<>(getString(
                                                            R.string.property_detail_header_inspection_times),
                                                            R.layout.item_header));

                            if (!describable.propertyListing().isAppointmentOnly())
                                if (!describable.propertyListing().retrieveInspectionTimes().isEmpty())
                                    descriptionList.addAll(
                                            addInspectionItem(
                                                    describable.propertyListing().retrieveInspectionTimes(),
                                                    describable.location(),
                                                    describable.propertyListing().isAppointmentOnly()
                                            )
                                    );
                                else {
                                    descriptionList.add(new PropertyHostTimeItem(
                                            null,
                                            describable.location(),
                                            getString(R.string.property_state_inspection),
                                            true));
                                }

                            descriptionList.addAll(addPropertyIfAuctioned(describable));
                            descriptionList.addAll(addPropertyLocationImage(describable.location()));
                            descriptionList.add(new HeaderItem<>(describable.retrieveDisplayableLastUpdatedAt(), R.layout.item_header));
                            interactable.configureAdapter(descriptionList);
                        }, throwable -> DEPENDENCIES.getLogger().e("throwable: " + throwable.toString(), throwable)
                );

    }

    @NonNull
    private PropertyDetailHeaderItem addPropertyDetailHeaderItem(PropertyDescribable describe) {
        PropertyDetailHeaderItem headerItem = new PropertyDetailHeaderItem();

        headerItem.setHeader(describe.title());
        headerItem.setBedroom(describe.numberOfBedrooms());
        headerItem.setBathroom(describe.numberOfBathrooms());
        headerItem.setCarspot(describe.numberOfParking());
        headerItem.setPropertySize(describe.retrieveLandSize());
        headerItem.applyPropertyTypeChange(describe.retrieveDisplayableTypeOfProperty());
        return headerItem;
    }

    private List<BaseModel> addPropertyDescription( PropertyDetailDescriptionItem propertyDetailDescriptionItem){
        List<BaseModel> descriptionList = new ArrayList<>();
        descriptionList.add(new HeaderItem<>(getString(R.string.property_detail_header_description), R.layout.item_header));
        descriptionList.add(new PropertyDetailDescriptionItem(propertyDetailDescriptionItem.getDescription()));
        return descriptionList;
    }

    private List<BaseModel> addPropertyIfAuctioned(PropertyDescribable describable) {
        List<BaseModel> descriptionList = new ArrayList<>();
        if (describable.state().equalsIgnoreCase(getString(R.string.property_state_auction))){
            descriptionList.add(new HeaderItem<>(getString(R.string.property_state_auction), R.layout.item_header));
            descriptionList.add(new PropertyHostTimeItem(
                            describable.propertyListing().retrieveAuctionTime(),
                            describable.location(),
                            describable.propertyListing().isOnSiteAuction(),
                            describable.state()
                    )
            );
        }
        return descriptionList;
    }

    private List<BaseModel> addPropertyLocationImage(PropertyLocation location) {
        List<BaseModel> locationImageList = new ArrayList<>();
        LatLng latLng = new LatLng(location.retrieveLatitude(),
                location.retrieveLongitude());

        locationImageList.add(new HeaderItem<>(getString(R.string.property_detail_header_location), R.layout.item_header));
        locationImageList.add(new PropertyLocationImageItem(latLng, location.isAddressMasked()));
        return locationImageList;
    }

    private List<BaseModel> addInspectionItem(List<PropertyInspectionTime> inspectionTimeList, PropertyLocation location, boolean isAppoinmentOnly) {
        List<BaseModel> modelList = new ArrayList<>();
        for(PropertyInspectionTime inspectionTime : inspectionTimeList)
            modelList.add(new PropertyHostTimeItem(inspectionTime, location, getString(R.string.property_state_inspection), isAppoinmentOnly));
        return modelList;
    }


    private String getString(@StringRes int stringRes){ return SohoApplication.getContext().getString(stringRes); }

    @Override
    public void stopPresenting() { mDisposable.dispose(); }
}
