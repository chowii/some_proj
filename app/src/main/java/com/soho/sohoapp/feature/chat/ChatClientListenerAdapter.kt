package com.soho.sohoapp.feature.chat

import com.twilio.chat.*

/**
 * Created by chowii on 20/12/17.
 */
open class ChatClientListenerAdapter : ChatClientListener{
    override fun onChannelDeleted(p0: Channel?) {

    }

    override fun onClientSynchronization(p0: ChatClient.SynchronizationStatus) {
    }

    override fun onNotificationSubscribed() {
    }

    override fun onUserSubscribed(p0: User?) {
    }

    override fun onChannelUpdated(p0: Channel?, p1: Channel.UpdateReason?) {
    }

    override fun onNotificationFailed(p0: ErrorInfo?) {
    }

    override fun onChannelJoined(p0: Channel?) {
    }

    override fun onChannelAdded(p0: Channel?) {
    }

    override fun onChannelSynchronizationChange(p0: Channel?) {
    }

    override fun onNotification(p0: String?, p1: String?) {
    }

    override fun onUserUnsubscribed(p0: User?) {
    }

    override fun onChannelInvited(p0: Channel?) {
    }

    override fun onConnectionStateChange(p0: ChatClient.ConnectionState?) {
    }

    override fun onError(p0: ErrorInfo?) {
    }

    override fun onUserUpdated(p0: User?, p1: User.UpdateReason?) {
    }
}