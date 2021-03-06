package com.soho.sohoapp.feature.chat.chatconversation.viewholder

import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.network.Keys.ChatImage
import com.soho.sohoapp.preferences.UserPrefs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by chowii on 12/1/18.
 */

class ChatImageViewHolder(itemView: View) : BaseChatImageViewHolder<ChatMessage>(itemView) {

    @BindView(R.id.image_card_start) lateinit var cardStart: CardView
    @BindView(R.id.image_card_end) lateinit var cardEnd: CardView
    @BindView(R.id.chat_start_image_view) lateinit var chatStartImageView: ImageView
    @BindView(R.id.chat_end_image_view) lateinit var chatEndImageView: ImageView
    @BindView(R.id.chat_start_progress_bar) lateinit var startProgressBar : ProgressBar
    @BindView(R.id.chat_end_progress_bar) lateinit var endProgressBar : ProgressBar

    private lateinit var userPrefs: UserPrefs
    private var isUserAuthor: Boolean = false

    override fun onBindViewHolder(model: ChatMessage) {
        showImage(model)
    }

    private fun showImage(model: ChatMessage) {

        val imageView = if (isUserAuthor) {
            showAuthorImage()
            chatEndImageView
        } else {
            showParticipantImage()
            chatStartImageView
        }

        model.chatAttachment?.file?.apply {

            val size = resizeImage(Pair(width, height))
            val endPoint: String = getImageUrl(model)

            Picasso.with(itemView.context)
                    .load(endPoint)
                    .placeholder(R.drawable.condo)
                    .centerInside()
                    .resize(size.first, size.second)
                    .onlyScaleDown()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            if (isUserAuthor) endProgressBar.visibility = GONE
                            else startProgressBar.visibility = GONE
                        }

                        override fun onError() {
                            Log.d("LOG_TAG---", "error loading image: ")
                        }
                    })
        }
    }

    private fun showParticipantImage() {
        chatEndImageView.visibility = GONE
        cardEnd.visibility = GONE
        endProgressBar.visibility = GONE
        chatStartImageView.visibility = VISIBLE
        cardStart.visibility = VISIBLE
        startProgressBar.visibility = VISIBLE
    }

    private fun showAuthorImage() {
        chatStartImageView.visibility = GONE
        cardStart.visibility = GONE
        endProgressBar.visibility = GONE
        chatEndImageView.visibility = VISIBLE
        cardEnd.visibility = VISIBLE
        startProgressBar.visibility = VISIBLE
    }

    private fun getImageUrl(model: ChatMessage): String = model.let {
        val conversationId = it.chatConversation?.id ?: 0
        val attachmentId = it.chatAttachment?.file?.id ?: 0
        val authToken = userPrefs.authToken

        String.format(
                Locale.getDefault(),
                ChatImage.CHAT_ATTACHMENT_ENDPOINT_FORMAT,
                conversationId,
                attachmentId,
                authToken
        )
    }

    fun onBindViewHolder(model: ChatMessage, userPrefs: UserPrefs) {
        this.userPrefs = userPrefs
        isUserAuthor = userPrefs.twilioUser == model.message.author
        startProgressBar.isIndeterminate = true
        endProgressBar.isIndeterminate = true
        onBindViewHolder(model)
    }
}
