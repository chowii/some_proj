package com.soho.sohoapp.feature.chat.chatconversation.adapter

import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.chatconversation.viewholder.ChatConversationViewHolder
import com.soho.sohoapp.feature.chat.chatconversation.viewholder.ChatImageViewHolder
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.preferences.UserPrefs

/**
 * Created by chowii on 28/12/17.
 */
class ChatConversationAdapter(
        private var messageList: MutableList<ChatMessage>,
        private val userPrefs: UserPrefs,
        val displayMetrics: DisplayMetrics
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int): Int = if (messageList[position].chatAttachment != null) {
        R.layout.item_chat_image
    } else {
        R.layout.item_chat_conversation
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val itemView = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return when(viewType) {
            R.layout.item_chat_conversation -> ChatConversationViewHolder(itemView)
            R.layout.item_chat_image -> ChatImageViewHolder(itemView)
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatConversationViewHolder -> holder.onBindViewHolder(messageList[position], userPrefs)
            is ChatImageViewHolder -> holder.onBindViewHolder(messageList[position], userPrefs)
        }
    }

    internal fun updatedMessageList(messageList: MutableList<ChatMessage>) {
        this.messageList = messageList
    }

    internal fun appendMessage(message: ChatMessage) {
        this.messageList.add(message)
    }

}