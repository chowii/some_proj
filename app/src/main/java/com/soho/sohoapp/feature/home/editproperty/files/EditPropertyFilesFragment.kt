package com.soho.sohoapp.feature.home.editproperty.files

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.feature.home.editproperty.verification.ownership.OwnershipFilesAdapter
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode

class EditPropertyFilesFragment : BaseFragment(), EditPropertyFilesContact.ViewInteractable {

    @BindView(R.id.files)
    lateinit var filesRV: RecyclerView

    private lateinit var presentable: EditPropertyFilesContact.ViewPresentable
    private lateinit var presenter: EditPropertyFilesPresenter
    private lateinit var filesAdapter: OwnershipFilesAdapter

    companion object {
        private const val KEY_PROPERTY = "KEY_PROPERTY"
        private const val ITEMS_IN_LINE = 3

        fun newInstance(property: Property) = EditPropertyFilesFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_PROPERTY, property)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_property_files, container, false)
        ButterKnife.bind(this, view)

        initView()
        presenter = EditPropertyFilesPresenter(this, NavigatorImpl.newInstance(this))
        presenter.startPresenting(savedInstanceState != null)
        return view
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCode.EDIT_PROPERTY_PREVIEW_FILE -> data?.extras?.let {
                    presentable.onPropertyFileUpdated(it.getParcelable(NavigatorImpl.KEY_PROPERTY_FILE),
                            it.getBoolean(NavigatorImpl.KEY_PROPERTY_FILE_DELETED))
                }
                RequestCode.EDIT_PROPERTY_NEW_FILE -> data?.extras?.let {
                    presentable.onPropertyFileCreated(it.getParcelable(NavigatorImpl.KEY_PROPERTY_FILE))
                }
            }
        }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: EditPropertyFilesContact.ViewPresentable) {
        this.presentable = presentable
    }

    override fun getPropertyFromExtras(): Property? = arguments?.getParcelable(KEY_PROPERTY)

    override fun setFileList(fileList: List<Displayable>) {
        filesAdapter.setData(fileList)
    }

    private fun initView() {
        filesAdapter = OwnershipFilesAdapter()
        filesAdapter.setOnItemClickListener(object : OwnershipFilesAdapter.OnItemClickListener {

            override fun onFileClicked(file: PropertyFile) {
                presentable.onFileClicked(file)
            }

            override fun onAddFileClicked() {
                presentable.onAddFileClicked()
            }

            override fun onDeleteIconClicked(attachment: Attachment) {}
        })

        with(filesRV) {
            setHasFixedSize(true)
            adapter = filesAdapter
            layoutManager = GridLayoutManager(this.context, EditPropertyFilesFragment.ITEMS_IN_LINE)
        }
    }
}