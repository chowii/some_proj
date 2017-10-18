package com.soho.sohoapp.feature.home.editproperty.archive

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.navigator.NavigatorImpl

class ArchiveConfirmationActivity : AbsActivity(), ArchiveConfirmationContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private lateinit var presentable: ArchiveConfirmationContract.ViewPresentable
    private lateinit var presenter: ArchiveConfirmationPresenter

    companion object {
        private val KEY_PROPERTY_ADDRESS = "KEY_PROPERTY_ADDRESS"

        fun createIntent(context: Context, address: String): Intent {
            val intent = Intent(context, ArchiveConfirmationActivity::class.java)
            intent.putExtra(KEY_PROPERTY_ADDRESS, address)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_confirmation)
        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }
        presenter = ArchiveConfirmationPresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: ArchiveConfirmationContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getPropertyAddressFromExtras() = intent.extras?.getString(KEY_PROPERTY_ADDRESS)

    override fun showPropertyAddress(address: String) {
        ButterKnife.findById<TextView>(this, R.id.address).text = address
    }

    @OnClick(R.id.confirm)
    internal fun onConfirmClicked() {
        presentable.onConfirmClicked()
    }
}