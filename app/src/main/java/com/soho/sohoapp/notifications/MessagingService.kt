package com.soho.sohoapp.notifications

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.chatconversation.ChatConversationActivity
import com.soho.sohoapp.navigator.RequestCode.CHAT_CONVERSATION_PUSH_NOTIFICATION
import com.soho.sohoapp.utils.letX

/**
 * Created by chowii on 23/1/18.
 */
class MessagingService : FirebaseMessagingService() {

    companion object {
        private val packageName = this::class.java.`package`.name
        @JvmStatic
        val DEFAULT_CHANNEL =  packageName + ".DEFAULT_CHANNEL"

        @JvmStatic
        val DEFAULT_CHANNEL_NAME = packageName + ".default_channel"

        @JvmStatic
        val DEFAULT_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT

        private val defaultChannelDescription = packageName + ".Default_Notification"
        private const val MESSAGE_BODY_KEY = "twi_body"
        private const val MESSAGE_CHANNEL_ID_KEY = "channel_id"
        private const val NEW_MESSAGE_STRING = "A New message: "
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val messageLong = remoteMessage.data[MESSAGE_BODY_KEY]
        val messageShort = messageLong?.removePrefix(NEW_MESSAGE_STRING)

        val notif = NotificationCompat.Builder(this, DEFAULT_CHANNEL).letX {
            setStyle(NotificationCompat.BigTextStyle().bigText(messageLong))
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(getString(R.string.app_name1))
            setContentText(messageShort)
            setAutoCancel(true)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            setContentIntent(showChatConversation(remoteMessage.data[MESSAGE_CHANNEL_ID_KEY]))
            build()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notifManager.createNotificationChannel(createChannel())

        //An unique identifier for this notification
        notifManager.notify(System.currentTimeMillis().toInt(), notif)

    }

    private fun showChatConversation(channelId: String?) = TaskStackBuilder.create(this).letX {
        addNextIntent(
                Intent(this@MessagingService, ChatConversationActivity::class.java).apply {
                    putExtra(ChatConversationActivity.CHAT_CHANNEL_SID_INTENT_EXTRA, channelId)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                })
                .getPendingIntent(CHAT_CONVERSATION_PUSH_NOTIFICATION, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    @SuppressLint("NewApi")
    private fun createChannel() = NotificationChannel(DEFAULT_CHANNEL, DEFAULT_CHANNEL_NAME, DEFAULT_IMPORTANCE).apply {
        description = defaultChannelDescription
        enableLights(true)
        lightColor = Color.GREEN
        enableVibration(true)
        vibrationPattern = LongArray(Notification.DEFAULT_VIBRATE)
    }
}