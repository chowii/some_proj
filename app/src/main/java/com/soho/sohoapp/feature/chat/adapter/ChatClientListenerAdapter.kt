package com.soho.sohoapp.feature.chat.adapter

import com.twilio.chat.*

/**
 * Created by chowii on 20/12/17.
 */
open class ChatClientListenerAdapter : ChatClientListener{
    override fun onChannelDeleted(channel: Channel?) {
    }

    override fun onClientSynchronization(status: ChatClient.SynchronizationStatus) {
    }

    override fun onNotificationSubscribed() {
    }

    override fun onUserSubscribed(user: User?) {
    }

    override fun onChannelUpdated(channel: Channel?, p1: Channel.UpdateReason?) {
    }

    override fun onNotificationFailed(errorInfo: ErrorInfo?) {
    }

    override fun onChannelJoined(channel: Channel?) {
    }

    override fun onChannelAdded(channel: Channel?) {
    }

    override fun onChannelSynchronizationChange(channel: Channel?) {
    }

    override fun onNotification(p0: String?, p1: String?) {
    }

    override fun onUserUnsubscribed(user: User?) {
    }

    override fun onChannelInvited(channel: Channel?) {
    }

    override fun onConnectionStateChange(connectionState: ChatClient.ConnectionState?) {
    }

    override fun onError(error: ErrorInfo?) {
    }

    override fun onUserUpdated(user: User?, updateReason: User.UpdateReason?) {
    }
}