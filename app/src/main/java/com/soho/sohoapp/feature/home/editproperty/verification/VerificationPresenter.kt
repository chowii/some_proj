package com.soho.sohoapp.feature.home.editproperty.verification

import android.net.Uri
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.dtos.VerificationResult
import com.soho.sohoapp.data.enums.VerificationType
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.permission.PermissionManagerInterface
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.FileHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VerificationPresenter(private val view: VerificationContract.ViewInteractable,
                            private val navigator: NavigatorInterface,
                            private val fileHelper: FileHelper,
                            private val permissionManager: PermissionManagerInterface) : AbsPresenter, VerificationContract.ViewPresentable {

    private val compositeDisposable = CompositeDisposable()
    private var property: Property? = null
    private var permissionDisposable: Disposable? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras()

        val photoVerification = property?.verifications?.find {
            VerificationType.LICENCE == it.type
        }

        val ownershipVerification = property?.verifications?.find {
            VerificationType.PROPERTY == it.type
        }

        photoVerification?.let {
            view.showPhotoVerificationStatus(it.getStateLabel())
            view.showPhotoVerificationColor(it.getColor())
        }

        ownershipVerification?.let {
            view.showOwnershipVerificationStatus(it.getStateLabel())
            view.showOwnershipVerificationColor(it.getColor())
        }
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onOwnershipProofClicked() {
        navigator.openOwnershipVerificationScreen(property)
    }

    override fun onPhotoIdClicked() {
        view.showAddPhotoDialog()
    }

    override fun onTakeNewPhotoClicked() {
        if (permissionManager.hasStoragePermission()) {
            view.capturePhoto()
        } else {
            permissionDisposable = permissionManager.requestStoragePermission(RequestCode.EDIT_PROPERTY_PRESENTER_STORAGE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ permissionEvent ->
                        if (permissionEvent.isPermissionGranted) {
                            view.capturePhoto()
                        }
                        permissionDisposable?.dispose()
                    }, { it.printStackTrace() })
            permissionDisposable?.let { compositeDisposable.add(it) }
        }
    }

    override fun onChooseFromGalleryClicked() {
        view.pickImageFromGallery()
    }

    override fun onPhotoReady(path: String) {
        sendImageToServer(Attachment().apply { filePath = path })
    }

    override fun onPhotoPicked(uri: Uri) {
        sendImageToServer(Attachment().apply { this.uri = uri })
    }

    private fun sendImageToServer(attachment: Attachment) {
        view.showLoadingDialog()
        compositeDisposable.add(Converter.toPhotoVerificationRequestBody(fileHelper, attachment)
                .switchMap<VerificationResult> { requestBody ->
                    DEPENDENCIES.sohoService.verifyPhotoId(requestBody)
                }
                .map { verification -> Converter.toVerification(verification) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { verification ->
                            view.hideLoadingDialog()
                            verification?.let {
                                view.showPhotoVerificationStatus(verification.getStateLabel())
                                view.showPhotoVerificationColor(verification.getColor())
                            }
                        },
                        {
                            view.hideLoadingDialog()
                            view.showError(it)
                        }))
    }
}