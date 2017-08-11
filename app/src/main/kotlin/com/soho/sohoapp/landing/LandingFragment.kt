package com.soho.sohoapp.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import kotlinx.android.synthetic.main.fragment_landing.*

/**
 * Created by chowii on 25/7/17.
 */
class LandingFragment: BaseFragment() {

    companion object {

        @JvmStatic
        fun newInstance(): LandingFragment{
            val fragment: LandingFragment = LandingFragment()
            val bundle: Bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }


    @BindView(R.id.signup) lateinit var sugin: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_landing, container, false)
        ButterKnife.bind(this, view)
        return view
    }

}
