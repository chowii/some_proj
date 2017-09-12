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
import com.soho.sohoapp.feature.home.more.model.AccountVerificationStatus
import com.soho.sohoapp.feature.home.more.model.AccountVerificationType
import com.soho.sohoapp.feature.home.more.model.VerificationItem

/**
 * Created by chowii on 11/9/17.
 */
class VerificationViewHolder(var itemView: View, val listener: SettingsAdapter.OnSettingsItemClickListener, private val context: Context) : BaseViewHolder<VerificationItem>(itemView) {

    @BindView(R.id.settings_root_view)
    lateinit var settingsRootLayout: RelativeLayout

    @BindView(R.id.title_text_view)
    lateinit var titleTextView: TextView

    @BindView(R.id.subtitle_text_view)
    lateinit var subtitleTextView: TextView

    @BindView(R.id.icon_item)
    lateinit var iconItemImageView: ImageView

    override fun onBindViewHolder(model: VerificationItem?) {
        model?.let {
            configureItem(model)
            statusTextColor(model)
        }
    }

    private fun statusTextColor(model: VerificationItem?) {
        when (AccountVerificationStatus.valueOf(model?.state?.name!!.toUpperCase())) {
            AccountVerificationStatus.NEW -> subtitleTextView.setTextColor(
                                ContextCompat.getColor(
                                        context,
                                        AccountVerificationStatus.NEW.colorRes
                                ))
            AccountVerificationStatus.PENDING -> subtitleTextView.setTextColor(
                                ContextCompat.getColor(
                                        context,
                                        AccountVerificationStatus.PENDING.colorRes
                                ))
            AccountVerificationStatus.NOT_VERIFIED -> subtitleTextView.setTextColor(
                                ContextCompat.getColor(
                                        context,
                                        AccountVerificationStatus.NOT_VERIFIED.colorRes
                                ))
            AccountVerificationStatus.VERIFIED -> subtitleTextView.setTextColor(
                                ContextCompat.getColor(
                                        context,
                                        AccountVerificationStatus.VERIFIED.colorRes
                                ))
        }
    }

    private fun configureItem(model: VerificationItem) {
        titleTextView.text = configureType(model.type)
        subtitleTextView.text = model.state.name
        iconItemImageView.setImageResource(model.type.res)
        settingsRootLayout.setOnClickListener({ _ -> listener.onSettingsItemClicked(model.type.type) })
    }

    private fun configureType(type: AccountVerificationType): CharSequence? = when (type) {
        AccountVerificationType.LicenceVerification -> {
            context.getString(R.string.settings_account_verification_photo_id_text)
        }
        AccountVerificationType.MobileVerification -> context.getString(R.string.settings_account_verification_mobile_number_text)
        AccountVerificationType.AgentLicenceVerification -> context.getString(R.string.settings_account_verification_agent_license_text)
    }
}