package com.soho.sohoapp.feature.home.editproperty;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.permission.PermissionManager;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.FileHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewPresentable {
    private final EditPropertyContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final PermissionManager permissionManager;
    private final FileHelper fileHelper;
    private final List<Image> propertyImages;
    private final CompositeSubscription compositeSubscription;
    private final CompositeDisposable compositeDisposable;
    private Subscription permissionSubscription;
    private Property property;

    EditPropertyPresenter(EditPropertyContract.ViewInteractable view,
                          NavigatorInterface navigator,
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
        view.setPresentable(this);
        view.showLoadingDialog();

        compositeDisposable.add(DEPENDENCIES.getSohoService().getProperty(view.getPropertyId())
                .map(Converter::toProperty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->
                {
                    property = result;
                    view.initTabs(property);
                    initPropertyImages();
                    Location address = result.getLocation();
                    if (address != null) {
                        view.showAddress1(address.getAddressLine1());
                        view.showAddress2(address.getAddressLine2());
                    }
                    view.hideLoadingDialog();
                }, throwable ->
                {
                    view.hideLoadingDialog();
                    view.showLoadingError();
                }));
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

    private void sendImageOnServer(Image propertyImage) {
        compositeDisposable.add(
                Converter.toImageRequestBody(fileHelper, propertyImage)
                        .switchMap(requestBody -> DEPENDENCIES.getSohoService()
                                .sendPropertyPhoto(property.getId(), requestBody))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe());
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

