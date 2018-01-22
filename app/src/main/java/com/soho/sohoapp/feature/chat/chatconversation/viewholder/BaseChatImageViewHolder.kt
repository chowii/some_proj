package com.soho.sohoapp.feature.chat.chatconversation.viewholder

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 22/1/18.
 */
abstract class BaseChatImageViewHolder<T : BaseModel>(itemView: View) : BaseViewHolder<T>(itemView) {

    private var widthDp: Float = itemView.context.resources.configuration.screenWidthDp.toFloat()
    private var widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, widthDp, Resources.getSystem().displayMetrics)

    fun resizeImage(imageDimension: Pair<Float, Float>): Pair<Int, Int> {
        itemView.context.let {
            val imageWidth = imageDimension.first
            val aspectRatio = getAspectRatio(imageWidth, imageDimension.second)
            val dimen = getCurrentDimenRatio(imageWidth, widthPx)
            val minimumScreenToImageRatio = 0.60f
            val imageResizeFactor = minimumScreenToImageRatio.plus(1)

            val newHeight: Float?
            return if (dimen > minimumScreenToImageRatio) {
                val newWidth = widthPx.times(imageResizeFactor)
                newHeight = newWidth.div(aspectRatio)
                Pair(newWidth.toInt(), newHeight.toInt())
            } else {
                Pair(imageDimension.first.toInt(), imageDimension.second.toInt())
            }
        }
    }

    private fun getCurrentDimenRatio(imageWidth: Float, widthPx: Float) =
            imageWidth / widthPx

    private fun getAspectRatio(imageWidth: Float, imageHeight: Float) =
            imageWidth / imageHeight

}