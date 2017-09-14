package com.soho.sohoapp.feature.home.more


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.SharedUser
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.HomeActivity
import com.soho.sohoapp.feature.home.more.adapter.MoreAdapter
import com.soho.sohoapp.feature.home.more.contract.MoreContract
import com.soho.sohoapp.feature.home.more.presenter.MorePresenter
import com.soho.sohoapp.feature.home.more.viewholder.MoreViewHolder
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl

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

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    lateinit var presenter: MorePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        ButterKnife.bind(this, view)
        presenter = MorePresenter(this, context)
        presenter.startPresenting()
        return view
    }

    override fun configureToolbar() {
        (activity as HomeActivity).setSupportActionBar(toolbar)
        toolbar.title = ""
    }

    override fun configureAdapter(model: List<BaseModel>) {
        recyclerView.adapter = MoreAdapter(model, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onSettingsItemClicked(button: String) {
        when(button){
            getString(R.string.setting_item_text) -> NavigatorImpl.newInstance(this).openSettingActivity()
            getString(R.string.log_out_item_text) -> logoutUser()
            else -> Dependencies.DEPENDENCIES.logger.d(button)
        }
    }

    private fun logoutUser() {
        Dependencies.DEPENDENCIES.preferences.authToken = ""
        //Double check and keep one
        SharedUser.getInstance().user = null
        Dependencies.DEPENDENCIES.preferences.mUser = null
        NavigatorImpl.newInstance(this).openHomeActivity(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

}
