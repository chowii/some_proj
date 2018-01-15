package com.soho.sohoapp.feature.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.durationFromNowAsString
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.chat.viewholder.ChatChannelViewHolder
import com.soho.sohoapp.feature.common.model.EmptyDataSet
import com.soho.sohoapp.feature.common.viewholder.EmptyDataSetViewHolder
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannelAdapter(private val subscribedChannels: MutableList<BaseModel?>,
                         private val onChatChannelClick: (String, String) -> Unit
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
                    subscribedChannels
                            .filter { it is ChatChannel }
                            .map { it as ChatChannel }[position]
                            .let { chatChannel: ChatChannel ->
                                addressTextView.text = chatChannel.propertyAddress
                                chatChannel.messageList.firstOrNull()?.let { message ->
                                    nameTextView.text = message.messageBody ?: "No Messages"
                                    timeTextView.text = message.timeStampAsDate?.durationFromNowAsString()
                                    itemView.setOnClickListener {
                                        onChatChannelClick.invoke(
                                                message.channelSid,
                                                chatChannel.property.chatConversation.conversionUsers.firstOrNull()?.trim().orEmpty())
                                    }
                                }
                                messageTextView.text = chatChannel.property.chatConversation.conversionUsers.firstOrNull()?: "No User"
                            }
                }
            }
            is EmptyDataSetViewHolder -> holder.onBindViewHolder(subscribedChannels[position] as EmptyDataSet)
        }
    }


    fun appendToMessageList(messageList: BaseModel?) {
        subscribedChannels.add(messageList)
    }

    fun refreshDataSet() = subscribedChannels.clear()

}