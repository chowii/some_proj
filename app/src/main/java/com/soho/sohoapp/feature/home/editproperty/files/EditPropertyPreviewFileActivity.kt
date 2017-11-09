package com.soho.sohoapp.feature.home.editproperty.files

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode

class EditPropertyPreviewFileActivity : AbsActivity(), EditPropertyPreviewFileContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.file)
    lateinit var imgFile: ImageView

    private lateinit var presentable: EditPropertyPreviewFileContract.ViewPresentable
    private lateinit var presenter: EditPropertyPreviewFilePresenter
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private const val KEY_PROPERTY = "KEY_PROPERTY"
        private const val KEY_PROPERTY_FILE = "KEY_PROPERTY_FILE"

        fun createIntent(context: Context, property: Property, propertyFile: PropertyFile): Intent =
                Intent(context, EditPropertyPreviewFileActivity::class.java)
                        .putExtra(KEY_PROPERTY, property)
                        .putExtra(KEY_PROPERTY_FILE, propertyFile)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_property_preview_file)
        ButterKnife.bind(this)

        initToolbar()
        presenter = EditPropertyPreviewFilePresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.EDIT_PROPERTY_PREVIEW_FILE_EDIT_MODE) {
            intent?.getParcelableExtra<PropertyFile>(NavigatorImpl.KEY_PROPERTY_FILE)?.let {
                presentable.onPropertyFileUpdated(it)
            }
        }
    }

    override fun setPresentable(presentable: EditPropertyPreviewFileContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showDeleteOption() {
        toolbar.menu.findItem(R.id.action_delete).isVisible = true
    }

    override fun getPropertyFromExtras() = intent.extras?.getParcelable<Property>(KEY_PROPERTY)

    override fun getPropertyFileFromExtras() = intent.extras?.getParcelable<PropertyFile>(KEY_PROPERTY_FILE)

    override fun showFileImage(filePhoto: String?) {
        DEPENDENCIES.imageLoader.load(ImageLoader.Params.create()
                .url(filePhoto!!)
                .view(imgFile))
    }

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun showConfirmationDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> presentable.onConfirmDeletionClicked()
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }
        AlertDialog.Builder(this).setMessage(R.string.property_file_confirm_deletion)
                .setPositiveButton(R.string.property_file_confirm_deletion_yes, dialogClickListener)
                .setNegativeButton(R.string.property_file_confirm_deletion_no, dialogClickListener)
                .show()
    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.file_preview_toolbar)
        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> presentable.onEditClicked()
                R.id.action_delete -> presentable.onDeleteClicked()
            }
            false
        }
    }
}