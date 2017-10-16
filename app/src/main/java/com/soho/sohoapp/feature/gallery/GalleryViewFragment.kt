package com.soho.sohoapp.feature.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Image
import com.soho.sohoapp.landing.BaseFragment
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * Created by mariolopez on 16/10/17.
 */
class GalleryViewFragment : BaseFragment() {

    companion object {
        fun newInstance(images: Array<out Image>, currentPos: Int) = GalleryViewFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelableArray(GalleryViewActivity.KEY_IMAGES_EXTRA, images)
                        putInt(GalleryViewActivity.KEY_CURRENT_POS, currentPos)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        gallery_pager.adapter = GalleryImageViewPager(gallery_pager.context)
                .apply {
                    val images = arguments.getParcelableArray(GalleryViewActivity.KEY_IMAGES_EXTRA)
                            .map { it as Image }
                    dataSet = images

                }
        gallery_pager.currentItem = arguments.getInt(GalleryViewActivity.KEY_CURRENT_POS, 0)
    }
}