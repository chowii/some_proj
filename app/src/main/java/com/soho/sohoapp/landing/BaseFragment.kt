package com.soho.sohoapp.landing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.soho.sohoapp.feature.network.ThrowableHandler
import com.soho.sohoapp.helper.SohoSnackbar

/**
 * Created by chowii on 25/7/17.
 */
open class BaseFragment : Fragment() {
    var transactionListener: FragmentTransactionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = super.onCreateView(inflater, container, savedInstanceState)


    fun handleError(t: Throwable) = handleError(t, true, null)

    fun handleError(t: Throwable, showInternetErrors: Boolean, coordinatorLayout: FrameLayout?) {
        ThrowableHandler.showError(t, showInternetErrors, coordinatorLayout, view, activity)
    }

    fun showSnackBar(message: String, view:View?) {
        SohoSnackbar().showSnackbar(view, message)
    }

    open fun setFragmentTransactionListener(listener: FragmentTransactionListener?) {
        transactionListener = listener
    }

    interface FragmentTransactionListener {
        fun onTransaction()
    }
}