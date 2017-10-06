package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.landing.BaseFragment

class OwnershipDetailsFragment : BaseFragment(), OwnershipDetailsContract.ViewInteractable {
    private lateinit var presentable: OwnershipDetailsContract.ViewPresentable
    private lateinit var presenter: OwnershipDetailsPresenter

    companion object {
        fun newInstance() = OwnershipDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ownership_verification_details, container, false)
        ButterKnife.bind(this, view)

        presenter = OwnershipDetailsPresenter(this)
        presenter.startPresenting(savedInstanceState != null)
        return view
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: OwnershipDetailsContract.ViewPresentable) {
        this.presentable = presentable
    }

    @OnClick(R.id.attach_files)
    internal fun onAttachFilesClicked() {
        presentable.onAttachFilesClicked()
    }
}