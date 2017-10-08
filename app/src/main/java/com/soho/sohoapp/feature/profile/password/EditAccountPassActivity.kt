package com.soho.sohoapp.feature.profile.password

/**
 * Created by mariolopez on 3/10/17.
 */
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
class EditAccountPassActivity : AbsActivity(), EditAccountPasswordContract.ViewInteractable {

    private lateinit var presentable: EditAccountPasswordContract.ViewPresentable

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, EditAccountPassActivity::class.java)
    }

    val presenter by lazy { EditAccountPassActivityPresenter(this, DEPENDENCIES.prefs.user) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_frag_container)
        ButterKnife.bind(this)
        toolbar.setNavigationOnClickListener({ finish() })
        toolbar.title = getString(R.string.edit_account_pass_title)
        presenter.startPresenting(savedInstanceState != null)
    }

    override fun setPresentable(presentable: EditAccountPasswordContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showPasswordFormFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditAccountPasswordFragment())
                .commit()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }
}

interface EditAccountPasswordContract {

    interface ViewPresentable

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showPasswordFormFragment()
    }
}