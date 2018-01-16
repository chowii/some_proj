package com.soho.sohoapp.feature.chat.chatconversation.viewholder

import android.content.res.Resources
import android.support.v7.widget.CardView
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.network.Keys.ChatImage
import com.soho.sohoapp.preferences.UserPrefs
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by chowii on 12/1/18.
 */

class ChatImageViewHolder(itemView: View) : BaseViewHolder<ChatMessage>(itemView) {

    @BindView(R.id.image_card_start) lateinit var cardStart: CardView
    @BindView(R.id.image_card_end) lateinit var cardEnd: CardView
    @BindView(R.id.chat_start_image_view) lateinit var chatStartImageView: ImageView
    @BindView(R.id.chat_end_image_view) lateinit var chatEndImageView: ImageView

    private lateinit var userPrefs: UserPrefs

    private var widthDp: Float = itemView.context.resources.configuration.screenWidthDp.toFloat()
    private var widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, widthDp, Resources.getSystem().displayMetrics)

    override fun onBindViewHolder(model: ChatMessage) {
        showImage(model)
    }

    private fun showImage(model: ChatMessage) {
        val imageView = if (userPrefs.twilioUser == model.message.author) {
            showAuthorImage()
            chatEndImageView
        } else {
            showParticipantImage()
            chatStartImageView
        }

        val size = resizeImage(model)
        val endPoint: String = getImageUrl(model)

        Picasso.with(itemView.context)
                .load(endPoint)
                .placeholder(R.drawable.condo)
                .centerInside()
                .resize(size.first, size.second)
                .onlyScaleDown()
                .into(imageView)
    }

    private fun showParticipantImage() {
        chatEndImageView.visibility = GONE
        cardEnd.visibility = GONE
        chatStartImageView.visibility = VISIBLE
        cardStart.visibility = VISIBLE
    }

    private fun showAuthorImage() {
        chatStartImageView.visibility = GONE
        cardStart.visibility = GONE
        chatEndImageView.visibility = VISIBLE
        cardEnd.visibility = VISIBLE
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

    private fun resizeImage(model: ChatMessage): Pair<Int, Int> {
        itemView.context.let {
            val imageFile = model.chatAttachment?.file
            val imageWidth = imageFile?.width ?: 1.0f
            val aspectRatio = getAspectRatio(imageWidth, imageFile?.height ?: 1.0f)
            val dimen = getCurrentDimenRatio(imageWidth, widthPx)
            val minimumScreenToImageRatio = 0.60f

            val newWidth = widthPx.times(minimumScreenToImageRatio)
            val newHeight: Float?
            return if (dimen > minimumScreenToImageRatio) {
                newHeight = newWidth.div(aspectRatio)
                Pair(newWidth.toInt(), newHeight.toInt())
            } else {
                Pair(imageFile?.width?.toInt() ?: 1, imageFile?.height?.toInt() ?: 1)
            }
        }
    }

    private fun getCurrentDimenRatio(imageWidth: Float, widthPx: Float) =
            imageWidth / widthPx

    private fun getAspectRatio(imageWidth: Float, imageHeight: Float) =
            imageWidth / imageHeight

    fun onBindViewHolder(model: ChatMessage, userPrefs: UserPrefs) {
        this.userPrefs = userPrefs
        onBindViewHolder(model)
    }
}
