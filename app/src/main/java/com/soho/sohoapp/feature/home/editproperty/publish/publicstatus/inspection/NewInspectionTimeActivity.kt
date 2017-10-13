package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.ButterKnife.findById
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.navigator.NavigatorImpl


class NewInspectionTimeActivity : AbsActivity(), NewInspectionTimeContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.dateDesc)
    lateinit var dateDesc: TextView

    @BindView(R.id.startTimeDesc)
    lateinit var startTimeDesc: TextView

    @BindView(R.id.endTimeDesc)
    lateinit var endTimeDesc: TextView

    private lateinit var presentable: NewInspectionTimeContract.ViewPresentable
    private lateinit var presenter: NewInspectionTimePresenter
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private val KEY_PROPERTY_ID = "KEY_PROPERTY_ID"
        private val KEY_INSPECTION_TIME = "KEY_INSPECTION_TIME"

        fun createIntent(context: Context, inspectionTime: InspectionTime?, propertyId: Int?): Intent {
            val intent = Intent(context, NewInspectionTimeActivity::class.java)
            intent.putExtra(KEY_PROPERTY_ID, propertyId)
            intent.putExtra(KEY_INSPECTION_TIME, inspectionTime)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_inspection_time)
        ButterKnife.bind(this)
        initToolbar()

        presenter = NewInspectionTimePresenter(this, NavigatorImpl.newInstance(this), fragmentManager)
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: NewInspectionTimeContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getInspectionTimeFromExtras() =
            intent.extras?.getParcelable<InspectionTime>(NewInspectionTimeActivity.KEY_INSPECTION_TIME)

    override fun getPropertyIdFromExtras() =
            intent.extras?.getInt(NewInspectionTimeActivity.KEY_PROPERTY_ID)

    override fun showToastMessage(@StringRes stringId: Int) {
        super.showToast(stringId)
    }

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun showInspectionDate(date: String) {
        dateDesc.text = date
    }

    override fun showInspectionStartTime(time: String) {
        startTimeDesc.text = time
    }

    override fun showInspectionEndTime(time: String) {
        endTimeDesc.text = time
    }

    override fun showDeleteAction() {
        toolbar.menu.findItem(R.id.action_delete).isVisible = true
    }

    override fun disable() {
        findById<LinearLayout>(this, R.id.date).isEnabled = false
        findById<LinearLayout>(this, R.id.startTime).isEnabled = false
        findById<LinearLayout>(this, R.id.endTime).isEnabled = false
        findById<Button>(this, R.id.save).visibility = View.GONE
        findById<TextView>(this, R.id.header).visibility = View.GONE
        findById<TextView>(this, R.id.dateText).setTextColor(resources.getColor(R.color.secondaryText))
        findById<TextView>(this, R.id.startTimeText).setTextColor(resources.getColor(R.color.secondaryText))
        findById<TextView>(this, R.id.endTimeText).setTextColor(resources.getColor(R.color.secondaryText))
        dateDesc.setTextColor(resources.getColor(R.color.secondaryText))
        startTimeDesc.setTextColor(resources.getColor(R.color.secondaryText))
        endTimeDesc.setTextColor(resources.getColor(R.color.secondaryText))
    }

    override fun showConfirmationDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> presentable.onConfirmDeletionClicked()
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        AlertDialog.Builder(this).setMessage(R.string.inspection_time_confirm_deletion)
                .setTitle(R.string.inspection_time_confirmation)
                .setPositiveButton(R.string.inspection_time_confirm_deletion_yes, dialogClickListener)
                .setNegativeButton(R.string.inspection_time_confirm_deletion_no, dialogClickListener)
                .show()
    }

    @OnClick(R.id.date)
    internal fun onDateClicked() {
        presentable.onDateClicked()
    }

    @OnClick(R.id.startTime)
    internal fun onStartTimeClicked() {
        presentable.onStartTimeClicked()
    }

    @OnClick(R.id.endTime)
    internal fun onEndTimeClicked() {
        presentable.onEndTimeClicked()
    }

    @OnClick(R.id.save)
    internal fun onSaveClicked() {
        presentable.onSaveClicked()
    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.new_inspection_time_toolbar)
        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        toolbar.setOnMenuItemClickListener { item ->
            if (R.id.action_delete == item.itemId) {
                presentable.onDeleteTimeClicked()
            }
            false
        }
    }
}