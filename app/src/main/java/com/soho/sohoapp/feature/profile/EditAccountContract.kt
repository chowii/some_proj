package com.soho.sohoapp.feature.profile

import android.app.FragmentManager
import android.net.Uri
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.utils.QueryHashMap
import java.util.*

interface EditAccountContract {

    interface ViewPresentable {
        fun onEditPhotoClick()

        fun cleanCameraDisposable()

        fun onChangePasswordClick()

        fun updaterUserProfile(values: QueryHashMap)

        fun showDatePickerDialog(fragmentManager: FragmentManager, calendar: Calendar)

    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)

        fun fillUserInfo(user: User)

        fun showLoading()

        fun hideLoading()

        fun showAvatar(picUri: Uri)

        fun showPickedDate(calendarPicked: Calendar)
    }
}
