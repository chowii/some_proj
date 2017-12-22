package com.soho.sohoapp.feature.chat.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.title_text_view) lateinit var addressTextView: TextView
    @BindView(R.id.name_text_view) lateinit var nameTextView: TextView
    @BindView(R.id.subtitle_text_view) lateinit var messageTextView: TextView
    @BindView(R.id.time_text_view) lateinit var timeTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

}