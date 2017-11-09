package com.soho.sohoapp.feature.home.editproperty.connections

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.navigator.NavigatorImpl

class ConnectionConfirmActivity : AbsActivity(), ConnectionConfirmContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.name)
    lateinit var nameTv: TextView

    @BindView(R.id.role)
    lateinit var roleTv: TextView

    @BindView(R.id.address)
    lateinit var addressTv: TextView

    private lateinit var presentable: ConnectionConfirmContract.ViewPresentable
    private lateinit var presenter: ConnectionConfirmPresenter
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private const val KEY_PROPERTY = "KEY_PROPERTY"
        private const val KEY_USER = "KEY_USER"

        fun createIntent(context: Context, property: Property, user: PropertyUser): Intent =
                Intent(context, ConnectionConfirmActivity::class.java)
                        .putExtra(KEY_PROPERTY, property)
                        .putExtra(KEY_USER, user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection_confirm)
        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }

        presenter = ConnectionConfirmPresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: ConnectionConfirmContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getPropertyFromExtras() = intent.extras?.getParcelable<Property>(KEY_PROPERTY)

    override fun getUserFromExtras() = intent.extras?.getParcelable<PropertyUser>(KEY_USER)

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun showPropertyAddress(address: String?) {
        addressTv.text = address
    }

    override fun showUserName(name: String?) {
        nameTv.text = getString(R.string.connection_confirm_name, name)
    }

    override fun showUserRole(@StringRes role: Int) {
        roleTv.text = getString(R.string.connection_confirm_role, getString(role))
    }

    @OnClick(R.id.confirm)
    fun onConfirmClicked() {
        presentable.onConfirmClicked()
    }
}