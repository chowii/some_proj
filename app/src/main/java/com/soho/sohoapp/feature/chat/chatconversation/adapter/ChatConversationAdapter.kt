package com.soho.sohoapp.feature.chat.chatconversation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.DateFormat
import com.soho.sohoapp.extensions.stringFormat
import com.soho.sohoapp.extensions.toStringWithFormat
import com.twilio.chat.Message

/**
 * Created by chowii on 28/12/17.
 */
class ChatConversationAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatConversationAdapter.ChatConversationViewHolder>() {

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int): Int = R.layout.item_chat_conversation

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatConversationViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return ChatConversationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatConversationViewHolder, position: Int) {
        holder.apply {
            messageList[position].apply {
                if (author == DEPENDENCIES.userPrefs.twilioUser) {
                    messageEndTextView.text = messageBody
                    messageStampEndTextView.text = timeStampAsDate.toStringWithFormat(DateFormat.DateTimeShort().stringFormat())
                } else {
                    messageStartTextView.text = messageList[position].messageBody
                    messageStampStartTextView.text = timeStampAsDate.toStringWithFormat(DateFormat.DateTimeShort().stringFormat())
                }
            }
        }
    }

    class ChatConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.message_start_text_view) lateinit var messageStartTextView: TextView
        @BindView(R.id.message_end_text_view) lateinit var messageEndTextView: TextView
        @BindView(R.id.message_stamp_start_text_view) lateinit var messageStampStartTextView: TextView
        @BindView(R.id.message_stamp_end_text_view) lateinit var messageStampEndTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }

}