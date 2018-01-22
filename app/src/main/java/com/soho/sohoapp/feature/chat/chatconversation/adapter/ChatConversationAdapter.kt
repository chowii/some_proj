package com.soho.sohoapp.feature.chat.chatconversation.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.chatconversation.viewholder.ChatConversationViewHolder
import com.soho.sohoapp.feature.chat.chatconversation.viewholder.ChatImageViewHolder
import com.soho.sohoapp.feature.chat.chatconversation.viewholder.PendingChatImageViewHolder
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.feature.chat.model.PendingMessage
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.preferences.UserPrefs

/**
 * Created by chowii on 28/12/17.
 */
class ChatConversationAdapter(
        private var messageList: MutableList<out BaseModel> = mutableListOf(),
        private val userPrefs: UserPrefs,
        private val onRetryClick: (Pair<Uri, String>) -> Unit
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int): Int = when {
        (messageList[position] as? ChatMessage)?.chatAttachment != null -> R.layout.item_chat_image
        messageList[position] is PendingMessage -> R.layout.item_pending_chat_image
        else -> R.layout.item_chat_conversation
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val itemView = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_chat_conversation -> ChatConversationViewHolder(itemView)
            R.layout.item_chat_image -> ChatImageViewHolder(itemView)
            R.layout.item_pending_chat_image -> PendingChatImageViewHolder(itemView, onRetryClick)
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatConversationViewHolder -> holder.onBindViewHolder(messageList[position] as ChatMessage, userPrefs)
            is ChatImageViewHolder -> holder.onBindViewHolder(messageList[position] as ChatMessage, userPrefs)
            is PendingChatImageViewHolder -> holder.onBindViewHolder(messageList[position] as PendingMessage)
        }
    }

    internal fun updatedMessageList(messageList: MutableList<out BaseModel>) {
        this.messageList = messageList
    }

    internal fun appendMessage(message: BaseModel) {
        val tempList = mutableListOf<BaseModel>()
        tempList.apply {
            addAll(messageList)
            add(message)
        }
        this.messageList = tempList
    }

    private fun addMessageAt(position: Int, message: BaseModel) {
        val tempList = mutableListOf<BaseModel>()
        tempList.apply {
            addAll(messageList)
            add(position, message)
        }
        this.messageList = tempList
    }

    internal fun updateImageMessage(message: ChatMessage) {
        findPendingImageItem { it.imageFile.second == message.chatAttachment?.file?.originalFilename }?.let {
            val index = messageList.indexOf(it)
            messageList.removeAt(index)
            addMessageAt(index, message)
        }
    }

    internal fun notifyUploadFailed(image: Pair<Uri, String>) = findPendingImageItem {
        it.imageFile.second == image.second
    }?.apply {
        val index = messageList.indexOf(this)
        (messageList[index] as PendingMessage).uploadSuccessful = false
    }


    private fun findPendingImageItem(find: (PendingMessage) -> Boolean) = messageList
            .filter { it is PendingMessage }
            .map { it as PendingMessage }
            .find { find(it) }

}