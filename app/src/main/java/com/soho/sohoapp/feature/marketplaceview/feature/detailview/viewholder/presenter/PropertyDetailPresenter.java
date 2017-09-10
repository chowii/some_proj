package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.R;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                .subscribe(
                        property -> {
                            List<BaseModel> descriptionList = new ArrayList<>();
                            PropertyDescribable describable = property;

                            descriptionList.add(addPropertyDetailHeaderItem(describable));

                            descriptionList.addAll(addPropertyDescription(new PropertyDetailDescriptionItem(describable.description())));

                            descriptionList.add(new HeaderItem<String>("Inspection Times", R.layout.item_header));
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
                                    descriptionList.add(new PropertyHostTimeItem(null, describable.location(), "Inspection", true));
                                }

                            descriptionList.addAll(addPropertyIfAuctioned(describable));

                            descriptionList.addAll(addPropertyLocationImage(describable.location()));
                            descriptionList.add(new HeaderItem<String>(retrieveLastUpdateTime(describable.lastUpdatedAt()), R.layout.item_header));

                            interactable.configureAdapter(descriptionList);
                        }, throwable -> DEPENDENCIES.getLogger().d("throwable: " + throwable.toString())
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
        headerItem.applyPropertyTypeChange(describe.typeOfProperty());
        return headerItem;
    }

    private List<BaseModel> addPropertyDescription( PropertyDetailDescriptionItem propertyDetailDescriptionItem){
        List<BaseModel> descriptionList = new ArrayList<>();
        descriptionList.add(new HeaderItem<String>("Description", R.layout.item_header));
        descriptionList.add(new PropertyDetailDescriptionItem(propertyDetailDescriptionItem.getDescription()));
        return descriptionList;
    }

    private List<BaseModel> addPropertyIfAuctioned(PropertyDescribable describable) {
        List<BaseModel> descriptionList = new ArrayList<>();
        if (describable.state().equalsIgnoreCase("auction")){
            descriptionList.add(new HeaderItem<String>("Auction", R.layout.item_header));
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

        locationImageList.add(new HeaderItem<String>("Location", R.layout.item_header));
        locationImageList.add(new PropertyLocationImageItem(latLng, location.isAddressMasked()));
        return locationImageList;
    }

    private String retrieveLastUpdateTime(Date date) {
        Calendar updateCalendar = Calendar.getInstance();
        updateCalendar.setTime(date);
        StringBuilder lastUpdatedBuilder = new StringBuilder("Last Updated: ");
        lastUpdatedBuilder.append(updateCalendar.get(Calendar.DAY_OF_MONTH));
        lastUpdatedBuilder.append(" ");
        lastUpdatedBuilder.append(updateCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        lastUpdatedBuilder.append(". ");
        lastUpdatedBuilder.append(updateCalendar.get(Calendar.YEAR));

        return lastUpdatedBuilder.toString();
    }


    private List<BaseModel> addInspectionItem(List<PropertyInspectionTime> inspectionTimeList, PropertyLocation location, boolean isAppoinmentOnly) {
        List<BaseModel> modelList = new ArrayList<>();
        for(PropertyInspectionTime inspectionTime : inspectionTimeList)
            modelList.add(new PropertyHostTimeItem(inspectionTime, location, "Inspection", isAppoinmentOnly));
        return modelList;
    }

    @Override
    public void stopPresenting() { mDisposable.dispose(); }
}
