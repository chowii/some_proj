package com.soho.sohoapp.feature.home.editproperty.verification

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
import com.soho.sohoapp.navigator.NavigatorImpl

class VerificationActivity : AbsActivity(), VerificationContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.photo_id_status)
    lateinit var photoIdStatus: TextView

    @BindView(R.id.ownership_proof_status)
    lateinit var ownershipProofStatus: TextView

    private lateinit var presentable: VerificationContract.ViewPresentable
    private lateinit var presenter: VerificationPresenter

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
        presenter = VerificationPresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun setPresentable(presentable: VerificationContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showToastMessage(@StringRes resId: Int) {
        showToast(resId)
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

    @OnClick(R.id.photo_id)
    internal fun onPhotoIdClicked() {
        presentable.onPhotoIdClicked()
    }

    @OnClick(R.id.ownership_proof)
    internal fun onOwnershipProofClicked() {
        presentable.onOwnershipProofClicked()
    }
}