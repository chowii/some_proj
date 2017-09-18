package com.soho.sohoapp.feature.home.more.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.toStringWithDisplayFormat
import com.soho.sohoapp.feature.home.more.adapter.SettingsAdapter
import com.soho.sohoapp.feature.home.more.model.SettingItem

/**
 * Created by chowii on 12/9/17.
 */
class SettingViewHolder(var itemView: View, val listener: SettingsAdapter.OnSettingsItemClickListener): BaseViewHolder<SettingItem>(itemView) {

    @BindView(R.id.settings_root_view)
    lateinit var settingsRootLayout: RelativeLayout

    @BindView(R.id.title_text_view)
    lateinit var titleTextView: TextView

    @BindView(R.id.subtitle_text_view)
    lateinit var subtitleTextView: TextView

    @BindView(R.id.icon_item)
    lateinit var iconItemImageView: ImageView

    override fun onBindViewHolder(model: SettingItem?) {
        model.let {
            titleTextView.text = model?.title
            subtitleTextView.text = model?.dateOfBirth?.toStringWithDisplayFormat() ?: ""
            iconItemImageView.setImageResource(model?.iconRes!!)
        }
        settingsRootLayout.setOnClickListener({ _ -> listener.onSettingsItemClicked(model?.title ?: "") })
    }
}