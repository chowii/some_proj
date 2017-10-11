package com.soho.sohoapp.feature.home.more.viewholder

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.more.adapter.SettingsAdapter
import com.soho.sohoapp.feature.home.more.model.VerificationItem

/**
 * Created by chowii on 11/9/17.
 */
class VerificationViewHolder(var itemView: View,
                             val listener: SettingsAdapter.OnSettingsItemClickListener,
                             private val context: Context) : BaseViewHolder<VerificationItem>(itemView) {

    @BindView(R.id.settings_root_view)
    lateinit var settingsRootLayout: RelativeLayout

    @BindView(R.id.title_text_view)
    lateinit var titleTextView: TextView

    @BindView(R.id.subtitle_text_view)
    lateinit var subtitleTextView: TextView

    @BindView(R.id.icon_item)
    lateinit var iconItemImageView: ImageView

    override fun onBindViewHolder(model: VerificationItem) {
        model.verification.let {
            titleTextView.text = context.getString(it.getDisplayableType())
            subtitleTextView.text = context.getString(it.getStateLabel())
            iconItemImageView.setImageResource(it.getIcon())
            settingsRootLayout.setOnClickListener({ _ -> listener.onSettingsItemClicked(it.type) })
            subtitleTextView.setTextColor(ContextCompat.getColor(context, it.getColor()))
        }
    }
}