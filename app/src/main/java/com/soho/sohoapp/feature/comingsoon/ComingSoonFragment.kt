package com.soho.sohoapp.feature.comingsoon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R

/**
 * Created by mariolopez on 16/10/17.
 */
class ComingSoonFragment : Fragment() {

    companion object {
        @JvmField
        val TAG: String = "ComingSoon"

        @JvmStatic
        fun newInstance() = ComingSoonFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater
            .inflate(R.layout.fragment_coming_soon, container, false)
}