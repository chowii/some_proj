package com.soho.sohoapp.feature.chat.adapter

import android.graphics.Typeface
import android.support.annotation.StringRes
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
class ChatChannelAdapter(private val subscribedChannels: MutableList<BaseModel>,
                         private val onChatChannelClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = subscribedChannels.size

    override fun getItemViewType(position: Int) = subscribedChannels[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_chat_channel -> ChatChannelViewHolder(itemView)
            R.layout.item_empty_dataset -> EmptyDataSetViewHolder(itemView, {})
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
                                configureUnconsumedMessages(chatChannel.isUnconsumed, this)
                                chatChannel.messageList.firstOrNull()?.let { message ->
                                    itemView.setOnClickListener {
                                        onChatChannelClick.invoke(message.channelSid)
                                        setChannelMessageAsRead(chatChannel)
                                    }
                                    timeTextView.text = message.timeStampAsDate?.durationFromNowAsString()
                                    messageTextView.text = message.messageBody
                                            ?: getString(R.string.chat_channel_no_messages_text, this)
                                }
                                nameTextView.text = chatChannel.property.chatConversation.conversionUsers
                                        .firstOrNull()
                                        ?: getString(R.string.chat_channel_no_user_text, this)
                            }
                }
            }
            is EmptyDataSetViewHolder -> holder.onBindViewHolder(subscribedChannels[position] as EmptyDataSet)
        }
    }

    private fun setChannelMessageAsRead(chatChannel: ChatChannel) {
        chatChannel.apply {
            isUnconsumed = false
            setChannelAsRead()
        }
        notifyDataSetChanged()
    }

    private fun configureUnconsumedMessages(
            unconsumedCount: Boolean,
            chatChannelViewHolder: ChatChannelViewHolder
    ) = if (unconsumedCount)
        chatChannelViewHolder.apply {
            nameTextView.setTypeface(null, Typeface.BOLD)
            timeTextView.setTypeface(null, Typeface.BOLD)
            addressTextView.setTypeface(null, Typeface.BOLD)
            messageTextView.setTypeface(null, Typeface.BOLD)
        } else {
        chatChannelViewHolder.apply {
            nameTextView.setTypeface(null, Typeface.NORMAL)
            timeTextView.setTypeface(null, Typeface.NORMAL)
            addressTextView.setTypeface(null, Typeface.NORMAL)
            messageTextView.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun getString(@StringRes res: Int, holder: RecyclerView.ViewHolder) = holder.itemView.context.getString(res)

    fun appendToMessageList(messageList: BaseModel) {
        subscribedChannels.add(messageList)
    }

    fun refreshDataSet() = subscribedChannels.clear()

    fun updateChannelList(updatedChannelList: List<BaseModel>) {
        subscribedChannels.clear()
        subscribedChannels.addAll(updatedChannelList)
    }

}