package com.soho.sohoapp.feature.chat.chatconversation.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.CardView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.R
import com.soho.sohoapp.extensions.and
import com.soho.sohoapp.feature.chat.model.PendingMessage
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

/**
 * Created by chowii on 18/1/18.
 */
class PendingChatImageViewHolder(
        itemView: View,
        private val onRetryClick: (Pair<Uri, String>) -> Unit
) : BaseChatImageViewHolder<PendingMessage>(itemView) {

    @BindView(R.id.pending_image_card_view) lateinit var imageCardView : CardView
    @BindView(R.id.pending_image_view) lateinit var pendingImageView : ImageView
    @BindView(R.id.progress_bar) lateinit var progressBar: ProgressBar
    @BindView(R.id.retry_upload_text) lateinit var retryUploadTextView: TextView

    override fun onBindViewHolder(model: PendingMessage) {
        val imageBitmap = BitmapFactory.decodeStream(itemView.context.contentResolver.openInputStream(model.imageFile.first))
        val imageSize = imageBitmap.and { resizeImage(Pair(width.toFloat(), height.toFloat())) }

        Picasso.with(itemView.context)
                .load(model.imageFile.first)
                .centerInside()
                .resize(imageSize.first, imageSize.second)
                .transform(BlurTransformation(itemView.context))
                .into(pendingImageView)

        if (!model.uploadSuccessful) {
            progressBar.visibility = GONE
            retryUploadTextView.visibility = VISIBLE
            imageCardView.setOnClickListener { onRetryClick.invoke(model.imageFile) }
        } else {
            progressBar.visibility = VISIBLE
            retryUploadTextView.visibility = GONE
            imageCardView.setOnClickListener {  }
        }
    }
}