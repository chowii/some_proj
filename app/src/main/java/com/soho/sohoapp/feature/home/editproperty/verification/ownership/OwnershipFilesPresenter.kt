package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.net.Uri
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.dtos.PropertyResult
import com.soho.sohoapp.data.dtos.VerificationResult
import com.soho.sohoapp.data.enums.VerificationType
import com.soho.sohoapp.data.listdata.AddFile
import com.soho.sohoapp.data.listdata.Displayable
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
import okhttp3.ResponseBody

class OwnershipFilesPresenter(private val view: OwnershipFilesContract.ViewInteractable,
                              private val navigator: NavigatorInterface,
                              private val permissionManager: PermissionManagerInterface,
                              private val fileHelper: FileHelper) : AbsPresenter, OwnershipFilesContract.ViewPresentable {

    private val attachmentsFromServer = mutableListOf<Displayable>()
    private val displayableList = mutableListOf<Displayable>()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var property: Property
    private var permissionDisposable: Disposable? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getProperty()

        val ownershipVerification = property.verifications?.find { VerificationType.PROPERTY == it.type }
        ownershipVerification?.let {
            attachmentsFromServer.addAll(it.attachments)
        }

        displayableList.add(AddFile())
        displayableList.addAll(attachmentsFromServer)
        view.setFileList(displayableList)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onAddFileClicked() {
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
        val attachment = Attachment().apply {
            this.filePath = path
            this.holder = R.drawable.bc_add_new_file
        }
        displayableList.add(attachment)
        view.setFileList(displayableList)
    }

    override fun onPhotoPicked(uri: Uri) {
        val attachment = Attachment().apply {
            this.uri = uri
            this.holder = R.drawable.bc_add_new_file
        }
        displayableList.add(attachment)
        view.setFileList(displayableList)
    }

    override fun onDeleteIconClicked(attachment: Attachment) {
        displayableList.remove(attachment)
        view.setFileList(displayableList)
    }

    override fun onSubmitClicked() {
        val newAttachments = displayableList
                .filterIsInstance<Attachment>()
                .filter { it.fileUrl == null }

        val removedAttachements = attachmentsFromServer.removeAll(displayableList)

        if (!newAttachments.isEmpty()) {
            view.showLoadingDialog()
            compositeDisposable.add(Converter.toImageRequestBody(fileHelper, newAttachments, property.id)
                    .switchMap<VerificationResult>
                    { requestBody ->
                        DEPENDENCIES.sohoService.sendPropertyVerificationAttachments(requestBody)
                    }
                    .map { result -> Converter.toVerification(result) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { property ->
                                view.hideLoadingDialog()
                                property?.let { property ->
                                    navigator.exitWithResultCodeOk(property)
                                } ?: navigator.exitWithResultCodeOk()
                            },
                            {
                                view.hideLoadingDialog()
                                view.showError(it)
                            }))
        } else {
            view.showValidationMessage(R.string.verification_ownership_validation_error)
        }
    }
}