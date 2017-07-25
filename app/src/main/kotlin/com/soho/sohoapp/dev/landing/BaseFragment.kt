package com.soho.sohoapp.dev.landing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.soho.sohoapp.dev.helper.NavHelper
import com.soho.sohoapp.dev.helper.SohoSnackbar
import com.soho.sohoapp.dev.network.HttpStatusException
import com.soho.sohoapp.dev.network.HttpErrorType.*

/**
 * Created by chowii on 25/7/17.
 */
open class BaseFragment: Fragment() {
    var transactionListener: FragmentTransactionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = super.onCreateView(inflater, container, savedInstanceState)


    fun handleError(t: Throwable) = handleError(t, true, null);

    fun handleError(t: Throwable, showInternetErrors: Boolean, coordinatorLayout: FrameLayout?){
        if(t is HttpStatusException)
            when(t.errorType){
                General -> {
                    if(showInternetErrors) coordinatorLayout?.let{

                    }
                    else handleCustomError(t.error)
                }

                PasswordReEnterRequired,
                PasswordResetRequired -> NavHelper.showLandingActivity(activity, t.error)

                ReloginRequired -> {
//                    SharedPrefsHelper.getInstance().removeUserCredentials()
                    NavHelper.showLandingActivity(activity, t.error)
                }
            }
        else if(showInternetErrors && coordinatorLayout != null)
            SohoSnackbar().showSnackbar(view, "Something went wrong. Please try again")

        else handleCustomError("Something went wrong. Please try again")
    }

    fun handleCustomError(error: String){

    }

    open fun setFragmentTransactionListener(listener: FragmentTransactionListener?) {
        transactionListener = listener
    }

    interface FragmentTransactionListener{
        fun onTransaction()
    }
}