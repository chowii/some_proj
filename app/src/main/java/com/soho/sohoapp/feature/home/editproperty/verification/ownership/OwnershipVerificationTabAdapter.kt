package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Property

class OwnershipVerificationTabAdapter(private val context: Context,
                                      private val property: Property,
                                      fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val ITEM_ONE = 0
    private val ITEM_TWO = 1
    private val TABS_COUNT = 2

    override fun getItem(position: Int): Fragment? = when (position) {
        ITEM_ONE -> OwnershipDetailsFragment.Companion.newInstance()
        ITEM_TWO -> OwnershipFilesFragment.Companion.newInstance(property)
        else -> null
    }

    override fun getCount() = TABS_COUNT

    override fun getPageTitle(position: Int) =
            when (position) {
                ITEM_ONE -> context.getString(R.string.verification_ownership_details)
                ITEM_TWO -> context.getString(R.string.verification_ownership_files)
                else -> null
            }
}