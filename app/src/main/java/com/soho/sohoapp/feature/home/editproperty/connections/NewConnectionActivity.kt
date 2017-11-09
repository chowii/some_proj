package com.soho.sohoapp.feature.home.editproperty.connections

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.common.OptionModalBottomSheet
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode

class NewConnectionActivity : AbsActivity(), NewConnectionContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.role)
    lateinit var txtRole: TextView

    @BindView(R.id.first_name)
    lateinit var txtFirstName: EditText

    @BindView(R.id.email)
    lateinit var txtEmail: EditText

    private val content: ViewGroup by lazy { findViewById<ViewGroup>(android.R.id.content) }
    private lateinit var presentable: NewConnectionContract.ViewPresentable
    private lateinit var presenter: NewConnectionPresenter
    private var loadingView: View? = null

    companion object {
        private const val KEY_PROPERTY = "KEY_PROPERTY"
        private const val BOTTOM_SHEET_TAG: String = "BOTTOM_SHEET_TAG"

        fun createIntent(context: Context, property: Property): Intent =
                Intent(context, NewConnectionActivity::class.java)
                        .putExtra(KEY_PROPERTY, property)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_connection)
        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener { presentable.onBackClicked() }

        presenter = NewConnectionPresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.NEW_CONNECTION_CONFIRM) {
            data?.getParcelableExtra<ConnectionRequest>(NavigatorImpl.KEY_CONNECTION_REQUEST)?.let {
                presentable.onNewRequestCreated(it)
            }
        }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: NewConnectionContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showToastMessage(message: Int) {
        showToast(message)
    }

    override fun getFirstName() = txtFirstName.text.toString()

    override fun getEmail() = txtEmail.text.toString()

    override fun getPropertyFromExtras() = intent.extras?.getParcelable<Property>(KEY_PROPERTY)

    override fun showLoadingView() {
        loadingView = layoutInflater.inflate(R.layout.view_loading, content, false)
        content.addView(loadingView)
    }

    override fun hideLoadingView() {
        loadingView?.let {
            content.removeView(it)
            loadingView = null
        }
    }

    override fun showRolesBottomSheet(roles: List<String>) {
        OptionModalBottomSheet(roles, getString(R.string.new_connection_role), { presentable.onRoleSelected(it) })
                .show(supportFragmentManager, BOTTOM_SHEET_TAG)
    }

    override fun showConnectionRole(role: String) {
        txtRole.text = role
    }

    @OnClick(R.id.roleLayout)
    fun onConnectionRoleClicked() {
        presentable.onConnectionRoleClicked()
    }

    @OnClick(R.id.send_invite)
    fun onSendInviteClicked() {
        presentable.onSendInviteClicked()
    }
}