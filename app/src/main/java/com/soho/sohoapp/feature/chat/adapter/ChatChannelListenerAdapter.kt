package com.soho.sohoapp.feature.chat.adapter

import com.twilio.chat.Channel
import com.twilio.chat.ChannelListener
import com.twilio.chat.Member
import com.twilio.chat.Message

/**
 * Created by chowii on 5/1/18.
 */
open class ChatChannelListenerAdapter : ChannelListener {
    override fun onMemberDeleted(member: Member) {
    }

    override fun onTypingEnded(member: Member) {
    }

    override fun onMessageAdded(message: Message) {
    }

    override fun onMessageDeleted(message: Message) {
    }

    override fun onMemberAdded(member: Member?) {
    }

    override fun onTypingStarted(member: Member) {
    }

    override fun onSynchronizationChanged(channel: Channel) {
    }

    override fun onMessageUpdated(message: Message?, updateReason: Message.UpdateReason?) {
    }

    override fun onMemberUpdated(member: Member?, updateReason: Member.UpdateReason?) {
    }
}