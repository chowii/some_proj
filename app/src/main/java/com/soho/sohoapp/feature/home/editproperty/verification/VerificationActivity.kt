package com.soho.sohoapp.feature.home.editproperty.verification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.Verification
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode.*
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.FileHelper

class VerificationActivity : AbsActivity(), VerificationContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.photo_id_status)
    lateinit var photoIdStatus: TextView

    @BindView(R.id.ownership_proof_status)
    lateinit var ownershipProofStatus: TextView

    private lateinit var presentable: VerificationContract.ViewPresentable
    private lateinit var presenter: VerificationPresenter

    private var galleryPicker: GalleryPicker? = null
    private var cameraPicker: CameraPicker? = null
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private val KEY_PROPERTY = "KEY_PROPERTY"

        fun createIntent(context: Context, property: Property): Intent {
            val intent = Intent(context, VerificationActivity::class.java)
            intent.putExtra(KEY_PROPERTY, property)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        presenter = VerificationPresenter(this,
                NavigatorImpl.newInstance(this),
                FileHelper.newInstance(this),
                PermissionManagerImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        galleryPicker?.onActivityResult(requestCode, resultCode, intent)
        cameraPicker?.onActivityResult(requestCode, resultCode)
        if(requestCode == PROOF_OF_OWNERSHIP_VERIFICATION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            intent?.getParcelableExtra<Verification>(NavigatorImpl.KEY_VERIFICATION_UPDATED)?.let { verification ->
                presentable.verificationUpdated(verification)
            } ?: finish() //We are unable to update the dataset, so we exit to avoid showing incorrect data
        }
    }

    override fun setPresentable(presentable: VerificationContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showPhotoVerificationStatus(@StringRes status: Int) {
        photoIdStatus.setText(status)
    }

    override fun showOwnershipVerificationStatus(@StringRes status: Int) {
        ownershipProofStatus.setText(status)
    }

    override fun showPhotoVerificationColor(@ColorRes color: Int) {
        photoIdStatus.setTextColor(resources.getColor(color))
    }

    override fun showOwnershipVerificationColor(@ColorRes color: Int) {
        ownershipProofStatus.setTextColor(resources.getColor(color))
    }

    override fun getPropertyFromExtras() = intent.extras?.getParcelable<Property>(KEY_PROPERTY)

    override fun showAddPhotoDialog() {
        val addPhotoDialog = AddPhotoDialog(this)
        addPhotoDialog.show(object : AddPhotoDialog.OnItemClickedListener {

            override fun onTakeNewPhotoClicked() {
                presenter.onTakeNewPhotoClicked()
            }

            override fun onChooseFromGalleryClicked() {
                presenter.onChooseFromGalleryClicked()
            }
        })
    }

    override fun capturePhoto() {
        cameraPicker = CameraPicker(this)
        cameraPicker?.takePhoto({ path -> presenter.onPhotoReady(path) })
    }

    override fun pickImageFromGallery() {
        galleryPicker = GalleryPicker(this)
        galleryPicker?.choosePhoto({ uri -> presenter.onPhotoPicked(uri) })
    }

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun setVerificationsUpdatedResult(verifications: List<Verification>) {
        setResult(Activity.RESULT_OK, Intent().apply {
            intent.putExtra(NavigatorImpl.KEY_VERIFICATIONS_UPDATED, verifications.toTypedArray())
        })
    }

    @OnClick(R.id.photo_id)
    internal fun onPhotoIdClicked() {
        presentable.onPhotoIdClicked()
    }

    @OnClick(R.id.ownership_proof)
    internal fun onOwnershipProofClicked() {
        presentable.onOwnershipProofClicked()
    }
}