package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import android.app.FragmentManager
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.extensions.toStringWithDisplayFormat
import com.soho.sohoapp.extensions.toStringWithTimeFormat
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.utils.Converter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class NewInspectionTimePresenter(private val view: NewInspectionTimeContract.ViewInteractable,
                                 private val navigator: NavigatorInterface,
                                 private val fragmentManager: FragmentManager) : AbsPresenter, NewInspectionTimeContract.ViewPresentable {

    private val TAG_DATE_PICKER_DIALOG = "TAG_DATE_PICKER_DIALOG"
    private val TAG_TIME_PICKER_DIALOG = "TAG_TIME_PICKER_DIALOG"
    private val MIN_INSPECTION_DURATION = TimeUnit.MINUTES.toMillis(10)
    private val compositeDisposable = CompositeDisposable()
    private lateinit var inspectionTime: InspectionTime
    private var dateCalendar: Calendar? = null
    private var startTimeCalendar: Calendar? = null
    private var endTimeCalendar: Calendar? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        val inspectionTimeFromExtras = view.getInspectionTimeFromExtras()
        inspectionTimeFromExtras?.let {
            view.showDeleteAction()
            view.disable()
            view.showInspectionDate(it.startTime.toStringWithDisplayFormat())
            view.showInspectionStartTime(it.startTime.toStringWithTimeFormat())
            view.showInspectionEndTime(it.endTime.toStringWithTimeFormat())
        }
        inspectionTime = inspectionTimeFromExtras ?: InspectionTime()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onDateClicked() {
        val calendar = dateCalendar ?: Calendar.getInstance()
        val onDatePickedListener = DatePickerDialog.OnDateSetListener(
                { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.YEAR, year)
                    view.showInspectionDate(calendar.timeInMillis.toStringWithDisplayFormat())
                    dateCalendar = calendar
                })
        val datePicker = DatePickerDialog.newInstance(onDatePickedListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.minDate = Calendar.getInstance()
        datePicker.show(fragmentManager, TAG_DATE_PICKER_DIALOG)
    }

    override fun onStartTimeClicked() {
        val calendar = startTimeCalendar ?: Calendar.getInstance()
        val onStartTimePickedListener = TimePickerDialog.OnTimeSetListener(
                { _, hourOfDay, minute, second ->
                    setTime(calendar, hourOfDay, minute, second)
                    view.showInspectionStartTime(calendar.timeInMillis.toStringWithTimeFormat())
                    startTimeCalendar = calendar
                })
        showTimePicker(onStartTimePickedListener, calendar)
    }

    override fun onEndTimeClicked() {
        val calendar = endTimeCalendar ?: Calendar.getInstance()
        val onEndTimePickedListener = TimePickerDialog.OnTimeSetListener(
                { _, hourOfDay, minute, second ->
                    setTime(calendar, hourOfDay, minute, second)
                    view.showInspectionEndTime(calendar.timeInMillis.toStringWithTimeFormat())
                    endTimeCalendar = calendar
                })
        showTimePicker(onEndTimePickedListener, calendar)
    }

    override fun onSaveClicked() {
        val date = dateCalendar
        val startTime = startTimeCalendar
        val endTime = endTimeCalendar
        if (date == null) {
            view.showToastMessage(R.string.inspection_time_not_valid_date)
        } else if (startTime == null) {
            view.showToastMessage(R.string.inspection_time_not_valid_start_time)
        } else if (endTime == null) {
            view.showToastMessage(R.string.inspection_time_not_valid_end_time)
        } else {
            startTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH))
            startTime.set(Calendar.MONTH, date.get(Calendar.MONTH))
            startTime.set(Calendar.YEAR, date.get(Calendar.YEAR))

            endTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH))
            endTime.set(Calendar.MONTH, date.get(Calendar.MONTH))
            endTime.set(Calendar.YEAR, date.get(Calendar.YEAR))

            val isInThePast = startTime.timeInMillis - System.currentTimeMillis() <= 0
            if (isInThePast) {
                view.showToastMessage(R.string.inspection_time_date_in_past)
            } else if (endTime.timeInMillis - startTime.timeInMillis <= 0) {
                view.showToastMessage(R.string.inspection_time_not_valid_range)
            } else if (endTime.timeInMillis - startTime.timeInMillis < MIN_INSPECTION_DURATION) {
                view.showToastMessage(R.string.inspection_time_too_short)
            } else {
                inspectionTime.startTime = startTime.timeInMillis
                inspectionTime.endTime = endTime.timeInMillis

                view.getPropertyIdFromExtras()?.let { sendInspectionTimeToServer(it) }
            }
        }
    }

    override fun onDeleteTimeClicked() {
        view.showConfirmationDialog()
    }

    override fun onConfirmDeletionClicked() {
        view.getPropertyIdFromExtras()?.let { deleteInspectionTimeOnServer(it) }
    }

    private fun sendInspectionTimeToServer(propertyId: Int) {
        view.showLoadingDialog()
        val map = Converter.toMap(inspectionTime)
        compositeDisposable.add(
                DEPENDENCIES.sohoService.createInspectionTime(propertyId, map)
                        .map { result -> Converter.toInspectionTime(result) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { inspectionTime ->
                                    view.hideLoadingDialog()
                                    navigator.exitWithResultCodeOk(inspectionTime, true)
                                },
                                {
                                    view.hideLoadingDialog()
                                    view.showError(it)
                                }))
    }

    private fun deleteInspectionTimeOnServer(propertyId: Int) {
        view.showLoadingDialog()
        compositeDisposable.add(
                DEPENDENCIES.sohoService.deleteInspectionTime(propertyId, inspectionTime.id)
                        .map { result -> Converter.toInspectionTime(result) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { inspectionTime ->
                                    view.hideLoadingDialog()
                                    navigator.exitWithResultCodeOk(inspectionTime, false)
                                },
                                {
                                    view.hideLoadingDialog()
                                    view.showError(it)
                                }))
    }

    private fun setTime(calendar: Calendar?, hourOfDay: Int, minute: Int, second: Int) {
        calendar?.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar?.set(Calendar.MINUTE, minute)
        calendar?.set(Calendar.SECOND, second)
    }

    private fun showTimePicker(listener: TimePickerDialog.OnTimeSetListener, calendar: Calendar) {
        val timePicker = TimePickerDialog.newInstance(listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
        timePicker.show(fragmentManager, TAG_TIME_PICKER_DIALOG)
    }
}