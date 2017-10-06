package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.Property

class OwnershipVerificationActivity : AbsActivity() {

    @BindView(R.id.tabs)
    lateinit var tabs: TabLayout

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    companion object {
        private val KEY_PROPERTY = "KEY_PROPERTY"

        fun createIntent(context: Context, property: Property): Intent {
            val intent = Intent(context, OwnershipVerificationActivity::class.java)
            intent.putExtra(KEY_PROPERTY, property)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ownership_verification)
        ButterKnife.bind(this)

        val pageAdapter = OwnershipVerificationTabAdapter(this, getPropertyFromExtras(), supportFragmentManager)
        with(viewPager) {
            offscreenPageLimit = pageAdapter.count
            adapter = pageAdapter
        }
        tabs.setupWithViewPager(viewPager)
    }

    private fun getPropertyFromExtras() = intent.extras.getParcelable<Property>(KEY_PROPERTY)
}