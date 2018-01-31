package com.soho.sohoapp.feature.home.more


import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.adapter.MoreAdapter
import com.soho.sohoapp.feature.home.more.contract.MoreContract
import com.soho.sohoapp.feature.home.more.presenter.MorePresenter
import com.soho.sohoapp.feature.home.more.viewholder.MoreViewHolder
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.zendesk.sdk.model.access.AnonymousIdentity
import com.zendesk.sdk.network.impl.ZendeskConfig
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class MoreFragment : BaseFragment(), MoreContract.ViewInteractable, MoreViewHolder.OnMoreItemClickListener {

    companion object {
        @JvmField
        val TAG: String = "MoreFragment"

        fun newInstance(): MoreFragment {
            val fragment = MoreFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var presenter: MorePresenter

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.user_name_tv)
    lateinit var userNameTv: TextView

    @BindView(R.id.user_properties_tv)
    lateinit var userPropertiesTv: TextView

    @BindView(R.id.user_avatar_iv)
    lateinit var userAvatarIv: CircleImageView

    lateinit var loading: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        ButterKnife.bind(this, view)
        loading = LoadingDialog(context)
        presenter = MorePresenter(
                this,
                context,
                (activity as AbsActivity),
                NavigatorImpl.newInstance(this),
                DEPENDENCIES.sohoService,
                DEPENDENCIES.userPrefs,
                DEPENDENCIES.twilioManager
                )
        presenter.startPresenting()
        return view
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun configureAdapter(model: List<BaseModel>) {
        recyclerView.adapter = MoreAdapter(model, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onSettingsItemClicked(button: String) {
        when (button) {
            getString(R.string.settings_help_item_text) -> presenter.getUser(true)
            getString(R.string.settings_item_text) -> NavigatorImpl.newInstance(this).openSettingActivity()
            getString(R.string.settings_log_out_item_text) -> presenter.logout()
            else -> DEPENDENCIES.logger.d(button)
        }
    }

    override fun showSupportActivity(user: User) {
        ZendeskConfig.INSTANCE.setIdentity(AnonymousIdentity.Builder()
                .withNameIdentifier(user.firstName + user.lastName)
                .withEmailIdentifier(user.email)
                .build())
        NavigatorImpl.newInstance(this).openHelpActivity()
    }

    override fun showLoading() {
        loading.show(getString(R.string.more_logging_out_text))
    }

    override fun showError(message: String) {
        loading.dismiss()
        AlertDialog.Builder(context).apply {
            setMessage(message)
            show()
        }
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun showUserProfileInfo(user: User) {

        userNameTv.text = getString(R.string.settings_name_last_name_format).format(
                user.firstName, user.lastName, Locale.getDefault())
        if (context != null && !user.avatar?.smallImageUrl.isNullOrEmpty()) {
            val imageLoader = DEPENDENCIES.imageLoader
            val params = ImageLoader.Params.create()
                    .url(user.avatar?.smallImageUrl!!)
                    .view(userAvatarIv)
                    .placeHolder(R.drawable.ic_account_circle_gray)
            imageLoader.load(params)
        }
    }

    @OnClick(R.id.btn_add_property)
    internal fun onAddPropertyClicked() {
        presenter.addPropertyClicked()
    }
}
