package com.soho.sohoapp.feature.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.chat.viewholder.ChatChannelViewHolder

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannelAdapter(private val subscribedChannels: MutableList<ChatChannel?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                    subscribedChannels[position]?.let {
                        addressTextView.text = it.propertyAddress
                        nameTextView.text = it.lastMessage?.messageBody ?: "No Messages"
                        messageTextView.text = it.property?.chatAttributes?.conversionUsers?.get(1) ?: "No User"
                        timeTextView.text = it.lastMessage?.timeStamp
                    }

                }
            }
        }
    }


    fun appendToMessageList(messageList: ChatChannel?) {
        subscribedChannels.add(messageList)
    }

}