package com.soho.sohoapp.feature.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
                         private val onChatChannelClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = subscribedChannels.size

    override fun getItemViewType(position: Int) = subscribedChannels[position]?.itemViewType ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_chat_channel -> ChatChannelViewHolder(itemView)
            R.layout.item_empty_dataset -> EmptyDataSetViewHolder(
                    itemView,
                    { Toast.makeText(parent.context, "Button Pressed", Toast.LENGTH_SHORT).show() }
            )
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
                                nameTextView.text = chatChannel.lastMessage?.messageBody ?: "No Messages"
                                timeTextView.text = chatChannel.lastMessage?.timeStampAsDate?.durationFromNowAsString()
                                messageTextView.text = chatChannel.property?.chatAttributes?.conversionUsers?.get(1) ?: "No User"
                                itemView.setOnClickListener {
                                    chatChannel.lastMessage?.channelSid?.let { it1 -> onChatChannelClick.invoke(it1) }
                                }
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