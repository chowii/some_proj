package com.soho.sohoapp.feature.home.more.presenter

import android.content.Context
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.contract.MoreContract
import com.soho.sohoapp.feature.home.more.model.MoreItem

/**
 * Created by chowii on 10/09/17.
 */
class MorePresenter (interactable: MoreContract.ViewInteractable, private val context: Context): MoreContract.ViewPresentable {

    val interactable: MoreContract.ViewInteractable = interactable

    override fun startPresenting() {
        interactable.configureToolbar()
        val moreList: MutableList<BaseModel> = ArrayList()
        moreList.add(MoreItem(getString(R.string.setting_item_text), R.drawable.settings))
        moreList.add(MoreItem(getString(R.string.help_item_text), R.drawable.settings))
        moreList.add(MoreItem(getString(R.string.log_out_item_text), R.drawable.settings))
        interactable.configureAdapter(moreList)
    }

    override fun stopPresenting() {

    }

    fun getString(res: Int): String = context.getString(res)
}