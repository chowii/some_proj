package com.soho.sohoapp.feature.gallery

import android.content.Context
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Image
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.logger.Logger
import java.io.File

/**
 * Created by mariolopez on 16/10/17.
 */
class GalleryImageViewPager(val context: Context) : PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val imageLoader: ImageLoader = DEPENDENCIES.imageLoader
    private val logger: Logger = DEPENDENCIES.logger
    var dataSet: List<Image> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = inflater.inflate(R.layout.item_gallery, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.gallery_photo_view)
        val image = dataSet[position]

        val params = ImageLoader.Params.create()
                .view(imageView)
                .placeHolder(image.holder)

        when {
            image.imageUrl != null -> imageLoader.load(params.url(image.imageUrl!!))
            image.filePath != null -> imageLoader.load(params.file(Uri.fromFile(File(image.filePath))))
            image.uri != null -> imageLoader.load(params.file(image.uri!!))
            else -> imageView.setImageResource(image.drawableId)
        }
        container.addView(itemView)
        return itemView
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}