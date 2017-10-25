package com.soho.sohoapp.feature.home.editproperty.files

import android.net.Uri
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.data.enums.FileCostType
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.data.models.SohoOption
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.FileHelper
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditPropertyAddFilePresenter(private var interactable: EditPropertyAddFileContract.ViewInteractable,
                                   private var currentFile: PropertyFile,
                                   var property: Property,
                                   val navigator: NavigatorInterface,
                                   private val rxGallery: Maybe<MutableList<Uri>>,
                                   private val rxCamera: Maybe<Uri>,
                                   private val permissionManager: PermissionManagerImpl,
                                   private val addPhotoDialog: AddPhotoDialog,
                                   private val fileHelper: FileHelper)
    : EditPropertyAddFileContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var cameraDisposable: Disposable? = null
    private var galleryDisposable: Disposable? = null
    private var fileTypes: List<SohoOption> = arrayListOf()

    // MARK: - ================== EditPropertyAddFileContract.ViewPresentable methods ==================

    override fun startPresenting() {
        interactable.setViewPresentable(this)
        retrieveFileTypes()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun getFileTypesList(): List<SohoOption> = fileTypes

    override fun onSaveClicked() {
        if (dataIsValid()) {
            currentFile.id?.let { currentFileId ->
                updateCurrentFile(currentFileId)
            } ?: createNewFile()
        }
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onAddFileClicked() {
        addPhotoDialog.show(object : AddPhotoDialog.OnItemClickedListener {
            override fun onTakeNewPhotoClicked() {
                selectPhotoFromCamera()
            }

            override fun onChooseFromGalleryClicked() {
                selectPhotoFromGallery()
            }
        })
    }

    override fun getCurrentPropertyFile(): PropertyFile = currentFile

    // MARK: - ================== General methods ==================

    private fun selectPhotoFromGallery() {
        cleanCameraDisposable()
        galleryDisposable = rxGallery.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { photos ->
                            photos.firstOrNull()?.let { photoUri ->
                                currentFile.fileToUploadUri = photoUri
                                interactable.fileSelected(photoUri)
                            }
                        },
                        {
                            interactable.showError(it)
                        })
    }

    private fun selectPhotoFromCamera() {
        cleanCameraDisposable()
        cameraDisposable = permissionManager.requestStoragePermission(RequestCode.EDIT_ACCOUNT_PRESENTER_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { permissionEvent ->
                    if (permissionEvent.isPermissionGranted) {
                        rxCamera.toObservable()
                    } else
                        Observable.empty<Uri>()
                }.subscribe(
                { photoUri ->
                    currentFile.fileToUploadUri = photoUri
                    interactable.fileSelected(photoUri)
                },
                {
                    interactable.showError(it)
                })
    }

    private fun cleanCameraDisposable() {
        cameraDisposable?.dispose()
        galleryDisposable?.dispose()
    }

    private fun retrieveFileTypes() {
        interactable.toggleRetrievingFileTypes(true)
        compositeDisposable.add(
                Dependencies.DEPENDENCIES.sohoService.getFileTypes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .map(Converter::toSohoOptions)
                        .subscribe(
                                { fileTypes ->
                                    this.fileTypes = fileTypes
                                    interactable.toggleRetrievingFileTypes(false)
                                    if (currentFile.id == null) {
                                        currentFile.documentType = fileTypes.first()?.key
                                        currentFile.isCost = fileTypes.first()?.category?.equals(FileCostType.EXPENSE)
                                    }
                                    interactable.setupForm(currentFile, fileTypes)
                                },
                                { throwable ->
                                    interactable.toggleRetrievingFileTypes(false)
                                    interactable.showError(throwable)
                                })
        )
    }

    private fun updateCurrentFile(fileId: Int) {
        interactable.toggleSendingFileIndicator(true)
        compositeDisposable.add(Converter.toPropertyFileRequestBody(fileHelper, currentFile)
                .switchMap { body ->
                    Dependencies.DEPENDENCIES.sohoService.updatePropertyFile(property.id, fileId, body)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(Converter::toPropertyFile)
                .subscribe(
                        { propertyFile ->
                            interactable.toggleSendingFileIndicator(false)
                            navigator.exitWithResultCodeOk(propertyFile, false)
                        },
                        { throwable ->
                            interactable.toggleSendingFileIndicator(false)
                            interactable.showError(throwable)
                        })
        )
    }

    private fun createNewFile() {
        interactable.toggleSendingFileIndicator(true)
        compositeDisposable.add(Converter.toPropertyFileRequestBody(fileHelper, currentFile)
                .switchMap { body ->
                    Dependencies.DEPENDENCIES.sohoService.createPropertyFile(property.id, body)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(Converter::toPropertyFile)
                .subscribe(
                        { propertyFile ->
                            interactable.toggleSendingFileIndicator(false)
                            navigator.exitWithResultCodeOk(propertyFile, false)
                        },
                        { throwable ->
                            interactable.toggleSendingFileIndicator(false)
                            interactable.showError(throwable)
                        })
        )
    }

    private fun dataIsValid(): Boolean {
        var dataIsValid = true
        if (currentFile.fileToUploadUri == null && currentFile.filePhoto == null) {
            interactable.showToastMessage(R.string.add_file_data_not_valid)
            dataIsValid = false
        }
        return dataIsValid
    }
}