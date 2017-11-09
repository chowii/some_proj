package com.soho.sohoapp.feature.home.editproperty.connections

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int,
                              val listener: RecyclerItemTouchHelperListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(getForegroundView(viewHolder))
        }
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView,
                                 viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                 actionState: Int, isCurrentlyActive: Boolean) {
        getForegroundView(viewHolder)?.let {
            getDefaultUIUtil().onDrawOver(c, recyclerView, it, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getForegroundView(viewHolder)?.let { getDefaultUIUtil().clearView(it) }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                             actionState: Int, isCurrentlyActive: Boolean) {
        getForegroundView(viewHolder)?.let {
            getDefaultUIUtil().onDraw(c, recyclerView, it, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        when (viewHolder) {
            is ConnectionsAdapter.UserHolder -> if (!viewHolder.canBeRemoved) return 0
            is ConnectionsAdapter.RequestHolder -> if (!viewHolder.canBeRemoved) return 0
        }
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }

    private fun getForegroundView(viewHolder: RecyclerView.ViewHolder) = when (viewHolder) {
        is ConnectionsAdapter.UserHolder -> viewHolder.viewForeground
        is ConnectionsAdapter.RequestHolder -> viewHolder.viewForeground
        else -> null
    }
}