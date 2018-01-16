package com.soho.sohoapp.feature.chat.chatconversation.viewholder

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.DateFormat
import com.soho.sohoapp.extensions.stringFormat
import com.soho.sohoapp.extensions.toStringWithFormat
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.preferences.UserPrefs

/**
 * Created by chowii on 12/1/18.
 */
class ChatConversationViewHolder(itemView: View) : BaseViewHolder<ChatMessage>(itemView) {

    @BindView(R.id.message_start_text_view) lateinit var messageStartTextView: TextView
    @BindView(R.id.message_end_text_view) lateinit var messageEndTextView: TextView
    @BindView(R.id.message_stamp_start_text_view) lateinit var messageStampStartTextView: TextView
    @BindView(R.id.message_stamp_end_text_view) lateinit var messageStampEndTextView: TextView

    private lateinit var userPrefs: UserPrefs

    override fun onBindViewHolder(model: ChatMessage?) {
        model?.let { message ->
            message.message.apply {
                if (author == userPrefs.twilioUser) {
                    showAuthorText()
                    messageEndTextView.text = messageBody
                    messageStampEndTextView.text = timeStampAsDate.toStringWithFormat(DateFormat.DateTimeShort().stringFormat())
                } else {
                    showParticipantText()
                    messageStartTextView.text = messageBody
                    messageStampStartTextView.text = timeStampAsDate.toStringWithFormat(DateFormat.DateTimeShort().stringFormat())
                }
            }
        }
    }

    fun onBindViewHolder(model: ChatMessage, userPrefs: UserPrefs) {
        this.userPrefs = userPrefs
        onBindViewHolder(model)
    }

    private fun showAuthorText() {
        messageStartTextView.visibility = View.GONE
        messageStampStartTextView.visibility = View.GONE
        messageEndTextView.visibility = View.VISIBLE
        messageStampEndTextView.visibility = View.VISIBLE
    }

    private fun showParticipantText() {
        messageStartTextView.visibility = View.VISIBLE
        messageStampStartTextView.visibility = View.VISIBLE
        messageEndTextView.visibility = View.GONE
        messageStampEndTextView.visibility = View.GONE
    }
}
