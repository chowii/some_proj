package com.soho.sohoapp.home.editproperty;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.home.addproperty.data.PropertyType;
import com.soho.sohoapp.home.editproperty.data.Property;
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.navigator.Navigator;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.permission.PermissionManager;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.FileHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewActionsListener {
    private final EditPropertyContract.View view;
    private final Navigator navigator;
    private final PermissionManager permissionManager;
    private final FileHelper fileHelper;
    private final List<PropertyImage> propertyImages;
    private final CompositeSubscription compositeSubscription;
    private final CompositeDisposable compositeDisposable;
    private Subscription permissionSubscription;
    private Property property;

    public EditPropertyPresenter(EditPropertyContract.View view,
                                 Navigator navigator,
                                 PermissionManager permissionManager,
                                 FileHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.view = view;
        this.navigator = navigator;
        this.permissionManager = permissionManager;
        propertyImages = new ArrayList<>();
        compositeSubscription = new CompositeSubscription();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        view.showLoadingDialog();

        Disposable disposable = ApiClient.getService().getProperty(view.getPropertyId())
                .map(Converter::toProperty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    property = result;
                    initPropertyImages();
                    PropertyAddress address = result.getAddress();
                    if (address != null) {
                        view.showAddress1(address.getAddressLine1());
                        view.showAddress2(address.getAddressLine2());
                    }
                    view.hideLoadingDialog();
                }, throwable -> {
                    view.hideLoadingDialog();
                    view.showLoadingError();
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void stopPresenting() {
        compositeSubscription.unsubscribe();
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
            permissionSubscription = permissionManager.requestStoragePermission(RequestCode.EDIT_PROPERTY_PRESENTER_STORAGE)
                    .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(permissionEvent -> {
                        if (permissionEvent.isPermissionGranted()) {
                            view.capturePhoto();
                        }
                        permissionSubscription.unsubscribe();
                    }, Throwable::printStackTrace);
            compositeSubscription.add(permissionSubscription);
        }
    }

    @Override
    public void onChooseFromGalleryClicked() {
        view.pickImageFromGallery();
    }

    @Override
    public void onPhotoReady(String path) {
        clearImagesListIfNeeded();
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setFilePath(path);
        propertyImage.setHolder(PropertyType.getDefaultImage(property.getType()));
        propertyImages.add(propertyImage);
        setPropertyImages(propertyImages, true);
        sendImageOnServer(propertyImage);
    }

    @Override
    public void onPhotoPicked(Uri uri) {
        clearImagesListIfNeeded();
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setUri(uri);
        propertyImage.setHolder(PropertyType.getDefaultImage(property.getType()));
        propertyImages.add(propertyImage);
        setPropertyImages(propertyImages, true);
        sendImageOnServer(propertyImage);
    }

    private void sendImageOnServer(PropertyImage propertyImage) {
        Converter.toImageRequestBody(fileHelper, propertyImage)
                .flatMap(requestBody -> ApiClient.getService()
                        .sendPropertyPhoto(property.getId(), requestBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void clearImagesListIfNeeded() {
        if (propertyImages.size() == 1 && propertyImages.get(0).getDrawableId() != 0) {
            propertyImages.clear();
        }
    }

    private void setPropertyImages(@NonNull List<PropertyImage> propertyImages, boolean scrollToLast) {
        view.setPropertyImages(propertyImages);
        if (scrollToLast) {
            int size = propertyImages.size();
            if (size > 0) {
                view.setCurrentPropertyImage(size - 1);
            }
        }
    }

    private void initPropertyImages() {
        List<PropertyImage> propertyImagesFromServer = property.getPropertyImageList();
        if (propertyImagesFromServer.isEmpty()) {
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setDrawableId(PropertyType.getDefaultImage(property.getType()));
            propertyImages.add(propertyImage);
        } else {
            Collections.reverse(propertyImagesFromServer);
            propertyImages.addAll(propertyImagesFromServer);
        }
        setPropertyImages(propertyImages, false);
    }
}

