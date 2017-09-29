package com.soho.sohoapp.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Jovan on 29/9/17.
 */
fun RecyclerView.addLeftSwipeToDelete(@ColorRes backgroundColorRes:Int, @DrawableRes iconResId:Int, onSwipe: (Int) -> Unit) {
    val swipeToDismissTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean =
                false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipe(viewHolder.adapterPosition)
        }

        override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            viewHolder?.itemView?.let { itemView ->
                val p = Paint()
                val icon: Bitmap

                if (dX < 0) {
                    icon = BitmapFactory.decodeResource(itemView.context.resources, iconResId)

                    // Set your color for negative displacement
                    p.color = ContextCompat.getColor(itemView.context, backgroundColorRes)

                    // Draw Rect with varying left side, equal to the item's right side
                    // plus negative displacement dX
                    c?.drawRect(itemView.right + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), p)

                    // Set the image icon for Left swipe
                    c?.drawBitmap(icon,
                            itemView.right.toFloat() - 16.dpToPx(resources) - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height) / 2,
                            p)
                }
            }

            // Fades the cell out as it is swipe across
            val alpha = 1.0f - Math.abs(dX) / (viewHolder?.itemView?.width ?: 1)
            viewHolder?.itemView?.alpha = alpha
            viewHolder?.itemView?.translationX = dX
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    })
    swipeToDismissTouchHelper.attachToRecyclerView(this)
}