package com.soho.sohoapp.feature.home.editproperty;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.permission.PermissionManagerInterface;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.FileHelper;
import com.soho.sohoapp.utils.QueryHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewPresentable {
    private final EditPropertyContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final PermissionManagerInterface permissionManager;
    private final FileHelper fileHelper;
    private final List<Image> propertyImages;
    private final ArrayList<PropertyType> propertyTypes;
    private final CompositeDisposable compositeDisposable;
    private Disposable permissionDisposable;
    private Property property;

    EditPropertyPresenter(EditPropertyContract.ViewInteractable view,
                          NavigatorInterface navigator,
                          PermissionManagerInterface permissionManager,
                          FileHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.view = view;
        this.navigator = navigator;
        this.permissionManager = permissionManager;
        propertyImages = new ArrayList<>();
        propertyTypes = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showLoadingView();

        compositeDisposable.add(DEPENDENCIES.getSohoService().getPropertyTypes()
                .map(Converter::toPropertyTypeList)
                .switchMap(propertyTypes -> {
                    EditPropertyPresenter.this.propertyTypes.addAll(propertyTypes);
                    return DEPENDENCIES.getSohoService().getProperty(view.getPropertyId());
                })
                .map(Converter::toProperty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->
                {
                    property = result;
                    view.initTabs(property, propertyTypes);
                    initPropertyImages();
                    Location address = result.getLocation();
                    if (address != null) {
                        view.showAddress1(address.getAddressLine1());
                        view.showAddress2(address.getAddressLine2());
                    }
                    view.hideLoadingView();
                }, throwable ->
                {
                    view.hideLoadingView();
                    view.showError(throwable);
                }));
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onAddPhotoClicked() {
        view.showAddPhotoDialog();
    }

    @Override
    public void onTakeNewPhotoClicked() {
        if (permissionManager.hasStoragePermission()) {
            view.capturePhoto();
        } else {
            permissionDisposable = permissionManager.requestStoragePermission(RequestCode.EDIT_PROPERTY_PRESENTER_STORAGE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(permissionEvent -> {
                        if (permissionEvent.isPermissionGranted()) {
                            view.capturePhoto();
                        }
                        permissionDisposable.dispose();
                    }, Throwable::printStackTrace);
            compositeDisposable.add(permissionDisposable);
        }
    }

    @Override
    public void onChooseFromGalleryClicked() {
        view.pickImageFromGallery();
    }

    @Override
    public void onPhotoReady(String path) {
        clearImagesListIfNeeded();
        Image propertyImage = new Image();
        propertyImage.setFilePath(path);
        propertyImage.setHolder(PropertyType.getDefaultImage(property.getType()));
        propertyImages.add(propertyImage);
        setPropertyImages(propertyImages, true);
        sendImageOnServer(propertyImage);
    }

    @Override
    public void onPhotoPicked(Uri uri) {
        clearImagesListIfNeeded();
        Image propertyImage = new Image();
        propertyImage.setUri(uri);
        propertyImage.setHolder(PropertyType.getDefaultImage(property.getType()));
        propertyImages.add(propertyImage);
        setPropertyImages(propertyImages, true);
        sendImageOnServer(propertyImage);
    }

    @Override
    public void onPropertyAddressChanged(Location location) {
        view.showAddress1(location.getAddressLine1());
        view.showAddress2(location.getAddressLine2());
        property.setLocation(location);
    }

    @Override
    public void onSaveClicked() {
        view.showLoadingDialog();
        QueryHashMap listingMap = Converter.toMap(property.getPropertyListingSafe());
        compositeDisposable.add(
                DEPENDENCIES.getSohoService().updatePropertyListing(property.getId(), listingMap)
                        .switchMap(propertyListingResult ->
                        {
                            QueryHashMap propertyMap = Converter.toPropertyMap(property);
                            return DEPENDENCIES.getSohoService().updateProperty(property.getId(), propertyMap);
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(propertyResult -> {
                            view.hideLoadingDialog();
                            navigator.exitCurrentScreen();
                        }, throwable -> {
                            view.hideLoadingDialog();
                            view.showError(throwable);
                        }));
    }

    private void sendImageOnServer(Image propertyImage) {
        compositeDisposable.add(
                Converter.toPropertyImageRequestBody(fileHelper, propertyImage)
                        .switchMap(requestBody -> DEPENDENCIES.getSohoService()
                                .sendPropertyPhoto(property.getId(), requestBody))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Functions.emptyConsumer(), view::showError));
    }

    private void clearImagesListIfNeeded() {
        if (propertyImages.size() == 1 && propertyImages.get(0).getDrawableId() != 0) {
            propertyImages.clear();
        }
    }

    private void setPropertyImages(@NonNull List<Image> propertyImages, boolean scrollToLast) {
        view.setPropertyImages(propertyImages);
        if (scrollToLast) {
            int size = propertyImages.size();
            if (size > 0) {
                view.setCurrentPropertyImage(size - 1);
            }
        }
    }

    @Override
    public void onHeaderPhotoClicked(List<Image> images, int currentItem) {
        navigator.showGallery(images, currentItem);
    }

    @Override
    public void onRoomsNumberChanged(int bedrooms, int bathrooms, int carspots) {
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setCarspots(carspots);
    }

    @Override
    public void onPropertyTypeChanged(String type) {
        property.setType(type);
    }

    @Override
    public void onRenovationChanged(String renovation) {
        property.setRenovationDetails(renovation);
    }

    @Override
    public void onInvestmentStatusChanged(boolean isInvestment) {
        property.setInvestment(isInvestment);
    }

    @Override
    public void onPropertyStatusChanged(String propertyStatus) {
        property.getPropertyListingSafe().setState(propertyStatus);
    }

    private void initPropertyImages() {
        List<Image> propertyImagesFromServer = property.getPhotosAsImages();
        if (propertyImagesFromServer.isEmpty()) {
            Image propertyImage = new Image();
            propertyImage.setDrawableId(PropertyType.getDefaultImage(property.getType()));
            propertyImages.add(propertyImage);
        } else {
            Collections.reverse(propertyImagesFromServer);
            propertyImages.addAll(propertyImagesFromServer);
        }
        setPropertyImages(propertyImages, false);
    }
}

