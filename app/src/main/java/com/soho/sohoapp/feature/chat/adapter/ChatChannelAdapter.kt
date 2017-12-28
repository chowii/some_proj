package com.soho.sohoapp.feature.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.durationFromNowAsString
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.chat.viewholder.ChatChannelViewHolder

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannelAdapter(private val subscribedChannels: MutableList<ChatChannel?>,
                         private val onChatChannelClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = subscribedChannels.size

    override fun getItemViewType(position: Int) = R.layout.item_chat_channel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_channel, parent, false)

        return when (viewType) {
            R.layout.item_chat_channel -> ChatChannelViewHolder(itemView)
            else -> null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatChannelViewHolder -> {
                holder.apply {
                    subscribedChannels[position]?.let { chatChannel ->
                        addressTextView.text = chatChannel.propertyAddress
                        nameTextView.text = chatChannel.lastMessage?.messageBody ?: "No Messages"
                        timeTextView.text = chatChannel.lastMessage?.timeStampAsDate?.durationFromNowAsString()
                        messageTextView.text = chatChannel.property?.chatAttributes?.conversionUsers?.get(1) ?: "No User"
                        itemView.setOnClickListener {
                            chatChannel.lastMessage?.channelSid?.let { it1 -> onChatChannelClick.invoke(it1) }
                        }
                    }
                }
            }
        }
    }


    fun appendToMessageList(messageList: ChatChannel?) {
        subscribedChannels.add(messageList)
    }

}