package com.soho.sohoapp.feature.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.Image
import com.soho.sohoapp.feature.BaseViewInteractable
import kotlinx.android.synthetic.main.activity_simple_frag_container.*

/**
 * Created by mariolopez on 16/10/17.
 */
class GalleryViewActivity : AbsActivity(), GalleryViewActivityContract.ViewInteractable {

    private lateinit var presentable: GalleryViewActivityContract.ViewPresentable

    companion object {
        val KEY_IMAGES_EXTRA = "images"
        val KEY_CURRENT_POS = "current pos"
        @JvmStatic
        fun createIntent(context: Context, images: MutableList<Image>, currentItemPos: Int): Intent = Intent(
                context, GalleryViewActivity::class.java).apply {
            putExtra(KEY_IMAGES_EXTRA, images.toTypedArray())
            putExtra(KEY_CURRENT_POS, currentItemPos)
        }
    }

    val presenter by lazy { GalleryViewActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_frag_container)

        toolbar.title = getString(R.string.gallery_title)
        toolbar.setNavigationOnClickListener({ finish() })

        val images = intent.getParcelableArrayExtra(KEY_IMAGES_EXTRA).map { it as Image }
        val currentPos = intent.getIntExtra(KEY_CURRENT_POS, 0)
        presenter.startPresenting(savedInstanceState != null)
        presenter.showGalleryFragment(images.toTypedArray(), currentPos)
    }

    override fun showError(throwable: Throwable) {
        handleError()
    }

    override fun setPresentable(presentable: GalleryViewActivityContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showGallery(images: Array<out Image>, currentPos: Int) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, GalleryViewFragment.newInstance(images, currentPos))
                .commit()
    }
}

class GalleryViewActivityPresenter(private val view: GalleryViewActivityContract.ViewInteractable)
    : AbsPresenter, GalleryViewActivityContract.ViewPresentable {


    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
    }

    override fun showGalleryFragment(images: Array<out Image>, currentPos: Int) {
        view.showGallery(images, currentPos)
    }

    override fun stopPresenting() {}
}

interface GalleryViewActivityContract {

    interface ViewPresentable {
        fun showGalleryFragment(images: Array<out Image>, currentPos: Int)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showGallery(images: Array<out Image>, currentPos: Int)
    }
}