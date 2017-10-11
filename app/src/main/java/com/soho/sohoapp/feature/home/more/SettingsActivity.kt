package com.soho.sohoapp.feature.home.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker
import com.soho.sohoapp.feature.home.more.adapter.SettingsAdapter
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.presenter.SettingsPresenter
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.FileHelper

/**
 * Created by chowii on 10/09/17.
 */
class SettingsActivity : AbsActivity(), SettingsContract.ViewInteractable {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    lateinit var presenter: SettingsPresenter

    private lateinit var adapter: SettingsAdapter
    private var galleryPicker: GalleryPicker? = null
    private var cameraPicker: CameraPicker? = null
    private var loadingView: View? = null
    private var loadingDialog: LoadingDialog? = null

    companion object {
        @JvmStatic
        fun createIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener { presenter.onBackClicked() }
        configureAdapter()

        presenter = SettingsPresenter(this,
                PermissionManagerImpl.newInstance(this),
                NavigatorImpl.newInstance(this),
                FileHelper.newInstance(this))
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting(false)
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun updateAdapterDataset(dataset: List<BaseModel>) {
        adapter.updateDataset(dataset)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        galleryPicker?.onActivityResult(requestCode, resultCode, intent)
        cameraPicker?.onActivityResult(requestCode, resultCode)
    }

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

    override fun showLoadingView() {
        val content = findViewById<ViewGroup>(android.R.id.content)
        loadingView = layoutInflater.inflate(R.layout.view_loading, content, false)
        content.addView(loadingView)
    }

    override fun hideLoadingView() {
        loadingView?.let {
            findViewById<ViewGroup>(android.R.id.content).removeView(it)
            loadingView = null
        }
    }

    private fun configureAdapter() {
        adapter = SettingsAdapter(listOf(), object : SettingsAdapter.OnSettingsItemClickListener {
            override fun onSettingsItemClicked(type: String?) {
                presenter.onSettingsItemClicked(type)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}