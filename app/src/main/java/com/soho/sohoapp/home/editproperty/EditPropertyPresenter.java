package com.soho.sohoapp.home.editproperty;

import android.net.Uri;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.Navigator;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.permission.PermissionManager;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.FileHelper;

import java.util.ArrayList;
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
    private PortfolioProperty property;

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
        property = view.getProperty();

        initPropertyImages(property);
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
        propertyImages.add(propertyImage);
        setPropertyImages();
        sendImageOnServer(propertyImage);
    }

    @Override
    public void onPhotoPicked(Uri uri) {
        clearImagesListIfNeeded();
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setUri(uri);
        propertyImages.add(propertyImage);
        setPropertyImages();
        sendImageOnServer(propertyImage);
    }

    private void sendImageOnServer(PropertyImage propertyImage) {
        Disposable disposable = Converter.toImageRequestBody(fileHelper, propertyImage)
                .flatMap(requestBody -> ApiClient.getService()
                        .sendPropertyPhoto(property.getId(), requestBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    private void clearImagesListIfNeeded() {
        if (propertyImages.size() == 1 && propertyImages.get(0).getDrawableId() != 0) {
            propertyImages.clear();
        }
    }

    private void setPropertyImages() {
        view.setPropertyImages(propertyImages);
        int size = propertyImages.size();
        if (size > 0) {
            view.setCurrentPropertyImage(size - 1);
        }
    }

    private void initPropertyImages(PortfolioProperty property) {
        List<PropertyImage> propertyImagesFromServer = property.getPropertyImageList();
        if (propertyImagesFromServer.isEmpty()) {
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setDrawableId(R.drawable.others);
            propertyImages.add(propertyImage);
        } else {
            propertyImages.addAll(propertyImagesFromServer);
        }
        setPropertyImages();
    }
}

