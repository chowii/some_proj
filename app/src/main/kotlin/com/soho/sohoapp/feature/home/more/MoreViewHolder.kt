package com.soho.sohoapp.feature.home.more

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R

/**
 * Created by chowii on 10/09/17.
 */
class MoreViewHolder (itemView: View, val listener: OnMoreItemClickListener): BaseViewHolder<MoreItem>(itemView) {

    @BindView(R.id.settings_item)
    lateinit var settingsItem: TextView

    override fun onBindViewHolder(model: MoreItem) {
        settingsItem.text = model.moreItem
        settingsItem.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                model.moreDrawable,
                                0)
        settingsItem.setOnClickListener({_ -> listener.onSettingsItemClicked(settingsItem.text.toString())})
    }

    interface OnMoreItemClickListener {
        fun onSettingsItemClicked(button: String)
    }

}