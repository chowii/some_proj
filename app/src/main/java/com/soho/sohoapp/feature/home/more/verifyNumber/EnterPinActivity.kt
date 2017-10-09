package com.soho.sohoapp.feature.home.more.verifyNumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.feature.BaseViewInteractable
import kotlinx.android.synthetic.main.activity_simple_frag_container.*

/**
 * Created by mariolopez on 9/10/17.
 */
class EnterPinActivity : AbsActivity(), EnterPinActivityContract.ViewInteractable {

    companion object {
         val EXTRA_PHONE_NUMBER = "phone number"
        @JvmStatic
        fun createIntent(context: Context, phoneNumber: String) = Intent(context, EnterPinActivity::class.java)
                .apply { putExtra(EXTRA_PHONE_NUMBER, phoneNumber) }
    }
    private lateinit var presentable: EnterPinActivityContract.ViewPresentable
    val presenter = EnterPinActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_frag_container)
        toolbar.setTitle(R.string.enter_pin_enter_pin)
        presenter.startPresenting()
    }

    override fun setPresentable(presentable: EnterPinActivityContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showPinFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EnterPinFragment.newInstance(intent.getStringExtra(EXTRA_PHONE_NUMBER)))
                .commit()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }
}

interface EnterPinActivityContract {

    interface ViewPresentable

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showPinFragment()
    }
}

class EnterPinActivityPresenter(private val view: EnterPinActivityContract.ViewInteractable)
    : AbsPresenter, EnterPinActivityContract.ViewPresentable {


    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        view.showPinFragment()
    }

    override fun stopPresenting() {}
}