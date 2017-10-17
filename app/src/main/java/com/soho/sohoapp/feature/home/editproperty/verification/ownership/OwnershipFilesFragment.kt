package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker
import com.soho.sohoapp.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.FileHelper

class OwnershipFilesFragment : BaseFragment(), OwnershipFilesContract.ViewInteractable {

    @BindView(R.id.files)
    lateinit var filesRV: RecyclerView

    private lateinit var presentable: OwnershipFilesContract.ViewPresentable
    private lateinit var presenter: OwnershipFilesPresenter
    private lateinit var filesAdapter: OwnershipFilesAdapter
    private var galleryPicker: GalleryPicker? = null
    private var cameraPicker: CameraPicker? = null
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private val KEY_PROPERTY = "KEY_PROPERTY"
        private val ITEMS_IN_LINE = 3

        fun newInstance(property: Property) = OwnershipFilesFragment().apply {
            arguments = Bundle()
            arguments.putParcelable(KEY_PROPERTY, property)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ownership_verification_files, container, false)
        ButterKnife.bind(this, view)

        initView()
        presenter = OwnershipFilesPresenter(this,
                NavigatorImpl.newInstance(this),
                PermissionManagerImpl.newInstance(this.context),
                FileHelper.newInstance(this.context))
        presenter.startPresenting(savedInstanceState != null)
        return view
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        galleryPicker?.onActivityResult(requestCode, resultCode, data)
        cameraPicker?.onActivityResult(requestCode, resultCode)
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: OwnershipFilesContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun setFileList(fileList: List<Displayable>) {
        filesAdapter.setData(fileList)
    }

    override fun getProperty(): Property = arguments.getParcelable<Property>(KEY_PROPERTY)

    override fun showAddPhotoDialog() {
        val addPhotoDialog = AddPhotoDialog(this.context)
        addPhotoDialog.show(object : AddPhotoDialog.OnItemClickedListener {
            override fun onTakeNewPhotoClicked() {
                presentable.onTakeNewPhotoClicked()
            }

            override fun onChooseFromGalleryClicked() {
                presentable.onChooseFromGalleryClicked()
            }
        })
    }

    override fun capturePhoto() {
        cameraPicker = CameraPicker(this)
        cameraPicker?.takePhoto({ path -> presentable.onPhotoReady(path) })
    }

    override fun pickImageFromGallery() {
        galleryPicker = GalleryPicker(this)
        galleryPicker?.choosePhoto({ uri -> presentable.onPhotoPicked(uri) })
    }

    override fun showLoadingDialog() {
        loadingDialog = LoadingDialog(this.context)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun showValidationMessage(stringId: Int) {
        showSnackBar(getString(stringId), filesRV)
    }

    @OnClick(R.id.submit)
    internal fun onSubmitClicked() {
        presentable.onSubmitClicked()
    }

    private fun initView() {
        filesAdapter = OwnershipFilesAdapter(this.context)
        filesAdapter.setOnItemClickListener(object : OwnershipFilesAdapter.OnItemClickListener {

            override fun onAddFileClicked() {
                presentable.onAddFileClicked()
            }

            override fun onDeleteIconClicked(attachment: Attachment) {
                presentable.onDeleteIconClicked(attachment)
            }
        })

        with(filesRV) {
            setHasFixedSize(true)
            adapter = filesAdapter
            layoutManager = GridLayoutManager(this.context, ITEMS_IN_LINE)
        }
    }
}