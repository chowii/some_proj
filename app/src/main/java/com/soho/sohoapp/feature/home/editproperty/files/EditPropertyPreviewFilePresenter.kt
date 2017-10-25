package com.soho.sohoapp.feature.home.editproperty.files

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.utils.Converter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditPropertyPreviewFilePresenter(private val view: EditPropertyPreviewFileContract.ViewInteractable,
                                       private val navigator: NavigatorInterface) : AbsPresenter, EditPropertyPreviewFileContract.ViewPresentable {
    private lateinit var property: Property
    private lateinit var propertyFile: PropertyFile
    private val compositeDisposable = CompositeDisposable()

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras() ?: Property()
        propertyFile = view.getPropertyFileFromExtras() ?: PropertyFile()
        view.showFileImage(propertyFile.filePhoto)

        if (DEPENDENCIES.userPrefs.user == null) {
            compositeDisposable.add(
                    DEPENDENCIES.sohoService.getProfile()
                            .map { userResult -> Converter.toUser(userResult) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { user ->
                                        showDeleteOptionIfNeed(user)
                                    },
                                    { throwable ->
                                        view.showError(throwable)
                                    }))
        } else {
            showDeleteOptionIfNeed(DEPENDENCIES.userPrefs.user)
        }
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onEditClicked() {
        navigator.openEditPropertyAddFileScreen(property, propertyFile, RequestCode.EDIT_PROPERTY_PREVIEW_FILE_EDIT_MODE)
    }

    override fun onDeleteClicked() {
        view.showConfirmationDialog()
    }

    override fun onConfirmDeletionClicked() {
        view.showLoadingDialog()
        propertyFile.id?.let {
            compositeDisposable.add(
                    DEPENDENCIES.sohoService.deletePropertyFile(property.id, it)
                            .map { fileResult -> Converter.toPropertyFile(fileResult) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { propertyFile ->
                                        view.hideLoadingDialog()
                                        navigator.exitWithResultCodeOk(propertyFile, true)
                                    },
                                    {
                                        view.hideLoadingDialog()
                                        view.showError(it)
                                    }))
        }
    }

    override fun onPropertyFileUpdated(propertyFile: PropertyFile) {
        navigator.exitWithResultCodeOk(propertyFile, false)
    }

    private fun showDeleteOptionIfNeed(user: User?) {
        val owner = property.propertyUsers?.find { it.userDetails?.id == user?.id }
        owner?.let { view.showDeleteOption() }
    }
}