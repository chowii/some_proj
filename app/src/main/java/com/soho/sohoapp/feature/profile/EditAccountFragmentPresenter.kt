package com.soho.sohoapp.feature.profile

import android.app.FragmentManager
import android.net.Uri
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.Image
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.FileHelper
import com.soho.sohoapp.utils.QueryHashMap
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class EditAccountFragmentPresenter(private val view: EditAccountContract.ViewInteractable
                                   , private val user: User?, val navigator: NavigatorImpl
                                   , private val rxGallery: Maybe<MutableList<Uri>>
                                   , private val rxCamera: Maybe<Uri>
                                   , private val permissionManager: PermissionManagerImpl
                                   , private val addPhotoDialog: AddPhotoDialog
                                   , private val fileHelper: FileHelper)
    : AbsPresenter, EditAccountContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var cameraDisposable: Disposable? = null
    private var galleryDisposable: Disposable? = null
    private var avatarUri: Uri? = null
    private val DATE_PICKER_TAG = "Date Picker Edit account"
    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        user?.let { view.fillUserInfo(user) }
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onEditPhotoClick() {
        addPhotoDialog.show(object : AddPhotoDialog.OnItemClickedListener {
            override fun onTakeNewPhotoClicked() {
                selectPhotoFromCamera()
            }

            override fun onChooseFromGalleryClicked() {
                selectPhotoFromGallery()
            }
        })
    }

    override fun updaterUserProfile(values: QueryHashMap) {
        val avatarImage = Image()
        avatarImage.uri = avatarUri
        view.showLoading()
        compositeDisposable.add(Converter.toImageRequestBodyUser(fileHelper, avatarImage, values)
                .switchMap { imageRequestBody ->
                    DEPENDENCIES.sohoService.updateUserProfileWithPhoto(imageRequestBody)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { userResult ->
                            DEPENDENCIES.prefs.user = Converter.toUser(userResult)
                            DEPENDENCIES.prefs.authToken = userResult.authenticationToken ?: ""
                            view.hideLoading()
                        },
                        {
                            view.hideLoading()
                            view.showError(it)
                        }))
    }


    override fun onChangePasswordClick() {
        navigator.openEditPasswordProfileScreen(RequestCode.NOT_USED)
    }

    private fun selectPhotoFromGallery() {
        cleanCameraDisposable()
        permissionManager.requestStoragePermission(RequestCode.EDIT_ACCOUNT_PRESENTER_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { permissionEvent ->
                    if (permissionEvent.isPermissionGranted) {
                        rxGallery.toObservable()
                    } else
                        Observable.empty<MutableList<Uri>>()
                }.subscribe {
            avatarUri = it.first()  //uri can be still null
            view.showAvatar(it.filter { it != null }.first())
        }

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
                }.subscribe {
            avatarUri = it
            view.showAvatar(it)
        }

    }

    override fun showDatePickerDialog(fragmentManager: FragmentManager, calendar: Calendar) {

        val datePicker = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->

                    val calendarPicked = Calendar.getInstance()
                    calendarPicked.set(Calendar.YEAR, year)
                    calendarPicked.set(Calendar.MONTH, monthOfYear)
                    calendarPicked.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    view.showPickedDate(calendarPicked)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

        )
        datePicker.maxDate = Calendar.getInstance()
        datePicker.showYearPickerFirst(true)
        datePicker.show(fragmentManager, DATE_PICKER_TAG)
    }

    override fun cleanCameraDisposable() {
        cameraDisposable?.dispose()
        galleryDisposable?.dispose()
    }
}