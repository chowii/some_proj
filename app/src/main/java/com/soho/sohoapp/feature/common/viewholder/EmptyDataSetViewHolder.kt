package com.soho.sohoapp.feature.common.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.common.model.EmptyDataSet

/**
 * Created by chowii on 29/12/17.
 */
class EmptyDataSetViewHolder(itemView: View, private val onButtonClick: () -> Unit) : BaseViewHolder<EmptyDataSet>(itemView) {

    @BindView(R.id.header) lateinit var headerTextView: TextView
    @BindView(R.id.subheader) lateinit var subheaderTextView: TextView
    @BindView(R.id.redirect_button) lateinit var button: Button

    override fun onBindViewHolder(model: EmptyDataSet) {
        headerTextView.text = model.header
        subheaderTextView.text = model.subHeader
        button.text = model.buttonText
        button.setOnClickListener { onButtonClick() }
    }
}