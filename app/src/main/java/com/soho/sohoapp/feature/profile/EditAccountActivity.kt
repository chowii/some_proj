package com.soho.sohoapp.feature.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.feature.BaseViewInteractable

/**
 * Created by mariolopez on 27/9/17.
 */
class EditAccountActivity : AbsActivity(), EditAccountActivityContract.ViewInteractable {

    private lateinit var presentable: EditAccountActivityContract.ViewPresentable


    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, EditAccountActivity::class.java)
    }

    val presenter by lazy { EditAccountActivityPresenter(this, DEPENDENCIES.userPrefs.user) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_frag_container)
        ButterKnife.bind(this)
        toolbar.title = getString(R.string.edit_account_title)
        toolbar.setNavigationOnClickListener({ finish() })
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun setPresentable(presentable: EditAccountActivityContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showUserFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditAccountFragment.newInstance(DEPENDENCIES.userPrefs.user))
                .commit()
    }
    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }
}
interface EditAccountActivityContract {

    interface ViewPresentable

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showUserFragment()
    }
}